package org.sid.ebankingbackend.services.Tickets;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.sid.ebankingbackend.entities.*;
import org.sid.ebankingbackend.models.User;
import org.sid.ebankingbackend.repository.Tickets.PriceRepository;
import org.sid.ebankingbackend.repository.Tickets.TicketCardRepository;
import org.sid.ebankingbackend.repository.Tickets.TicketRepository;
import org.sid.ebankingbackend.repository.UserRepository;
import org.sid.ebankingbackend.services.Tickets.QrCode.QRCodeGenerator;
import org.sid.ebankingbackend.services.Whattsapp.WhatsAppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class TicketService implements ITicketService {
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    PriceRepository priceRepository;
    @Autowired
    TicketCardRepository ticketCardRepository;
    @Autowired
    PriceService priceService;
    @Autowired
    WhatsAppService whatsAppService;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    QRCodeGenerator generateQRCodeImage;
    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);


    @Override
    public List<Ticket> GetAllTickets() {
        return ticketRepository.findAll();
    }


    @Override
    public Ticket GetTicket(String ref) {
        return ticketRepository.findByRefTicket(ref);
    }


    @Override
    public Ticket ModifyTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public void DeletTicket(Long id) {
        ticketRepository.deleteById(id);

    }



    @Transactional
    public Ticket generateTicket(Long userid,Places place) {
        Ticket newTicket = new Ticket();

        // Définir la référence unique du ticket
        newTicket.setRefTicket(generateUniqueRef());



        // Définir la disponibilité, la date d'expiration, le lieu du ticket
        newTicket.setDisponibility(true);
        newTicket.setExpireDate(generateExpireDate());
        newTicket.setCreationDate(new Date());


        newTicket.setPlaces(place);


        // Déterminer le type de ticket en fonction de la rangée de la place
        TypeTicket typeTicket = determineTicketType(place.getRowLabel().toString());
        newTicket.setTypeTicket(typeTicket);

        // Récupérer le prix en fonction de la tranche d'âge et du type de ticket
        Price price = priceRepository.findByTrancheAgeAndTypeTicket(TrancheAge.ADULT, typeTicket);
        newTicket.setPrice(price);
        //c'est provisoirement jusqu 'a avoir le user connecter
        VenuePlan venuePlan = place.getVenuePlan(); // Récupérer le VenuePlan associé à la place
        if (venuePlan != null) {
            newTicket.setUserid(userid); // Utiliser l'ID de l'utilisateur stocké dans VenuePlan
        }
        // Générer le QR Code et l'ajouter au ticket
        try {
            byte[] qrCodeImage = generateQRCodeImage.generateQRCodeImage(newTicket);
            newTicket.setQrCode(qrCodeImage); // Assurez-vous d'avoir un champ qrCode dans votre entité Ticket
        } catch (Exception e) {
            e.printStackTrace();
            // Gérer l'exception si la génération du QR code échoue
        }

        return newTicket;
    }

    private TypeTicket determineTicketType(String rowLabel) {
        // Si la rangée est A ou B, retourner PREMIUM, sinon CLASSIQUE
        if ("A".equalsIgnoreCase(rowLabel) || "B".equalsIgnoreCase(rowLabel)) {
            return TypeTicket.PREMIUM;
        } else {
            return TypeTicket.CLASSIQUE;
        }
    }

// Assurez-vous que la méthode findByTrancheAgeAndTypeTicket(TrancheAge, TypeTicket) existe dans priceRepository

    private String generateUniqueRef() {
        // Utilisez la date/heure actuelle comme base pour la référence
        String datePart = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        // Ajoutez un UUID pour garantir l'unicité
        String uniquePart = UUID.randomUUID().toString().substring(0, 8);
        return "TCK" + datePart + uniquePart;
    }

    private Date generateExpireDate() {
        // Implémentez la logique pour définir la date d'expiration du ticket
        // Par exemple, ajouter 1 jour à la date/heure actuelle
        Date now = new Date();
        long expireInMillis = now.getTime() + (1000 * 60 * 60 * 24); // Ajoute 24 heures
        return new Date(expireInMillis);
    }


  /*  @Scheduled(fixedRate = 120000) // 120 000 millisecondes = 2 minutes
    public void updateTicketDisponibility() {
        Date twoMinutesAgo = new Date(System.currentTimeMillis() - 120000); // Définir le moment "il y a deux minutes"
        // Mettre à jour les tickets créés il y a plus de deux minutes
        ticketRepository.updateDisponibilityForTicketsBefore(twoMinutesAgo);
    }*/
    public Ticket updateTicketPriceForAgeGroup(Long ticketId, TrancheAge trancheAge) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));

        Price price = priceService.findPriceForAgeGroupAndType(trancheAge, ticket.getTypeTicket());

        ticket.setPrice(price); // Associe l'objet Price trouvé au ticket
        ticketRepository.save(ticket);

        // Trouver le TicketCard correspondant
        Optional<Ticketcard> ticketCardOpt = ticketCardRepository.findTicketCardByTicketId(ticketId);
        if (ticketCardOpt.isPresent()) {
            Ticketcard ticketCard = ticketCardOpt.get();

            // Calculer le nouveau total du TicketCard
            float total = ticketCard.getTickets().stream()
                    .map(t -> t.getPrice().getPrice())
                    .reduce(0f, Float::sum);

            // Mettre à jour et sauvegarder le TicketCard
            ticketCard.setTotal(total);
            ticketCardRepository.save(ticketCard);
        }

        return ticket;
    }





    //@Scheduled(cron = "0 59 23 * * *")
  // @Scheduled(cron = "0 */1 * * * *")
    @Transactional
    public void rewardBestBuyerOfTheDay() {
        userService.resetDiscountCodes();
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().plusDays(1).atStartOfDay();


        Date startOfDayDate = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
        Date endOfDayDate = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());

        Pageable topThree = PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "ticketCount"));
        Page<Object[]> topBuyersPage = ticketRepository.findTopBuyersForToday(startOfDayDate, endOfDayDate, topThree);

        if (!topBuyersPage.isEmpty()) {
            Object[] topBuyer = topBuyersPage.getContent().get(0);
            Long bestCustomerId = (Long) topBuyer[0];
            Long ticketCount = (Long) topBuyer[1];

            if (ticketCount > 0) {  // S'assurer que le meilleur acheteur a acheté au moins un ticket
                String discountCode = generateDiscountCode();
                userService.updateDiscountCodeForUser(bestCustomerId,discountCode);
                sendDiscountCode(bestCustomerId, discountCode);  // Envoyer le code de réduction
                logger.info("Code de réduction envoyé au meilleur acheteur avec l'ID: {}", bestCustomerId);
            }
        } else {
            logger.info("Aucun acheteur trouvé pour aujourd'hui.");
        }
    }
    private String generateDiscountCode() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
    public void sendDiscountCode(Long userId, String discountCode) {
        User user = userRepository.findById(userId).get();

        String messageBody = "Félicitations ! Voici votre code de réduction : " + discountCode;

        // Envoyer le message via WhatsApp
        whatsAppService.sendWhatsAppMessage(user.getPhoneNumber(), messageBody); // Assurez-vous que User a un numéro de téléphone enregistré

    }


}



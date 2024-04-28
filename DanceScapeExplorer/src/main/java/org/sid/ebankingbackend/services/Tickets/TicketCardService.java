package org.sid.ebankingbackend.services.Tickets;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.sid.ebankingbackend.entities.Ticket;
import org.sid.ebankingbackend.entities.Ticketcard;
import org.sid.ebankingbackend.repository.Tickets.TicketCardRepository;
import org.sid.ebankingbackend.repository.Tickets.TicketRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class TicketCardService implements ITicketCardService {

    TicketCardRepository ticketCardRepository;
    TicketRepository ticketRepository;
    PriceService priceService;
    UserService  userService;

    @Override
    public List<Ticketcard> GetAllTicketCard() {
        return ticketCardRepository.findAll();
    }

    @Override
    public Ticketcard GetTicketCard(Long id) {
        return  ticketCardRepository.findById(id).get();
    }

    @Override
    public Ticketcard AddTicketcard(Ticketcard ticketcard) {
        return ticketCardRepository.save(ticketcard);
    }

    @Override
    public Ticketcard ModifyTicketCard(Ticketcard ticketcard) {
        return ticketCardRepository.save(ticketcard);
    }

    @Override
    public void DeletTicketCard(Long id) {
        ticketCardRepository.deleteById(id);

    }

    @Transactional
    public Ticketcard createAndAssignTicketCardToUser(Long userId) {
        Date latestCreationDate = ticketRepository.findLatestCreationDateByUserId(userId);
        List<Ticket> availableTicketsForUser = ticketRepository.findAllByUseridAndCreationDate(userId, latestCreationDate);

        // Créer un nouveau panier (Ticketcard)
        Ticketcard ticketCard = new Ticketcard();
        ticketCard.setUserid(userId);
        ticketCard.setDate(new Date());
        // Convertir la liste en Set pour l'assigner au panier
        ticketCard.setTickets(new HashSet<>(availableTicketsForUser));

        // Calculer le total des prix des tickets et l'affecter au panier
        double total = availableTicketsForUser.stream()
                .mapToDouble(ticket -> ticket.getPrice().getPrice()) // Assurez-vous que getPrice() ne renvoie pas null et qu'il a une méthode pour obtenir la valeur
                .sum();
        ticketCard.setTotal((float) total);

        // Sauvegarder le panier dans la base de données
        return ticketCardRepository.save(ticketCard);
    }
    /*public Ticketcard getTicketCardByUserIdAndDate(Long userId) {
        Ticketcard ticketCard = ticketCardRepository.findFirstByUseridOrderByDateDesc(userId);
        if (ticketCard != null) {
            for (Ticket ticket : ticketCard.getTickets()) {
                // Vérifiez que getQrCode() ne renvoie pas null
                if (ticket.getQrCode() != null) {
                    String qrCodeBase64 = Base64.getEncoder().encodeToString(ticket.getQrCode());
                    // Préfixez le string base64 avec le préfixe requis pour les données d'image en base64
                    String qrCodeDataUri = "data:image/png;base64," + qrCodeBase64;
                    // Assurez-vous que le modèle Ticket a un champ pour stocker la chaîne complète de l'URI de données
                    ticket.setQrCodeBase64(qrCodeDataUri);
                }
            }
        }
        return ticketCard;
    }*/



    @Transactional
    public Ticketcard applyDiscountToMostRecentTicketCard(Long userId, String discountCode) {

        List<Ticketcard> ticketcards = ticketCardRepository.findByUserid(userId);

        Ticketcard mostRecentTicketcard = null;
        Date mostRecentDate = null;

        for (Ticketcard tc : ticketcards) {
            for (Ticket ticket : tc.getTickets()) {
                if (mostRecentDate == null || ticket.getCreationDate().after(mostRecentDate)) {
                    mostRecentDate = ticket.getCreationDate();
                    mostRecentTicketcard = tc;
                }
            }
        }
        boolean validated = userService.validateDiscountCode(discountCode);
        if (mostRecentTicketcard != null && validated) {
            // Supposons que la réduction est de 40%
            float newTotal = mostRecentTicketcard.getTotal() * 0.6f;
            mostRecentTicketcard.setTotal(newTotal);
            userService.resetDiscountCodes();
            return ticketCardRepository.save(mostRecentTicketcard);
        } else {
            throw new IllegalArgumentException("Aucun ticket récent trouvé ou code de réduction invalide.");
        }
    }


     @Transactional
   public Map<String, Object> getLastTicketCardDetails(Long userId) {
        Ticketcard lastTicketCard = ticketCardRepository.findFirstByUseridOrderByDateDesc(userId);
        if (lastTicketCard == null) {
            throw new IllegalArgumentException("Aucun Ticketcard trouvé pour cet utilisateur.");
        }

        Map<String, Object> details = new HashMap<>();
        details.put("userName", userService.getUserName(userId)); // Méthode hypothétique pour récupérer le nom d'utilisateur
        details.put("total", lastTicketCard.getTotal());
        details.put("date", lastTicketCard.getDate());

        List<Map<String, Object>> ticketsDetails = new ArrayList<>();
        for (Ticket ticket : lastTicketCard.getTickets()) {
            Map<String, Object> ticketDetails = new HashMap<>();
            ticketDetails.put("refTicket", ticket.getRefTicket());
            ticketDetails.put("expireDate", ticket.getExpireDate().toString());

            // Ajout des détails de la place
            if (ticket.getPlaces() != null) {
                ticketDetails.put("seatNumber", ticket.getPlaces().getSeatNumber());
                ticketDetails.put("rowLabel", ticket.getPlaces().getRowLabel().toString());
            }

            // Convertir le QR code en base64 pour affichage
            if (ticket.getQrCode() != null) {
                String qrCodeBase64 = "data:image/png;base64," + Base64.getEncoder().encodeToString(ticket.getQrCode());
                ticketDetails.put("qrCodeBase64", qrCodeBase64);
            }

            ticketsDetails.add(ticketDetails);
        }
        details.put("tickets", ticketsDetails);

        return details;
    }

    public Ticketcard getTicketCardByUserIdAndCompetitionAndDate(Long userId, Long competitionId) {
        List<Ticketcard> ticketCards = ticketCardRepository.findFirstByUseridAndCompetitionOrderByDateDesc(userId, competitionId);
        if (ticketCards.isEmpty()) {
            throw new IllegalArgumentException("No Ticketcard found for this user and competition.");
        }

        Ticketcard ticketCard = ticketCards.get(0); // Assuming you only want the most recent one

        // Proceed to format QR codes
        for (Ticket ticket : ticketCard.getTickets()) {
            if (ticket.getQrCode() != null) {
                String qrCodeBase64 = Base64.getEncoder().encodeToString(ticket.getQrCode());
                String qrCodeDataUri = "data:image/png;base64," + qrCodeBase64;
                ticket.setQrCodeBase64(qrCodeDataUri);
            }
        }
        return ticketCard;
    }

}



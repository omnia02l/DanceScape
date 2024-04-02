package org.sid.ebankingbackend.services;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.sid.ebankingbackend.entities.*;
import org.sid.ebankingbackend.models.User;
import org.sid.ebankingbackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.security.Principal;


import java.util.*;

@Service
@AllArgsConstructor

public class Registrationservice implements IRegistrationservice {
    @Autowired
    RegistrationRepository regrepo;
    @Autowired
    TeamRepository teamrepo;
    @Autowired
    DancerRepository dancerepo;
    @Autowired
    CompetitionRepository comprepo;
    @Autowired
    private UserRepository userRepository;


    @Override
    public List<Registration> retrieveAllRegistrations() {
        return regrepo.findAll();
    }

    @Override
    public Registration addRegistration(Registration r) {
        return regrepo.save(r);
    }

    // Dans votre service
    @Override
    public Registration updateRegistration(Long id, Registration newRegistration) {
        // Récupérer la registration existante à partir de l'ID
        Registration existingRegistration = regrepo.findById(id).get();
        if (existingRegistration != null) {
            // Mettre à jour les champs nécessaires à l'aide du Builder
            existingRegistration.setStatusreg(newRegistration.getStatusreg());
            existingRegistration.setApproved_date(newRegistration.getApproved_date());
            existingRegistration.setRejected_date(newRegistration.getRejected_date());
            // Enregistrer les modifications dans la base de données
            return regrepo.save(existingRegistration);
        } else {

            return null;
        }
    }





    @Override
    public Registration retrieveRegistration(Long idreg) {
        return regrepo.findById(idreg).get();
    }

    @Override
    public void removeRegistration(Long idreg) {
        regrepo.deleteById(idreg);
    }


    @Override
    public long countPendingRegistrations() {
        return regrepo.countByStatusreg(Statusreg.Pending);
    }


    @Scheduled(fixedRate = 60000)
    public void countPendingRegistrationsScheduler() {
        long count = countPendingRegistrations();
        System.out.println("Number of Pending Registrations: " + count);
    }

    public long countMaleDancers(List<Dancer> dancers) {
        return dancers.stream().filter(dancer -> dancer.getDgender() == Gender.man).count();
    }

    public long countFemaleDancers(List<Dancer> dancers) {
        return dancers.stream().filter(dancer -> dancer.getDgender() == Gender.women).count();
    }
    @Override
    public int calculateAge(Date dateofbirthd) {
        Calendar birthdateCalendar = Calendar.getInstance();
        birthdateCalendar.setTime(dateofbirthd);

        Calendar now = Calendar.getInstance();

        int age = now.get(Calendar.YEAR) - birthdateCalendar.get(Calendar.YEAR);

        // Vérifier si la date de naissance n'a pas encore eu lieu cette année
        if (now.get(Calendar.DAY_OF_YEAR) < birthdateCalendar.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age;
    }



    @Override
    @Transactional
    public Registration addRegistrationWithTeamAndDancersassigncomp(RegistrationDTO registrationDTO, Long competitionId, Principal principal) {
        // Récupérer le nom d'utilisateur de l'utilisateur connecté
        String userName = principal.getName();


        User user = userRepository.findByUsername(userName).get();

        Competition competition = comprepo.findById(competitionId).get();

        // Récupérer les frais par participant dans cette compétition
        float feesPerParticipant = competition.getFeesperparticipant();

        Registration registration = registrationDTO.getRegistration();
        registration.setUsername(user.getUsername());
        Team team = registrationDTO.getTeam();
        List<Dancer> dancers = registrationDTO.getDancers();

        // Calculer le nombre de danseurs dans l'équipe
        int numberOfDancers = dancers.size();

        // Multiplier les frais par participant par le nombre de danseurs dans l'équipe
        double amountPaid = feesPerParticipant * numberOfDancers;
        registration.setAmountpaid(amountPaid);

        long maleCount = countMaleDancers(dancers);
        long femaleCount = countFemaleDancers(dancers);
        if (team.getTeamtype() == Teamtype.Group && (maleCount == 0 || femaleCount == 0)) {
            throw new IllegalArgumentException("since we strive for gender equality, we should have at least a man or a woman");
        } else {
            // Assurez-vous que la collection competitions de l'équipe est initialisée
            if (team.getCompetitions() == null) {
                team.setCompetitions(new HashSet<>());
            }

            // Ajouter la compétition à l'équipe
            team.getCompetitions().add(competition);

            // Ajouter l'équipe à la compétition
            if (competition.getTeams() == null) {
                competition.setTeams(new HashSet<>());
            }
            competition.getTeams().add(team);

            // Enregistrer l'équipe avec la compétition associée
            Team savedTeam = teamrepo.save(team);

            // Enregistrer les danseurs
            for (Dancer dancer : dancers) {
                int age = calculateAge(dancer.getDateofbirthd());
                dancer.setAge(age);
                dancer.setTeam(savedTeam);
                dancerepo.save(dancer);
            }

            // Mettre à jour l'enregistrement avec l'équipe
            registration.setTeam(savedTeam);

            // Enregistrer l'enregistrement
            Registration savedRegistration = regrepo.save(registration);

            return savedRegistration;
        }
    }
    @Autowired
    private EmailSenderService emailSenderService;
    @Override
    public void sendEmailsToDancersInTeamByRegistrationId(Long registrationId, String subject, String body) {
        // 2. Récupérer la registration par son ID
        Optional<Registration> registrationOptional = regrepo.findById(registrationId);
        if (registrationOptional.isPresent()) {
            Registration registration = registrationOptional.get();
            // 3. Récupérer la team associée à la registration
            Team team = registration.getTeam();
            if (team != null) {
                // 4. Récupérer la liste des danseurs dans la team
                List<Dancer> dancers = team.getDancers();
                for (Dancer dancer : dancers) {
                    // 5. Récupérer l'e-mail du danseur et envoyer l'e-mail
                    emailSenderService.sendEmailToDancer(dancer, subject, body);
                }
            } else {
                System.out.println("La registration n'est associée à aucune team.");
            }
        } else {
            System.out.println("Aucune registration trouvée avec l'ID fourni.");
        }
    }
}

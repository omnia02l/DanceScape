package org.sid.ebankingbackend.services;



import org.sid.ebankingbackend.entities.Registration;
import org.sid.ebankingbackend.entities.RegistrationDTO;

import java.security.Principal;
import java.util.Date;
import java.util.List;

public interface IRegistrationservice {
    List<Registration> retrieveAllRegistrations();

    Registration addRegistration(Registration r);

    Registration updateRegistration(Long id, Registration newRegistration);

    Registration retrieveRegistration(Long idreg);

    void removeRegistration(Long idreg);
    long countPendingRegistrations();
    int calculateAge(Date dateofbirthd);


     Registration addRegistrationWithTeamAndDancersassigncomp(RegistrationDTO registrationDTO, Long competitionId, Principal principal);
    void sendEmailsToDancersInTeamByRegistrationId(Long registrationId, String subject, String body);

}

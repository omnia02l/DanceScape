package org.sid.ebankingbackend.controllers;


import org.sid.ebankingbackend.entities.Registration;
import org.sid.ebankingbackend.entities.RegistrationDTO;
import org.sid.ebankingbackend.services.IRegistrationservice;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/registration")
@CrossOrigin("*")
public class RegistrationController {
    @Autowired
    IRegistrationservice iregservice;
    @GetMapping("/retrieve_all_regs")
    public List<Registration> getRegistrations() {
        List<Registration> listregs = iregservice.retrieveAllRegistrations();
        return listregs;
    }



    @PutMapping("/update_reg/{id}")
    public Registration updateRegistration(@PathVariable Long id, @RequestBody Registration newRegistration) {
        // Appeler la méthode de service pour mettre à jour la registration
        return iregservice.updateRegistration(id, newRegistration);
    }
    @GetMapping("/retrieve_reg/{id}")
    public Registration retrieveRegistration(@PathVariable Long id) {
        Registration reg = iregservice.retrieveRegistration(id);
        return reg;
    }

    @DeleteMapping("/remove_reg/{id}")
    public void removeRegistration(@PathVariable Long id) {
        iregservice.removeRegistration(id);
    }



    @GetMapping("/countPendingRegistrations")
    public long countPendingRegistrations() {
        return iregservice.countPendingRegistrations();
    }

    @PutMapping("/add_reg_with_comp_team_and_dancers/{competitionId}")
    public Registration addRegistrationWithTeamAndDancersAssignComp(@RequestBody RegistrationDTO registrationDTO, @PathVariable Long competitionId,
                                                                    Principal principal) {
        return iregservice.addRegistrationWithTeamAndDancersassigncomp(registrationDTO, competitionId,principal);
    }
    @PostMapping("/{registrationId}/send-emails")
    public void sendEmailsToDancersInTeam(@PathVariable Long registrationId,
                                          @RequestParam String subject,
                                          @RequestParam String body) {
        iregservice.sendEmailsToDancersInTeamByRegistrationId(registrationId, subject, body);
    }



}

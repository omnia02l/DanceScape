package org.sid.ebankingbackend.repository;

import org.sid.ebankingbackend.entities.Registration;
import org.sid.ebankingbackend.entities.Statusreg;
import org.springframework.data.jpa.repository.JpaRepository;



public interface RegistrationRepository extends JpaRepository<Registration,Long> {
    long countByStatusreg(Statusreg statusreg);
}

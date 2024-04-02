package org.sid.ebankingbackend.repository;

import org.sid.ebankingbackend.entities.Dancecategory;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;


public interface DancecategRepository extends JpaRepository<Dancecategory,Long> {

}

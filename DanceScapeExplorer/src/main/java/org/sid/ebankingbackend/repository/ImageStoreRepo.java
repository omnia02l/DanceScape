package org.sid.ebankingbackend.repository;

import org.sid.ebankingbackend.entities.ImageStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageStoreRepo extends JpaRepository<ImageStore,Integer> {
    List<ImageStore> findByOrderById();
}

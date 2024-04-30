package org.sid.ebankingbackend.repository;

import org.sid.ebankingbackend.entities.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PostImageRepo extends JpaRepository<PostImage,Long> {

    @Query(value = "select * from post_image where post_id=?1" ,nativeQuery = true)
    Optional<PostImage> getImage(Long id);
}

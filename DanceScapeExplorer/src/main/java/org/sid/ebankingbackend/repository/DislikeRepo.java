package org.sid.ebankingbackend.repository;

import org.sid.ebankingbackend.entities.Dislikes;
import org.sid.ebankingbackend.entities.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DislikeRepo extends JpaRepository<Dislikes,Long> {

    @Query(value = "select count(*) from dislikes where post_id=?1 AND user_name=?2",nativeQuery = true)
    int getDislikeNumber(Long id, String userName);

    @Query(value = "select * from dislikes where post_id=?1 AND user_name=?2",nativeQuery = true)
    Optional<Dislikes> getDislike(Long id, String userName);
}

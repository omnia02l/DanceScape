package org.sid.ebankingbackend.repository;

import org.sid.ebankingbackend.entities.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepo extends JpaRepository<Likes,Long> {

    @Query(value = "select count(*) from likes where post_id=?1 AND user_name=?2",nativeQuery = true)
    int getLikeNumber(Long id, String userName);

    @Query(value = "select * from likes where post_id=?1 AND user_name=?2",nativeQuery = true)
    Optional<Likes> getLike(Long id, String userName);
}

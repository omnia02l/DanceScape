package org.sid.ebankingbackend.repository;

import org.sid.ebankingbackend.entities.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface MusicRepository extends JpaRepository<Music,Long> {
    @Query("SELECT DISTINCT m FROM Music m " +
            "JOIN m.performances p" +
            " JOIN p.competition c " +
            "JOIN c.dancecateg dc" +
            " JOIN dc.dancestyles ds " +
            "WHERE dc.categdname = :danceCategory AND ds.styledname = :danceStyle")
    Music findBydanceCategAndStyle(String danceCategory, String danceStyle);
}



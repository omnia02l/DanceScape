package org.sid.ebankingbackend.repository;


import org.sid.ebankingbackend.entities.LikesDislikesDTO;
import org.sid.ebankingbackend.entities.Result;
import org.sid.ebankingbackend.entities.VoteStatsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result ,Long> {

    List<Result> findAllByDateRBetween(Date startDate, Date endDate);

    @Query("SELECT new org.sid.ebankingbackend.entities.VoteStatsDTO(AVG(v.score) as averageScore, COUNT(v) as totalVotes) " +
            "FROM Vote v WHERE v.performance.idperf = :performanceId")
    VoteStatsDTO calculateVoteStatistics(@Param("performanceId") Long performanceId);

    Optional<Result> findByPerformanceIdperf(Long performanceId);
    @Query("SELECT new org.sid.ebankingbackend.entities.LikesDislikesDTO(r.likes, r.dislikes) FROM Result r WHERE r.idResultat = :id")
    Optional<LikesDislikesDTO> findLikesAndDislikesById(@Param("id") Long id);

    @Query("SELECT SUM(r.likes) as totalLikes, SUM(r.dislikes) as totalDislikes FROM Result r")
    List<Object[]> findLikesAndDislikesTotals();

    <S extends Result> S saveAndFlush(S entity);

}

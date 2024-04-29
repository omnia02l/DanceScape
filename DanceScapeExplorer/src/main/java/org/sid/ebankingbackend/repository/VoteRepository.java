package org.sid.ebankingbackend.repository;

import org.sid.ebankingbackend.entities.Performance;
import org.sid.ebankingbackend.entities.Vote;
import org.sid.ebankingbackend.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface VoteRepository extends CrudRepository <Vote,Long> {


    @Query("SELECT v FROM Vote v WHERE v.performance.idperf = :performanceId AND FUNCTION('DATE', v.voteDate) = :date")
    List<Vote> findVotesByPerformanceAndDate(Long performanceId, Date date);

    List<Vote> findByPerformanceIdperf(Long performanceId);

    List<Vote> findByUserId(Long userId);


    @Query("SELECT v.role.name, COUNT(v) FROM Vote v GROUP BY v.role.name")
    List<Object[]> countVotesByType();
    boolean existsByUserIdAndPerformanceIdperf(Long userId, Long performanceId);

    Optional<Vote> findByUserIdAndPerformanceIdperf(Long userId, Long performanceId);

    List<Vote> findAllByUserId(Long userId);

}

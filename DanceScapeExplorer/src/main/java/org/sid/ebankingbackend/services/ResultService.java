package org.sid.ebankingbackend.services;

import org.sid.ebankingbackend.entities.LikesDislikesDTO;
import org.sid.ebankingbackend.entities.Result;
import org.sid.ebankingbackend.entities.VoteStatsDTO;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ResultService {

     List<Result> getAllResults() ;

     void updateResult(Long resultId) ;

     VoteStatsDTO getVoteStatistics(Long resultId) ;

        Result likeResult(Long resultId) ;
      Result dislikeResult(Long resultId) ;

      Result getResultById(Long id);
     Optional<Result> getResultsByPerformanceId(Long performanceId);
    public Optional<LikesDislikesDTO> getLikesAndDislikesById(Long id) ;
    Result saveOrUpdateResult(Result result, Long userId);
     void deleteResult(Long id);
     List<Result> getResultsBetweenDates(Date startDate, Date endDate) ;
     Result calculateResultForPerformance(Long performanceId) ;
}

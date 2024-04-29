package org.sid.ebankingbackend.services;

import org.sid.ebankingbackend.entities.Performance;
import org.sid.ebankingbackend.entities.Typevotes;
import org.sid.ebankingbackend.entities.Vote;
import org.sid.ebankingbackend.models.ERole;

import java.util.List;

public interface VoteService {

     Vote addVote(Long userId ,Long performanceId, Vote vote);
     List<Vote> getAllVotes();

    public List<Vote> findVotesByUserId(Long userid);

     Vote getVoteById(Long id);

     List<Object[]> getVoteStatistics();

    List<Vote> getVotesByPerformance(Long idperf) ;
    void deleteVote(Long id);

      Vote updateVote(Long userId, Long performanceId, Vote updatedVote) ;

      List<Vote> getAllVotesByUserId(Long userId) ;




     }



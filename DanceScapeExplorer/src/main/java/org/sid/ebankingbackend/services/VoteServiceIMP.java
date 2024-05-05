package org.sid.ebankingbackend.services;

import jakarta.transaction.Transactional;
import org.sid.ebankingbackend.entities.Performance;
import org.sid.ebankingbackend.entities.Result;
import org.sid.ebankingbackend.entities.Typevotes;
import org.sid.ebankingbackend.models.ERole;
import org.sid.ebankingbackend.entities.Vote;
import org.sid.ebankingbackend.models.Role;
import org.sid.ebankingbackend.models.User;
import org.sid.ebankingbackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VoteServiceIMP implements VoteService {

    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private ResultRepository rp;
    @Autowired
    private ResultService resultService;
    @Autowired
    private ResultRepository resultRepository ;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PerformanceRepository performanceRepository;

    @Transactional
    public Vote addVote(Long userId, Long performanceId, Vote vote) {
        // Fetch or load the associated performance
        Performance performance = performanceRepository.findById(performanceId)
                .orElseThrow(() -> new RuntimeException("Performance not found"));

        // Attempt to fetch an existing result, or create a new one if none exists
        Result result = resultRepository.findByPerformanceIdperf(performance.getIdperf())
                .orElseGet(() -> {
                    Result newResult = new Result();
                    newResult.setPerformance(performance);
                    return resultRepository.save(newResult);
                });

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        vote.setUser(user);

        Role role = roleRepository.findByName(ERole.valueOf(user.getUserRole()))
                .orElseThrow(() -> new RuntimeException("Role not found"));
        vote.setRole(role);

        assignWeightToVote(vote);

        vote.setPerformance(performance);
        vote.setResult(result);
        vote.setVoteDate(Date.valueOf(LocalDate.now()));

        Vote savedVote = voteRepository.save(vote);
        resultService.calculateResultForPerformance(performanceId);

        return savedVote;
    }
    public int getTotalWeightedScoreForPerformance(Long performanceId) {
        List<Vote> votes = voteRepository.findByPerformanceIdperf(performanceId);
        if (votes == null || votes.isEmpty()) {
            return 0;  // Return 0 if there are no votes
        }
        return votes.stream()
                .mapToInt(vote -> vote.getScore() * vote.getWeight())
                .sum();
    }


    private void assignWeightToVote(Vote vote) {
        User user = vote.getUser();
        if (user != null && user.getUserRole() != null) {
            // Assuming getUserRole() returns a String, we need to convert it to an ERole enum
            ERole role  = ERole.valueOf(user.getUserRole());

            switch (role) {
                case jury:
                    vote.setWeight(3);
                    break;
                case dancer:
                    vote.setWeight(2);
                    break;
                case registred_user:
                default:
                    vote.setWeight(1);
                    break;
            }
        }
    }

    public List<Vote> findVotesByUserId(Long userid) {
        return (List<Vote>) voteRepository.findByUserId(userid);
    }

    public List<Object[]> getVoteStatistics() {
        return voteRepository.countVotesByType();
    }
    public List<Vote> getVotesByPerformance(Long idperf) {
        return voteRepository.findByPerformanceIdperf(idperf);
    }
    public List<Vote> getAllVotes() {
        return (List<Vote>) voteRepository.findAll();
    }

    public Vote getVoteById(Long id) {
        return voteRepository.findById(id).orElse(null);
    }

    public void deleteVote(Long id) {
        voteRepository.deleteById(id);
    }

    public List<Vote> getAllVotesByUserId(Long userId) {
        return voteRepository.findAllByUserId(userId);
    }

    public Vote updateVote(Long userId, Long performanceId, Vote updatedVote) {
        Vote existingVote = voteRepository.findByUserIdAndPerformanceIdperf(userId, performanceId)
                .orElseThrow(() -> new RuntimeException("Vote not found"));

        existingVote.setScore(updatedVote.getScore());

        Vote savedVote = voteRepository.save(existingVote);

        resultService.calculateResultForPerformance(performanceId);

        return savedVote;
    }

    public void deleteVote(Long userId, Long performanceId) {
        Vote voteToDelete = voteRepository.findByUserIdAndPerformanceIdperf(userId, performanceId)
                .orElseThrow(() -> new RuntimeException("Vote not found"));

        voteRepository.delete(voteToDelete);

        resultService.calculateResultForPerformance(performanceId);
    }

    public List<Vote> getAllVotesWithUserVote(Long userId) {
        List<Vote> allVotes = getAllVotes();
        List<Vote> userVotes = getAllVotesByUserId(userId);

        List<Vote> allVotesWithUserVote = new ArrayList<>(allVotes);
        allVotesWithUserVote.addAll(userVotes);

        return allVotesWithUserVote;
    }
}

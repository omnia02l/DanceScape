package org.sid.ebankingbackend.services;


import jakarta.transaction.Transactional;
import org.sid.ebankingbackend.entities.*;
import org.sid.ebankingbackend.repository.PerformanceRepository;
import org.sid.ebankingbackend.repository.ResultRepository;
import org.sid.ebankingbackend.repository.VoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ResultServiceIMP implements ResultService {

 @Autowired
 private PerformanceRepository performanceRepository ;
    @Autowired
    private ResultRepository resultRepository ;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    public ResultServiceIMP(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    public List<Result> getAllResults() {
        return (List<Result>) resultRepository.findAll();
    }

    public Result getResultById(Long id) {
        return resultRepository.findById(id).orElse(null);
    }

    public Result saveOrUpdateResult(Result result, Long userId) {
        return resultRepository.save(result);
    }

    public void deleteResult(Long id) {
        resultRepository.deleteById(id);
    }


    public List<Result> getResultsBetweenDates(Date startDate, Date endDate) {
        return resultRepository.findAllByDateRBetween(startDate, endDate);
    }

    public VoteStatsDTO getVoteStatistics(Long resultId) {
        Result result = resultRepository.findById(resultId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid result ID: " + resultId));

        double averageScore = result.getVotes().stream()
                .mapToInt(Vote::getScore)
                .average()
                .orElse(0.0);

        long totalVotes = result.getVotes().size();

        return new VoteStatsDTO(averageScore, totalVotes);
    }

    public void updateResult(Long resultId) {
        Result result = resultRepository.findById(resultId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid result ID: " + resultId));

        int totalScore = result.getVotes().stream().mapToInt(Vote::getScore).sum();
        int voteCount = result.getVotes().size();
        float averageScore = voteCount > 0 ? (float) totalScore / voteCount : 0;

        result.setResultat(averageScore);
        result.setNombresvotes(voteCount);
        resultRepository.save(result);
    }

    @Transactional
    @Override
    public Result calculateResultForPerformance(Long performanceId) {
        Logger logger = LoggerFactory.getLogger(getClass());
        Performance performance = performanceRepository.findById(performanceId).orElse(null);
        if (performance == null) {
            logger.error("Performance not found for id: {}", performanceId);
            throw new IllegalArgumentException("Performance not found for id: " + performanceId);
        }

        List<Vote> votes = voteRepository.findByPerformanceIdperf(performanceId);
        if (votes.isEmpty()) {
            logger.info("No votes found for performance id: {}", performanceId);
            return handleNoVotes(performance);
        }

        float totalScore = votes.stream().mapToInt(Vote::getScore).sum();
        Result result = resultRepository.findByPerformanceIdperf(performanceId).orElse(new Result());
        result.setResultat(votes.isEmpty() ? 0 : totalScore / votes.size());
        result.setNombresvotes(votes.size());
        result.setDateR(new Date());
        result.setPerformance(performance);
        return resultRepository.save(result);
    }

    private Result handleNoVotes(Performance performance) {
        Result result = new Result();
        result.setResultat(0);
        result.setNombresvotes(0);
        result.setDateR(new Date());
        result.setPerformance(performance);
        return resultRepository.save(result);
    }
    @Transactional
    public Result likeResult(Long resultId) {
        Result result = resultRepository.findById(resultId).orElse(null);
        if (result != null) {
            result.setLikes(result.getLikes() + 1);
            return resultRepository.save(result);
        }
        return null;
    }

    public Optional<Result> getResultsByPerformanceId(Long performanceId) {
        return resultRepository.findByPerformanceIdperf(performanceId);
    }
    public Optional<LikesDislikesDTO> getLikesAndDislikesById(Long id) {
        return resultRepository.findLikesAndDislikesById(id);
    }
    @Transactional
    public Result dislikeResult(Long resultId) {
        Result result = resultRepository.findById(resultId).orElse(null);
        if (result != null) {
            result.setDislikes(result.getDislikes() + 1);
            return resultRepository.save(result);
        }
        return null;
    }


}

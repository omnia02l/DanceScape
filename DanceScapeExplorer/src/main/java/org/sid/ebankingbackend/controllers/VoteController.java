package org.sid.ebankingbackend.controllers;

import org.sid.ebankingbackend.entities.Vote;
import org.sid.ebankingbackend.entities.VoteStatsDTO;
import org.sid.ebankingbackend.repository.ResultRepository;
import org.sid.ebankingbackend.repository.VoteRepository;
import org.sid.ebankingbackend.services.ResultService;
import org.sid.ebankingbackend.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("/*")
@RequestMapping("/votes")
public class VoteController {
    @Autowired
    private VoteService voteService;

    @Autowired
    private VoteRepository voteRepository ;

    @Autowired
    private ResultService resultService ;

    @GetMapping("/getAll")
    public ResponseEntity<List<Vote>> getAllVotes() {
        List<Vote> votes = voteService.getAllVotes();
        return new ResponseEntity<>(votes, HttpStatus.OK);
    }

    @GetMapping("/votes/user/{userId}")
    public ResponseEntity<List<Vote>> getVotesByUser(@PathVariable Long userId) {
        try {
            List<Vote> userVotes = voteService.findVotesByUserId(userId);
            return new ResponseEntity<>(userVotes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/statistics")
    public List<Object[]> getVoteStatistics() {
        return voteService.getVoteStatistics();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vote> getVoteById(@PathVariable("id") Long id) {
        Vote vote = voteService.getVoteById(id);
        return new ResponseEntity<>(vote, vote != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/AddV/{performanceId}/{userId}")
    public ResponseEntity<?> addVote(@PathVariable Long performanceId,
                                     @PathVariable Long userId,
                                     @RequestBody Vote vote) {
        try {
            // Assuming the score is part of the Vote object received in the request
            Vote savedVote = voteService.addVote(userId, performanceId, vote);
            return new ResponseEntity<>(savedVote, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Catching a more general exception that could be thrown by either the service or the repository layers
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteVote(@PathVariable("id") Long id) {
        if (voteService.getVoteById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        voteService.deleteVote(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{performanceId}/{userId}")
    public ResponseEntity<?> updateVote(@PathVariable Long performanceId,
                                        @PathVariable Long userId,
                                        @RequestBody Vote updatedVote) {
        try {
            Vote vote = voteService.updateVote(userId, performanceId, updatedVote);
            return new ResponseEntity<>(vote, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/statistics/{performanceId}")
    public ResponseEntity<VoteStatsDTO> getVoteStatistics(@PathVariable Long performanceId) {
        VoteStatsDTO stats = resultService.getVoteStatistics(performanceId);
        return ResponseEntity.ok(stats);
    }


    @GetMapping("votes/calendrier/{idperf}")
    public ResponseEntity<List<Vote>> getVotesByPerformance(@PathVariable Long idperf) {
        return ResponseEntity.ok(voteService.getVotesByPerformance(idperf));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Vote>> getAllVotesByUser(@PathVariable("userId") Long userId) {
        List<Vote> userVotes = voteService.getAllVotesByUserId(userId);
        return new ResponseEntity<>(userVotes, HttpStatus.OK);
    }
}

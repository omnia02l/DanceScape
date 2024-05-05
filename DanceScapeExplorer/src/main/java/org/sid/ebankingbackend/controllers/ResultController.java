package org.sid.ebankingbackend.controllers;


import org.sid.ebankingbackend.entities.LikesDislikesDTO;
import org.sid.ebankingbackend.entities.Result;
import org.sid.ebankingbackend.services.ResultService;
import org.sid.ebankingbackend.services.ResultServiceIMP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin("/*")
@RestController
@RequestMapping("/results")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @Autowired
    public ResultController(ResultServiceIMP resultServiceIMP) {
        this.resultService = resultServiceIMP;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Result>> getAllResults() {
        List<Result> results = resultService.getAllResults();
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/getby/{id}")
    public ResponseEntity<Result> getResultById(@PathVariable("id") Long id) {
        Result result = resultService.getResultById(id);
        return new ResponseEntity<>(result, result != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/like/{resultId}")
    public ResponseEntity<Result> likeResult(@PathVariable Long resultId) {
        Result result = resultService.likeResult(resultId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/dislike/{resultId}")
    public ResponseEntity<Result> dislikeResult(@PathVariable Long resultId) {
        Result result = resultService.dislikeResult(resultId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/addResultat/{userId}")
    public ResponseEntity<Result> saveOrUpdateResult(@RequestBody Result result,@PathVariable Long userId ) {
        Result newResult = resultService.saveOrUpdateResult(result,userId);
        return new ResponseEntity<>(newResult, HttpStatus.CREATED);
    }

    @PostMapping("/{resultId}/update")
    public ResponseEntity<?> updateResult(@PathVariable Long resultId) {
        try {
            resultService.updateResult(resultId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<Void> deleteResult(@PathVariable("id") Long id) {
        if (resultService.getResultById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        resultService.deleteResult(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/likes-dislikes/{id}")
    public Optional<LikesDislikesDTO> getLikesAndDislikesById(@PathVariable Long id) throws Exception {
        return resultService.getLikesAndDislikesById(id);
    }

    @GetMapping("/byPerformance/{performanceId}")
    public ResponseEntity<Optional<Result>> getResultsByPerformance(@PathVariable Long performanceId) {
        Optional<Result> results = resultService.getResultsByPerformanceId(performanceId);
        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(results);
    }


    @GetMapping("/between")
    public ResponseEntity<List<Result>> getResultsBetweenDates(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        List<Result> results = resultService.getResultsBetweenDates(startDate, endDate);
        return ResponseEntity.ok(results);
    }

    @PostMapping("/calculateR/{performanceId}")
    public Result calculateResultForPerformance(@PathVariable Long performanceId) {
        return resultService.calculateResultForPerformance(performanceId);
    }



}
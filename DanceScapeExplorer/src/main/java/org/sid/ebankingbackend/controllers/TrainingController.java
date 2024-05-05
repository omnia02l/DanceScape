package org.sid.ebankingbackend.controllers;

import org.sid.ebankingbackend.entities.*;
import org.sid.ebankingbackend.payload.request.UpdateTrainingRequest;
import org.sid.ebankingbackend.payload.response.TrainingResponse;
import org.sid.ebankingbackend.payload.response.UpdateTrainingDatesRequest;
import org.sid.ebankingbackend.services.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/training")
public class TrainingController {

    @Autowired
    TrainingService trainingService;

    @PostMapping("/create-dance-hall")
    private String createDanceHall(@RequestBody DanceHall danceHall){
        return this.trainingService.addDanceHall(danceHall);
    }

    @GetMapping("/list-dance-halls")
    private List<DanceHall> listDanceHalls(){
        return this.trainingService.listDanceHall();
    }

    @PostMapping("/update-dance-hall-status")
    private String updateDanceHallStatus(@RequestParam Long danceHallId){
        return this.trainingService.updateDanceHallStatus(danceHallId);
    }

    @DeleteMapping("/delete-dance-hall")
    private String deleteDanceHall(@RequestParam Long danceHallId){
        return this.trainingService.deleteDanceHall(danceHallId);
    }

    @GetMapping("/list-coaches")
    private List<String> listCoaches(){
        return this.trainingService.listCoaches();
    }

    @GetMapping("/list-hall-names")
    private List<String> listDanceHallNames(){
        return this.trainingService.listDanceHallNames();
    }

    @PostMapping("/create-training")
    private String createTraining(@RequestBody Training training){
        return this.trainingService.createTraining(training);
    }

    @GetMapping("/list-training")
    private List<Training> listTraining(){
        return this.trainingService.listTraining();
    }

    @GetMapping("/training-response")
    private TrainingResponse getTrainingResponse(@RequestParam Long trainingId){
        return this.trainingService.getTrainingResponse(trainingId);
    }

    @PostMapping("/update-training-dates")
    private String updateTrainingDates(@RequestBody UpdateTrainingDatesRequest updateTrainingDatesRequest){
        return this.trainingService.updateTrainingDates(updateTrainingDatesRequest);
    }

    @GetMapping("if-joined")
    private Boolean ifJoined(@RequestParam Long id, Principal principal){
        return this.trainingService.ifJoined(id,principal.getName());
    }

    @PostMapping("/join-training")
    private String joinTraining(@RequestParam Long id, Principal principal){
        return this.trainingService.joinTraining(id,principal.getName());
    }

    @PostMapping("/cancel-training")
    private String cancelTraining(@RequestParam Long id, Principal principal){
        return this.trainingService.cancelTraining(id,principal.getName());
    }

    @DeleteMapping("/delete-training")
    private String deleteTraining(@RequestParam Long id){
        return this.trainingService.deleteTraining(id);
    }

    @PostMapping("/update-training")
    private String updateTraining(@RequestParam Long id, @RequestBody UpdateTrainingRequest updateTrainingRequest){
        return this.trainingService.updateTraining(id,updateTrainingRequest);
    }

    @GetMapping("/stats")
    private Stats getStats() {
        return this.trainingService.getStats();
    }


    @GetMapping("/list-all-coach")
    private List<CoachStatus> listAllCoach(){
        return this.trainingService.listAllCoach();
    }

    @PostMapping("/change-coach-status")
    private String changeCoachStatus(@RequestParam Long id){
        return this.trainingService.changeCoachStatus(id);
    }

    @GetMapping("/list-training-with-category/{category}")
    private List<Training> listTrainingWithCategory(@PathVariable(value = "category") String c){
        return this.trainingService.listTrainingWithCategory(c);
    }

    @GetMapping("/stats-with-cat")
    private TrainingStatsWithCat getStatsWithCat(){
        return this.trainingService.getTrainingStatsWithCat();
    }
}

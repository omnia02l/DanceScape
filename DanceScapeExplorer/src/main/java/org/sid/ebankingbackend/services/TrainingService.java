package org.sid.ebankingbackend.services;

import org.sid.ebankingbackend.entities.*;
import org.sid.ebankingbackend.payload.request.UpdateTrainingRequest;
import org.sid.ebankingbackend.payload.response.TrainingResponse;
import org.sid.ebankingbackend.payload.response.UpdateTrainingDatesRequest;

import java.time.Instant;
import java.util.List;

public interface TrainingService {

    String addDanceHall(DanceHall danceHall);

    List<DanceHall> listDanceHall();

    String updateDanceHallStatus(Long danceHallId);

    String deleteDanceHall(Long danceHallId);

    List<String> listCoaches();

    List<String> listDanceHallNames();

    String createTraining(Training training);

    List<Training> listTraining();

    TrainingResponse getTrainingResponse(Long id);

    String updateTrainingDates(UpdateTrainingDatesRequest updateTrainingDatesRequest);

    Boolean ifJoined(Long id, String userName);

    String joinTraining(Long id, String userName);

    String cancelTraining(Long id, String userName);

    String deleteTraining(Long id);

    String updateTraining(Long id,UpdateTrainingRequest updateTrainingRequest);

    Stats getStats();

    List<CoachStatus> listAllCoach();

    String changeCoachStatus(Long coachId);

    List<Training> listTrainingWithCategory(String category);

    TrainingStatsWithCat getTrainingStatsWithCat();
}

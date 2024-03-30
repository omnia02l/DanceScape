package org.sid.ebankingbackend.payload.response;

import lombok.Data;
import org.sid.ebankingbackend.entities.DanceHall;
import org.sid.ebankingbackend.entities.Training;
import org.sid.ebankingbackend.entities.TrainingParticipant;
import org.sid.ebankingbackend.repository.DanceHallRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
public class TrainingResponse {

    private Long id;
    private String trainingName;
    private String trainingDescription;
    private Instant start;
    private Instant end;
    private Long capacity;
    private String danceHallName;
    private String danceHallAddress;
    private String coachName;

    private List<TrainingParticipant> trainingParticipantList = new ArrayList<>();

    public TrainingResponse(Training training, String danceHallAddress, List<TrainingParticipant> trainingParticipantList) {
        this.id = training.getId();
        this.trainingName = training.getTrainingName();
        this.trainingDescription = training.getDescription();
        this.start = training.getStart();
        this.end = training.getEnd();
        this.capacity = training.getCapacity();
        this.danceHallName = training.getDanceHallName();
        this.danceHallAddress = danceHallAddress;
        this.coachName = training.getCoachName();
        this.trainingParticipantList = trainingParticipantList;
    }
}

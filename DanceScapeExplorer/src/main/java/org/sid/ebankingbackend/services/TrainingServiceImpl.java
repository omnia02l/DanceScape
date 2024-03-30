package org.sid.ebankingbackend.services;

import jakarta.transaction.Transactional;
import org.sid.ebankingbackend.entities.DanceHall;
import org.sid.ebankingbackend.entities.DanceHallStatus;
import org.sid.ebankingbackend.entities.Training;
import org.sid.ebankingbackend.entities.TrainingParticipant;
import org.sid.ebankingbackend.error.BadRequestException;
import org.sid.ebankingbackend.models.ERole;
import org.sid.ebankingbackend.models.User;
import org.sid.ebankingbackend.payload.request.UpdateTrainingRequest;
import org.sid.ebankingbackend.payload.response.TrainingResponse;
import org.sid.ebankingbackend.payload.response.UpdateTrainingDatesRequest;
import org.sid.ebankingbackend.repository.DanceHallRepo;
import org.sid.ebankingbackend.repository.TrainingParticipantRepo;
import org.sid.ebankingbackend.repository.TrainingRepo;
import org.sid.ebankingbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TrainingServiceImpl implements TrainingService {

    @Autowired
    private DanceHallRepo danceHallRepo;
    @Autowired
    private TrainingRepo trainingRepo;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TrainingParticipantRepo trainingParticipantRepo;

    @Override
    public String addDanceHall(DanceHall danceHall) {
        if (this.danceHallRepo.existsByHallName(danceHall.getHallName()))
            throw new BadRequestException("Dance hall name Invalid");
        this.danceHallRepo.save(danceHall);
        return "Dance hall saved";
    }

    @Override
    public List<DanceHall> listDanceHall() {
        return this.danceHallRepo.findAll();
    }

    @Override
    public String updateDanceHallStatus(Long danceHallId) {
        DanceHall danceHall = this.danceHallRepo.findById(danceHallId).get();
        if (DanceHallStatus.AVAILABLE.equals(danceHall.getDanceHallStatus())) {
            danceHall.setDanceHallStatus(DanceHallStatus.NOT_AVAILABLE);
        } else {
            danceHall.setDanceHallStatus(DanceHallStatus.AVAILABLE);
        }
        this.danceHallRepo.save(danceHall);
        return "Dance hall status updated";
    }

    @Override
    public String deleteDanceHall(Long danceHallId) {
        this.danceHallRepo.deleteById(danceHallId);
        return "Dance hall deleted";
    }

    @Override
    public List<String> listCoaches() {
        return this.userRepository.findAll()
                .stream().filter(user -> user.getUserRole().equals(ERole.coach.toString()))
                .map(User::getUsername)
                .toList();
    }

    @Override
    public List<String> listDanceHallNames() {
        return this.danceHallRepo.findAll()
                .stream()
                .filter(danceHall -> DanceHallStatus.AVAILABLE.equals(danceHall.getDanceHallStatus()))
                .map(DanceHall::getHallName).toList();
    }

    @Override
    public String createTraining(Training training) {
        if (training.getStart().isBefore(Instant.now()))
            throw new BadRequestException("Start date invalid");
        DanceHall danceHall = this.danceHallRepo.findByHallName(training.getDanceHallName());
        training.setCapacity(danceHall.getCapacity());
        this.trainingRepo.save(training);
        return "Training created";
    }

    @Override
    public List<Training> listTraining() {
        return this.trainingRepo.findAll();
    }

    @Override
    public TrainingResponse getTrainingResponse(Long id) {
        Training training = this.trainingRepo.findById(id).get();
        return new TrainingResponse(training,
                this.getDanceHallAddress(training.getDanceHallName()),
                this.getTrainingParticipant(id).orElse(new ArrayList<>()));
    }

    @Override
    public String updateTrainingDates(UpdateTrainingDatesRequest updateTrainingDatesRequest) {
        if (updateTrainingDatesRequest.getStart().isBefore(Instant.now()))
            throw new BadRequestException("Start date invalid");
        Training training = this.trainingRepo.findById(updateTrainingDatesRequest.getId()).get();
        training.setStart(updateTrainingDatesRequest.getStart());
        training.setEnd(updateTrainingDatesRequest.getEnd());
        this.trainingRepo.save(training);
        return "Training dates updated";
    }

    @Override
    public Boolean ifJoined(Long id, String userName) {
        return this.trainingParticipantRepo.existsByTrainingIdAndParticipantName(id,userName);
    }

    @Transactional
    @Override
    public String joinTraining(Long id, String userName) {
        User user = this.userRepository.findByUsername(userName).get();
        Training training = this.trainingRepo.findById(id).get();
        DanceHall danceHall = this.danceHallRepo.findByHallName(training.getDanceHallName());
                if(danceHall.getDanceHallStatus().equals(DanceHallStatus.NOT_AVAILABLE))
                    throw new BadRequestException("Sorry, the dance hall is not available. We will contact you later");
        if(Instant.now().isAfter(training.getStart()) || training.getCapacity() == 0)
            throw new BadRequestException("Training already started OR no place available");
        training.setCapacity(training.getCapacity() - 1);
        if(training.getCapacity() == 0){
            training.setColor("#d8363a");
        }
        this.trainingRepo.save(training);
        TrainingParticipant trainingParticipant = new TrainingParticipant();
        trainingParticipant.setParticipantName(userName);
        trainingParticipant.setPhoneNumber(user.getPhoneNumber());
        trainingParticipant.setTrainingId(id);
        this.trainingParticipantRepo.save(trainingParticipant);
        return "Request accepted";
    }

    @Transactional
    @Override
    public String cancelTraining(Long id, String userName) {
        Training training = this.trainingRepo.findById(id).get();
        training.setCapacity(training.getCapacity() + 1);
        if(training.getCapacity()>=1){
            training.setColor("#007bff");
        }
        this.trainingRepo.save(training);
        this.trainingParticipantRepo.cancelTraining(id,userName);
        return "Request accepted";
    }

    @Override
    public String deleteTraining(Long id) {
        this.trainingRepo.deleteById(id);
        this.trainingParticipantRepo.deleteTraining(id);
        return "Training deleted";
    }

    @Override
    public String updateTraining(Long id,UpdateTrainingRequest updateTrainingRequest) {
        Training training = this.trainingRepo.findById(id).get();
        training.setTrainingName(updateTrainingRequest.getName());
        training.setDescription(updateTrainingRequest.getDescription());
        training.setCoachName(updateTrainingRequest.getCoachName());
        this.trainingRepo.save(training);
        return "training updated";
    }

    private String getDanceHallAddress(String danceHallName) {
        return this.danceHallRepo.findByHallName(danceHallName)
                .getHallAddress();
    }

    private Optional<List<TrainingParticipant>> getTrainingParticipant(Long id){
        return this.trainingParticipantRepo.getTrainingParticipants(id);
    }
}

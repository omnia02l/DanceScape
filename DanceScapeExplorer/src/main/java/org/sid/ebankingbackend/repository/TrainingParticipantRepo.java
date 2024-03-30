package org.sid.ebankingbackend.repository;

import jakarta.transaction.Transactional;
import org.sid.ebankingbackend.entities.TrainingParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingParticipantRepo extends JpaRepository<TrainingParticipant,Long> {

    @Query(value = "select * from training_participant where training_id=?1",nativeQuery = true)
    Optional<List<TrainingParticipant>> getTrainingParticipants(Long TrainingId);

    Boolean existsByTrainingIdAndParticipantName(Long id, String userName);

    @Modifying
    @Transactional
    @Query(value = "Delete from training_participant where training_id =?1 AND participant_name=?2",nativeQuery = true)
    void cancelTraining(Long id, String userName);

    @Modifying
    @Transactional
    @Query(value = "Delete from training_participant where training_id =?1",nativeQuery = true)
    void deleteTraining(Long id);

}

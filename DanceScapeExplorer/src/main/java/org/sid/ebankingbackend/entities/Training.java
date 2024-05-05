package org.sid.ebankingbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trainingName;

    private String description;

    private Instant start;

    private Instant end;

    private String color = "#007bff";

    private Long capacity;

    private String danceHallName;

    private String coachName;

    @Enumerated(EnumType.STRING)
    private TrainingCategory trainingCategory;
}

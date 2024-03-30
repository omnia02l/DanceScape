package org.sid.ebankingbackend.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTrainingDatesRequest {
    private Long id;
    private Instant start;
    private Instant end;
}

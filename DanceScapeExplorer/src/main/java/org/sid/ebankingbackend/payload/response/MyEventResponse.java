package org.sid.ebankingbackend.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.ebankingbackend.models.Event;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyEventResponse {
    private Event event;
    private Boolean status;
}

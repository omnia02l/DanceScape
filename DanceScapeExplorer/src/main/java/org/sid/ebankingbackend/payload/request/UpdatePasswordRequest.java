package org.sid.ebankingbackend.payload.request;

import lombok.Data;

@Data
public class UpdatePasswordRequest {

    private String newPassword;
    private String confirmation;
}

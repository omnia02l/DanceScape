package org.sid.ebankingbackend.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.ebankingbackend.models.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String userName;
    private String firsName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String role;
    private Boolean status;

    public UserDTO(User user) {
        this.id = user.getId();
        this.userName = user.getUsername();
        this.firsName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.role = user.getRoles().stream().toList().get(0).getName().name();
        this.status = user.getDisabled();
    }
}

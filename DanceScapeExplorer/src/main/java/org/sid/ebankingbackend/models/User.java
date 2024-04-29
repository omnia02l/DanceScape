package org.sid.ebankingbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.ebankingbackend.entities.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(	name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String phoneNumber;

    private Boolean disabled = false;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore
    private Set<Vote> votes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    @JsonIgnore
    private Set<Orders> orders;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    @JsonIgnore
    private Set<Registration> Registrations;

    @OneToOne
    @JsonIgnore
    private PaymentGateway paymentgateway;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    @JsonIgnore
    private Set<Claim> Claims;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    @JsonIgnore
    private Set<ResultComment> resultComments;

    @OneToOne
    @JsonIgnore
    private Agreement agreement;

    public User(String username, String firstName, String lastName, String phoneNumber, String email, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    public String getUserRole(){
        return this.getRoles().stream().toList().get(0).getName().toString();
    }
}

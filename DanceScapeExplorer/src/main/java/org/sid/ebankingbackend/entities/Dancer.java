package org.sid.ebankingbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
//@Table( name = "Dancer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dancer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long iddancer;
    private String dfirstname;
    private String dlastname;
    @Temporal(TemporalType.DATE)
    private Date dateofbirthd;
    private int age;
    @Enumerated(EnumType.STRING)
    private Gender dgender;
    private String demail;

    @JsonIgnore
    @ManyToOne
    private Team team;
}
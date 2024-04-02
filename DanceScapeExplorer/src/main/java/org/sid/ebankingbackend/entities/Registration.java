package org.sid.ebankingbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.sid.ebankingbackend.models.User;

import java.io.Serializable;
import java.util.Date;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Table( name = "Regestration")


public class Registration implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idreg;
    @Temporal(TemporalType.DATE)
    private Date Registration_date ;
    @Enumerated(EnumType.STRING)
    private Statusreg statusreg = Statusreg.Pending;
    @Column(nullable = true)
    private String Videolink;
    @Lob
    @Column(name = "videofile", columnDefinition = "BLOB",nullable = true)
    private byte[] videofile;
    @Column(nullable = true)
    private double amountpaid;
    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private Date Approved_date;
    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private Date Rejected_date;
    @Column(nullable = true)
    private String username;
    @JsonIgnore
    @ManyToOne
    private  User user;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private  Team team;



}

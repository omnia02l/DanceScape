package org.sid.ebankingbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.sid.ebankingbackend.models.User;

import java.io.Serializable;
import java.util.Date;


@Entity
@Getter
@Setter
//@Table( name = "Regestration")


public class Registration implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idreg;
    @Temporal(TemporalType.DATE)
    private Date Registration_date ;
    @Enumerated(EnumType.STRING)
    private Statusreg Statusreg;
    private String Videolink;
    @Lob
    @Column(name = "videofile", columnDefinition = "BLOB")
    private byte[] videofile;
    private double amountpaid;
    @Temporal(TemporalType.DATE)
    private Date Approved_date;
    @Temporal(TemporalType.DATE)
    private Date Rejected_date;
    @JsonIgnore
    @ManyToOne
     private User user;
    @JsonIgnore
    @ManyToOne
    private  Team team;



}

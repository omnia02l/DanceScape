package org.sid.ebankingbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sid.ebankingbackend.models.User;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Table( name = "Resultcomment")
public class ResultComment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCom ;
    private String comment ;
    @Temporal(TemporalType.DATE)
    private Date datecom ;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    Result result ;

    @ManyToOne
    @JsonIgnore
    private User user ;

}

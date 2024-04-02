package org.sid.ebankingbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


@Entity
//@Table( name = "Danncestyle")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dancestyle implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idstyled;
    private String styledname;
    private String styledesc;
    @Temporal(TemporalType.DATE)
    private Date sadddate;

}

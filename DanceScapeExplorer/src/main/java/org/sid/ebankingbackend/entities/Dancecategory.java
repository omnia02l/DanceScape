package org.sid.ebankingbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;


@Entity
// @Table( name = "Dancecategory")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dancecategory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idcategd;
    private String categdname;
    private String categdesc;
    private  boolean showStyles ;
    @Temporal(TemporalType.DATE)

    private Date cadddate;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Dancestyle> dancestyles ;
    // Constructeur prenant un argument de type int (entier)
    public Dancecategory(int idcategd) {
        this.idcategd = (long) idcategd;
    }

}

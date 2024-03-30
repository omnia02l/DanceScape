package org.sid.ebankingbackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;


@Entity
   // @Table( name = "Dancecategory")
    @Getter
    @Setter
    public class Dancecategory implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)

        private Long idcategd;
        private String categdname;
        private String categdesc;
        @Temporal(TemporalType.DATE)

        private Date cadddate;

        @OneToMany(cascade = CascadeType.ALL)
        private Set<Dancestyle> dancestyles ;


}

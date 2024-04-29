package org.sid.ebankingbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;


@Entity
//@Table( name = "Music")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Music implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idmusic;
    private String musicname;
    private String artistname;
    private String categdesc;
    @Temporal(TemporalType.DATE)
    private Date ReleaseDate;
    private String musiclink;

    @Column(nullable = true)
    private String audiofile;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "music")
    @JsonIgnore
    private Set<Performance>  performances;

}


package org.sid.ebankingbackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;



    @Entity
    //@Table( name = "Music")
    @Getter
    @Setter
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
        @Lob
        @Column(name = "audiofile", columnDefinition = "BLOB")
        private byte[] audiofile;



    }

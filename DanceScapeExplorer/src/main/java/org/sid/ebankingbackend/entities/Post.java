package org.sid.ebankingbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Table( name = "Post")
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idPost")
    private Long idPost;
    private String titlePost;
    private String descriptionPost;
    @Temporal(TemporalType.DATE)
    private Date pubdate;
    private String imagePost;
    private boolean isliked;

    @OneToMany(mappedBy = "post")
    private Set<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="post")
    private Set<Interaction> Interactions;



}

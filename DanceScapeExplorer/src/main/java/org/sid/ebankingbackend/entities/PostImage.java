package org.sid.ebankingbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iD;
    private String imageType;
    @Lob
    private byte[] imageData;
    private Long postId;

    public PostImage(String imagetype, byte[] imagedata, Long postId) {
        this.imageType = imagetype;
        this.imageData = imagedata;
        this.postId = postId;
    }
}

package com.telerikacademy.beertag.models;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Images")
public class Image {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageId;

    @Lob
    @Column(name = "Bytes", columnDefinition = "BLOB")
    private MultipartFile bytes;

    public Image(MultipartFile bytes) {
        this.bytes = bytes;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public MultipartFile getBytes() {
        return bytes;
    }

    public void setBytes(MultipartFile bytes) {
        this.bytes = bytes;
    }
}

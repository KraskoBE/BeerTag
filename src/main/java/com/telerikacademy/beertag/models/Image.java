package com.telerikacademy.beertag.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;


@Entity
@Table(name = "Images")
public class Image {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageId;

    @Lob
    @Column(name = "Bytes", columnDefinition = "MEDIUMBLOB")
    @JsonIgnore
    private byte[] bytes;

    public Image() {
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public byte[] getBytes() {
        return decompress(bytes);
    }

    public void setBytes(byte[] bytes) {
        this.bytes = compress(bytes);
    }


    private static byte[] compress(byte[] in) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DeflaterOutputStream deflate = new DeflaterOutputStream(out);
            deflate.write(in);
            deflate.flush();
            deflate.close();

            System.out.println("Original: " + in.length/1024);
            System.out.println("Compressed: " + out.size()/1024);

            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] decompress(byte[] in) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InflaterOutputStream inflate = new InflaterOutputStream(out);
            inflate.write(in);
            inflate.flush();
            inflate.close();

            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

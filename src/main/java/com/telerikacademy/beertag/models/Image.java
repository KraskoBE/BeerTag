package com.telerikacademy.beertag.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Where(clause = "enabled=1")
@Table(name = "images")
public class Image {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private int id;

    @Lob
    @Column(name = "bytes", columnDefinition = "MEDIUMBLOB")
    @JsonIgnore
    private byte[] bytes;

    @NotNull
    @JsonIgnore
    @Column(name = "enabled")
    private boolean enabled = true;

    public Image(byte[] bytes) {
        this.bytes = compress(bytes);
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
            DeflaterOutputStream defl = new DeflaterOutputStream(out);
            defl.write(in);
            defl.flush();
            defl.close();

            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] decompress(byte[] in) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InflaterOutputStream infl = new InflaterOutputStream(out);
            infl.write(in);
            infl.flush();
            infl.close();

            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

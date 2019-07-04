package com.telerikacademy.beertag.models.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    private String name;
    private String email;
    private String password;
}

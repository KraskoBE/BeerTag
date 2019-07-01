package com.telerikacademy.beertag.controllers;

import com.telerikacademy.beertag.models.AuthenticationRequest;
import com.telerikacademy.beertag.repositories.UserRepository;
import com.telerikacademy.beertag.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private JwtProvider provider;
    private UserRepository repository;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtProvider provider, UserRepository repository) {
        this.authenticationManager = authenticationManager;
        this.provider = provider;
        this.repository = repository;
    }

    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody AuthenticationRequest data) {
        try {
            String email = data.getEmail();
            //authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, data.getPassword()));

            String token = provider.createToken(email,
                    Collections.singletonList(this.repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Username " + email + "not found")).getUserRole().toString()));

            Map<Object, Object> model = new HashMap<>();
            model.put("email", email);
            model.put("token", token);
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity singup(@RequestBody AuthenticationRequest data) {
        return null;
    }
}














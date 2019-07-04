package com.telerikacademy.beertag.controllers;

import com.telerikacademy.beertag.models.DTO.AuthenticationRequest;
import com.telerikacademy.beertag.models.User;
import com.telerikacademy.beertag.repositories.UserRepository;
import com.telerikacademy.beertag.security.JwtProvider;
import com.telerikacademy.beertag.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider provider;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JwtProvider provider,
                                    UserRepository userRepository,
                                    UserService userService,
                                    ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.provider = provider;
        this.userRepository = userRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @CrossOrigin(origins = "http://localhost:80")
    @GetMapping("/signin")
    public ResponseEntity signin(@RequestParam("email") final String email, @RequestParam("password") String password, HttpServletResponse response) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid username/password"));

            String token = provider.createToken(email,
                    Collections.singletonList(user.getUserRole().name()));

            Map<Object, Object> model = new HashMap<>();
            model.put("email", email);
            model.put("token", token);

            Cookie cookie = new Cookie("access_token", token);
            cookie.setPath("/");
            cookie.setDomain("localhost");

            response.addCookie(cookie);
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody final AuthenticationRequest user) {
        return userService.register(modelMapper.map(user, User.class))
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/validate")
    public ResponseEntity validate(@RequestParam final String token) {
        try {
            provider.validateToken(token);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}














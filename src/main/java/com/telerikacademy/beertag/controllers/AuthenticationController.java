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
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin(origins = "http://localhost",
        allowCredentials = "true",
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST})
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

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody final AuthenticationRequest userAuth, HttpServletResponse response) {
        try {
            String email = userAuth.getEmail();
            String password = userAuth.getPassword();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid username/password"));

            String token = provider.createToken(user);

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
    public ResponseEntity register(@RequestBody final AuthenticationRequest user, HttpServletResponse response) {
        Optional<User> newUser = userService.register(modelMapper.map(user, User.class));

        if (!newUser.isPresent())
            return ResponseEntity.badRequest().build();

        String token = provider.createToken(newUser.get());

        Cookie cookie = new Cookie("access_token", token);
        cookie.setPath("/");
        cookie.setDomain("localhost");

        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/validate")
    public ResponseEntity validate(@RequestParam final String token) {
        try {
            provider.validateToken(token);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}














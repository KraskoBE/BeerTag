package com.telerikacademy.beertag.security;

import com.telerikacademy.beertag.repositories.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository users;
    public CustomUserDetailsService(UserRepository users) {
        this.users = users;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.users.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
    }
}

package com.telerikacademy.beertag.models.constants;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    Member,
    Admin;

    @Override
    public String getAuthority() {
        return name();
    }
}

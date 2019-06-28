package com.telerikacademy.beertag.models;

import com.telerikacademy.beertag.models.constants.UserRole;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "UsersAuth")
public class UserAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserAuthId")
    private int userAuthId;

    @NotNull
    @Column(name = "Password")
    private String password;

    @NotNull
    @Column(name = "Email", unique = true)
    private String email;

    private boolean enabled;

    private UserRole userRole;

    @JoinColumn(name = "UserId", unique = true)
    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    public UserAuth() {
    }

    public int getUserAuthId() {
        return userAuthId;
    }

    public void setUserAuthId(int userAuthId) {
        this.userAuthId = userAuthId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}

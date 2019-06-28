package com.telerikacademy.beertag.models;

import com.telerikacademy.beertag.models.constants.UserRole;

public class FullUserDTO {

    private String email;
    private String password;
    private String name;
    private int age;
    private boolean enabled;
    private UserRole userRole;

    public FullUserDTO() {
    }

    public FullUserDTO(String email, String password, String name, int age) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
    }

    public static User toUser(FullUserDTO fullUserDTO) {
        User user = new User();
        user.setName(fullUserDTO.getName());
        user.setAge(fullUserDTO.getAge());
        return user;
    }

    public static UserAuth toUserAuth(FullUserDTO fullUserDTO)
    {
        UserAuth userAuth = new UserAuth();

        userAuth.setEmail(fullUserDTO.getEmail());
        userAuth.setPassword(fullUserDTO.getPassword());
        userAuth.setEnabled(fullUserDTO.isEnabled());
        userAuth.setUserRole(fullUserDTO.getUserRole());

        return userAuth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

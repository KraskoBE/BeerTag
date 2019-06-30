package com.telerikacademy.beertag.models;

import com.telerikacademy.beertag.models.constants.UserRole;
import lombok.Data;

@Data
public class FullUserDTO {

    private String email;
    private String password;
    private String name;
    private int age;
    private boolean enabled;
    private UserRole userRole;

    public static User toUser(FullUserDTO fullUserDTO) {
        User user = new User();
        user.setName(fullUserDTO.getName());
        user.setAge(fullUserDTO.getAge());

        return user;
    }

   /*public static UserAuth toUserAuth(FullUserDTO fullUserDTO) {
        UserAuth userAuth = new UserAuth();

        userAuth.setEmail(fullUserDTO.getEmail());
        userAuth.setPassword(fullUserDTO.getPassword());
        userAuth.setEnabled(fullUserDTO.isEnabled());
        userAuth.setUserRole(fullUserDTO.getUserRole());

        return userAuth;
    }*/
}

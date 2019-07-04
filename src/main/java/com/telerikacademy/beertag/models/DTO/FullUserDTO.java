package com.telerikacademy.beertag.models.DTO;

import com.telerikacademy.beertag.models.User;
import com.telerikacademy.beertag.models.constants.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}

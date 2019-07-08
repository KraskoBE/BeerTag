package com.telerikacademy.beertag.controllers.UserControllerTests;


import com.telerikacademy.beertag.controllers.JsonHelper;
import com.telerikacademy.beertag.models.User;
import com.telerikacademy.beertag.models.constants.UserRole;
import com.telerikacademy.beertag.repositories.UserRepository;
import com.telerikacademy.beertag.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@Secured("Member")
public class UserControllerSaveTests {


    @Mock
    UserService mockUserService;

    @Mock
    UserRepository userRepo;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser("ROLE_Member")
    public void save_Should_Return_StatusOk_When_Successful() throws Exception {
        //Arrange
        final String EMAIL = "test@email.com";
        User user = new User();
        user.setId(1);
        user.setEmail(EMAIL);
        user.setUserRole(UserRole.Member);

        System.out.println("             sss" + user.getAuthorities());

        Mockito.when(mockUserService.save(user)).thenReturn(Optional.of(user));

        //Act & Assert
        mockMvc.perform(post("/api/users/")
                //.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrcmFzZW52YWNoZXYzQGdtYWlsLmNvbSIsInVzZXJfaWQiOjEsInJvbGVzIjoiTWVtYmVyIiwibmFtZSI6IktyYXNlbiIsImltYWdlSWQiOjEsImlhdCI6MTU2MjQ5MDg0MiwiZXhwIjoxNTYyNTc3MjQyfQ.fvf-4WbNuQBSK-30P5CFDsPAentMgKLhEq5a41RHe2g")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonHelper.convertObjectToJson(user)))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$.email").value(EMAIL));
    }

    @Test
    @WithMockUser(authorities = "ROLE_Member")
    public void save_Should_Return_BadRequest_When_PassingInvalidUser() throws Exception {
        //Arrange
        final String EMAIL = "test@email.com";
        User user = new User();
        user.setId(1);
        user.setEmail(EMAIL);
        user.setUserRole(UserRole.Member);

        Mockito.when(mockUserService.save(user)).thenReturn(Optional.empty());

        //Act & Assert
        mockMvc.perform(post("/api/users/").contentType(MediaType.APPLICATION_JSON)
                .content(JsonHelper.convertObjectToJson(user)))
                .andExpect(status().isOk())
                .andExpect(status().isBadRequest());
    }

}

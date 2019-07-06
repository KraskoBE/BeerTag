package com.telerikacademy.beertag.controllers.UserControllerTests;


import com.telerikacademy.beertag.models.User;
import com.telerikacademy.beertag.repositories.UserRepository;
import com.telerikacademy.beertag.services.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerGetTests {

    @MockBean
    UserServiceImpl mockUserService;

    @Mock
    UserRepository userRepo;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(roles={"USER","ADMIN"})
    public void findById_Should_Return_StatusOk_When_Successful() throws Exception {
        //Arrange
        final String EMAIL = "test@email.com";
        User user = new User();
        user.setId(1);
        user.setEmail(EMAIL);

        Mockito.when(mockUserService.findById(1)).thenReturn(Optional.of(user));

        //Act & Assert
        mockMvc.perform(get("/api/users/{id}",1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(EMAIL));
    }

    @Test
    @WithMockUser(roles={"USER","ADMIN"})
    public void findById_Should_Return_StatusNotFound_When_UserDoesntExist() throws Exception {
        //Arrange
        final String EMAIL = "test@email.com";
        User user = new User();
        user.setId(1);
        user.setEmail(EMAIL);

        Mockito.when(mockUserService.findById(1)).thenReturn(Optional.empty());

        //Act & Assert
        mockMvc.perform(get("/api/users/{id}",1))
                .andExpect(status().isNotFound());
    }

}












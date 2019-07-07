package com.telerikacademy.beertag.controllers.UserControllerTests;


import com.telerikacademy.beertag.controllers.JsonHelper;
import com.telerikacademy.beertag.models.User;
import com.telerikacademy.beertag.models.constants.UserRole;
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
import org.springframework.http.MediaType;
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
public class UserControllerSaveTests {


    @MockBean
    UserServiceImpl mockUserService;

    @Mock
    UserRepository userRepo;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(roles={"USER","ADMIN"})
    public void save_Should_Return_StatusOk_When_Successful() throws Exception {
        //Arrange
        final String EMAIL = "test@email.com";
        User user = new User();
        user.setId(1);
        user.setEmail(EMAIL);
        user.setUserRole(UserRole.Member);

        Mockito.when(mockUserService.save(user)).thenReturn(Optional.of(user));

        //Act & Assert
        mockMvc.perform(post("/api/users/").contentType(MediaType.APPLICATION_JSON)
                .content(JsonHelper.convertObjectToJson(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(EMAIL));
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

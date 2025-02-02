package org.blog.service;

import org.testconfiguration.UserServiceTestConf;
import org.blog.dto.user.UserCreateDto;
import org.blog.dto.user.UserResponseDto;
import org.blog.model.User;
import org.blog.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringJUnitConfig(classes = UserServiceTestConf.class)
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void resetMocks() {
        reset(userRepository);
    }

    @Test
    public void addUserTest() {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setFirstName("testFirstName");
        userCreateDto.setPwd("testPwd");
        userCreateDto.setLogin("testLogin");
        userCreateDto.setLastName("testLastName");
        when(userRepository.existsByLogin(userCreateDto.getLogin())).thenReturn(false);

        userService.addUser(userCreateDto);

        verify(userRepository, times(1)).save(any(User.class));
        verify(userRepository, times(1)).existsByLogin(userCreateDto.getLogin());
    }

    @Test
    public void addUserWhenUserExist() {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setFirstName("testFirstName");
        userCreateDto.setPwd("testPwd");
        userCreateDto.setLogin("testLogin");
        userCreateDto.setLastName("testLastName");
        HttpStatus expectedStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        when(userRepository.existsByLogin(userCreateDto.getLogin())).thenReturn(true);

        ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> userService.addUser(userCreateDto));

        assertEquals(e.getMessage(), expectedStatus.toString());
        verify(userRepository, times(0)).save(any(User.class));
        verify(userRepository, times(1)).existsByLogin(userCreateDto.getLogin());
    }

    @Test
    public void loginUserTest() {
        String login = "testLogin";
        String password = "testPwd";
        UserResponseDto exeptedUserResponseDto = new UserResponseDto();
        exeptedUserResponseDto.setFirstName("testFirstName");
        exeptedUserResponseDto.setLastName("testLastName");
        exeptedUserResponseDto.setUserId(1L);
        when(userRepository.findUserByLoginAndPwd(login,password)).thenReturn(Optional.of(exeptedUserResponseDto));

        UserResponseDto actualUserResponseDto = userService.loginUser(login, password);

        assertEquals(actualUserResponseDto, exeptedUserResponseDto);
        verify(userRepository, times(1)).findUserByLoginAndPwd(login,password);
    }

    @Test
    public void loginUserTestWhenUserNotFound() {
        String login = "testLogin";
        String password = "testPwd";
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        when(userRepository.findUserByLoginAndPwd(login,password)).thenReturn(Optional.empty());

        ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> userService.loginUser(login, password));

        assertEquals(e.getMessage(), expectedStatus.toString());
        verify(userRepository, times(1)).findUserByLoginAndPwd(login,password);
    }

    @Test
    public void existUserTest() {
        when(userRepository.existsById(1L)).thenReturn(true);

        boolean actualResponse = userService.existUser(1L);

        assertTrue(actualResponse);
        verify(userRepository, times(1)).existsById(1L);
    }
}

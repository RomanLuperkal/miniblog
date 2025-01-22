package org.blog;

import org.blog.configuration.UserServiceTestConf;
import org.blog.mapper.UserMapper;
import org.blog.model.User;
import org.blog.repository.UserRepository;
import org.blog.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserServiceTestConf.class)
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void resetMocks() {
        reset(userRepository);
    }

    @Test
    public void test() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        System.out.println(userRepository.findById(1L));
    }

    @Test
    public void test2() {
        System.out.println(userRepository.findById(1L));
    }
}

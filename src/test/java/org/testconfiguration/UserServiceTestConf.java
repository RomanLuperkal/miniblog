package org.testconfiguration;

import org.blog.miniblog.mapper.UserMapper;
import org.blog.miniblog.mapper.UserMapperImpl;
import org.blog.miniblog.repository.UserRepository;
import org.blog.miniblog.service.UserService;
import org.blog.miniblog.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.mockito.Mockito.mock;

@Configuration
public class UserServiceTestConf {

    @Bean
    public UserRepository getUserRepository() {
        return mock(UserRepository.class);
    }

    @Bean
    public UserMapper getUserMapper() {
        return new UserMapperImpl();
    }

    @Bean
    public UserService getUserService(UserRepository  userRepository, UserMapper userMapper) {
        return new UserServiceImpl(userRepository, userMapper);
    }
}

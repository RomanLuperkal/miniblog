package org.blog.configuration;

import org.blog.mapper.UserMapper;
import org.blog.repository.UserRepository;
import org.blog.service.UserService;
import org.blog.service.impl.UserServiceImpl;
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
        return mock(UserMapper.class);
    }

    @Bean
    public UserService getUserService(UserRepository  userRepository, UserMapper userMapper) {
        return new UserServiceImpl(userRepository, userMapper);
    }
}

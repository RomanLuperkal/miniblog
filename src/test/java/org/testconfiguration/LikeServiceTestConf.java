package org.testconfiguration;


import org.blog.miniblog.repository.LikeRepository;
import org.blog.miniblog.service.LikeService;
import org.blog.miniblog.service.PostService;
import org.blog.miniblog.service.UserService;
import org.blog.miniblog.service.impl.LikeServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class LikeServiceTestConf {
    @Bean
    public LikeService likeService(LikeRepository likeRepository, UserService userService, PostService postService) {
        return new LikeServiceImpl(likeRepository, userService, postService);
    }

    @Bean
    public LikeRepository getLikeRepository() {
        return mock(LikeRepository.class);
    }

    @Bean
    public UserService getUserService() {
        return mock(UserService.class);
    }

    @Bean
    public PostService getPostService() {
        return mock(PostService.class);
    }
}

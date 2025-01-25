package org.testconfiguration;

import org.blog.repository.LikeRepository;
import org.blog.service.LikeService;
import org.blog.service.PostService;
import org.blog.service.UserService;
import org.blog.service.impl.LikeServiceImpl;
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

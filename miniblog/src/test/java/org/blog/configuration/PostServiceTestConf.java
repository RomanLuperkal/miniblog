package org.blog.configuration;

import org.blog.mapper.*;
import org.blog.repository.PostRepository;
import org.blog.service.PostService;
import org.blog.service.TagService;
import org.blog.service.impl.PostServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class PostServiceTestConf {

    @Bean
    public PostMapper getPostMapper() {
        return new PostMapperImpl();
    }

    @Bean
    public CommentMapper getCommentMapper() {
        return new CommentMapperImpl();
    }

    @Bean
    public TagMapper getTagMapper() {
        return new TagMapperImpl();
    }

    @Bean
    public PostRepository getPostRepository() {
        return mock(PostRepository.class);
    }

    @Bean
    public TagService getTagService() {
        return mock(TagService.class);
    }

    @Bean
    public PostService getPostService(PostMapper postMapper, TagService tagService, PostRepository postRepository,
                                      TagMapper tagMapper) {
        return new PostServiceImpl(postMapper, postRepository, tagService, tagMapper);
    }
}

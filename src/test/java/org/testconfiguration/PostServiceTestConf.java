package org.testconfiguration;


import org.blog.miniblog.mapper.*;
import org.blog.miniblog.repository.PostRepository;
import org.blog.miniblog.service.PostService;
import org.blog.miniblog.service.TagService;
import org.blog.miniblog.service.impl.PostServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@Configuration
public class PostServiceTestConf {

    @Bean
    public PostMapper getPostMapper() {
        return spy(new PostMapperImpl());
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
    public PostService getPostService(PostMapper postMapper, TagService tagService, PostRepository postRepository) {
        return new PostServiceImpl(postMapper, postRepository, tagService);
    }
}

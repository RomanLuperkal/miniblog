package org.blog.configuration;

import org.blog.mapper.CommentMapper;
import org.blog.mapper.CommentMapperImpl;
import org.blog.repository.CommentRepository;
import org.blog.service.CommentService;
import org.blog.service.impl.CommentServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class CommentServiceTestConf {

    @Bean
    public CommentRepository commentRepository() {
        return mock(CommentRepository.class);
    }

    @Bean
    public CommentMapper commentMapper() {
        return new CommentMapperImpl();
    }

    @Bean
    public CommentService commentService(CommentRepository commentRepository, CommentMapper commentMapper) {
        return new CommentServiceImpl(commentRepository, commentMapper);
    }
}

package org.testconfiguration;

import org.blog.miniblog.service.CommentService;
import org.blog.miniblog.service.impl.CommentServiceImpl;
import org.blog.miniblog.mapper.CommentMapper;
import org.blog.miniblog.mapper.CommentMapperImpl;
import org.blog.miniblog.repository.CommentRepository;
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

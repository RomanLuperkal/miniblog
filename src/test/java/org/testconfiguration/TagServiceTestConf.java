package org.testconfiguration;

import org.blog.miniblog.repository.TagRepository;
import org.blog.miniblog.service.TagService;
import org.blog.miniblog.service.impl.TagServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class TagServiceTestConf {
    @Bean
    public TagService tagService(TagRepository tagRepository) {
        return new TagServiceImpl(tagRepository);
    }

    @Bean
    public TagRepository tagRepository() {
        return mock(TagRepository.class);
    }
}

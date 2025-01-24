package org.blog.configuration;

import org.blog.repository.TagRepository;
import org.blog.service.TagService;
import org.blog.service.impl.TagServiceImpl;
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

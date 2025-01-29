package org.blog.miniblog.service;

import org.blog.miniblog.repository.TagRepository;
import org.testconfiguration.TagServiceTestConf;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@SpringJUnitConfig(classes = TagServiceTestConf.class)
public class TagServiceTest {
    @Autowired
    private TagService tagService;
    @Autowired
    private TagRepository tagRepository;

    @Test
    public void deleteTagsTest() {
        tagService.deleteTags(List.of(1L, 2L));

        verify(tagRepository, times(1)).deleteAllById(List.of(1L, 2L));
    }

}

package org.blog.miniblog.service;

import org.apache.commons.collections4.list.TreeList;
import org.blog.miniblog.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@SpringBootTest
public class TagServiceTest {
    @Autowired
    private TagService tagService;
    @MockBean
    private TagRepository tagRepository;

    @Test
    public void deleteTagsTest() {
        tagService.deleteTags(List.of(1L, 2L));
        verify(tagRepository, times(1)).deleteAllById(List.of(1L, 2L));
    }

}

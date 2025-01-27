package org.blog.service.impl;

import lombok.RequiredArgsConstructor;
import org.blog.repository.TagRepository;
import org.blog.service.TagService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    public void deleteTags(List<Long> tagIds) {
        tagRepository.deleteAllById(tagIds);
    }
}

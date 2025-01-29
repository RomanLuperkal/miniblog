package org.blog.miniblog.service.impl;

import lombok.RequiredArgsConstructor;
import org.blog.miniblog.repository.TagRepository;
import org.blog.miniblog.service.TagService;
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

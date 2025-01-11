package org.blog.service.impl;

import lombok.RequiredArgsConstructor;
import org.blog.dto.post.PostCreateDto;
import org.blog.mapper.PostMapper;
import org.blog.model.Post;
import org.blog.repository.PostRepository;
import org.blog.service.PostService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostMapper postMapper;
    private final PostRepository postRepository;

    @Override
    public void createPostCreate(PostCreateDto postCreateDto) {
        Post newPost = postMapper.postCreateDtoToPost(postCreateDto);
        postRepository.save(newPost);
    }
}

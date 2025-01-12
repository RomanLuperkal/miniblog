package org.blog.service.impl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.blog.dto.post.PostCreateDto;
import org.blog.dto.post.PostResponseDto;
import org.blog.dto.user.UserResponseDto;
import org.blog.mapper.PostMapper;
import org.blog.model.Post;
import org.blog.repository.PostRepository;
import org.blog.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostMapper postMapper;
    private final PostRepository postRepository;

    @Override
    public void createPostCreate(PostCreateDto postCreateDto, HttpSession session) {
        Post newPost = postMapper.postCreateDtoToPost(postCreateDto);
        UserResponseDto user = (UserResponseDto) session.getAttribute("user");
        newPost.setOwnerId(user.getUserId());
        postRepository.save(newPost);
    }

    @Override
    public List<PostResponseDto> getPosts() {
        return postMapper.postListToPostResponseDtoList(postRepository.findAll());
    }
}

package org.blog.service;

import jakarta.servlet.http.HttpSession;
import org.blog.dto.post.PostCreateDto;
import org.blog.dto.post.PostResponseDto;

import java.util.List;

public interface PostService {
    void createPostCreate(PostCreateDto postCreateDto, HttpSession session);
    List<PostResponseDto> getPosts(List<String> tags);
}

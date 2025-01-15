package org.blog.service;

import jakarta.servlet.http.HttpSession;
import org.blog.dto.post.ListPostResponseDto;
import org.blog.dto.post.PostCreateDto;
import org.blog.dto.post.PostResponseDto;

import java.util.List;

public interface PostService {

    void createPostCreate(PostCreateDto postCreateDto, HttpSession session);
    ListPostResponseDto getPosts(List<String> tags, int from, int size);
    PostResponseDto getPost(Long postId);
}

package org.blog.miniblog.service;

import jakarta.servlet.http.HttpSession;
import org.blog.miniblog.dto.post.FullPostResponseDto;
import org.blog.miniblog.dto.post.ListPostResponseDto;
import org.blog.miniblog.dto.post.PostCreateDto;
import org.blog.miniblog.dto.post.UpdatePostDto;


import java.util.List;

public interface PostService {

    void createPost(PostCreateDto postCreateDto, HttpSession session);

    ListPostResponseDto getPosts(List<String> tags, int from, int size);

    FullPostResponseDto getPost(Long postId);

    void deletePost(Long postId);

    FullPostResponseDto updatePost(Long postId, UpdatePostDto updatePostDto);

    void calculateLikes(int value, Long postId);
}

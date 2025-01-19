package org.blog.service;

import jakarta.servlet.http.HttpSession;
import org.blog.dto.like.LikeResponseDto;
import org.blog.dto.post.FullPostResponseDto;
import org.blog.dto.post.ListPostResponseDto;
import org.blog.dto.post.PostCreateDto;

import java.util.List;

public interface PostService {

    void createPostCreate(PostCreateDto postCreateDto, HttpSession session);
    ListPostResponseDto getPosts(List<String> tags, int from, int size);
    FullPostResponseDto getPost(Long postId);
    void deletePost(Long postId);
    LikeResponseDto likePost(Long postId, Long userId);
}

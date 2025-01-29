package org.blog.miniblog.service;


import org.blog.miniblog.dto.like.LikeResponseDto;

public interface LikeService {
    LikeResponseDto likePost(Long postId, Long userId);
}

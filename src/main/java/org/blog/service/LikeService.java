package org.blog.service;

import org.blog.dto.like.LikeResponseDto;

public interface LikeService {
    LikeResponseDto likePost(Long postId, Long userId);
}

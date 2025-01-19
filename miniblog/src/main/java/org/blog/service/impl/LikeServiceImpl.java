package org.blog.service.impl;

import lombok.RequiredArgsConstructor;
import org.blog.dto.like.LikeResponseDto;
import org.blog.model.Like;
import org.blog.repository.LikeRepository;
import org.blog.service.LikeService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;

    @Override
    public LikeResponseDto likePost(Long postId, Long userId) {
        Like.LikeKey likeKey = new Like.LikeKey();
        likeKey.setPostId(postId);
        likeKey.setUserId(userId);
        Optional<Like> likePost = likeRepository.findById(likeKey);
        LikeResponseDto likeResponseDto = new LikeResponseDto();

        if (likePost.isPresent()) {
            likeRepository.delete(likePost.get());
            likeResponseDto.setMessage("Ваш лайк удален");
            return likeResponseDto;
        }

        Like newLike = new Like();
        newLike.setLikeKey(likeKey);
        likeRepository.save(newLike);
        likeResponseDto.setMessage("Вы лайкнули пост");
        return likeResponseDto;
    }
}

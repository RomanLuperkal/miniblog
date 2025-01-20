package org.blog.service.impl;

import lombok.RequiredArgsConstructor;
import org.blog.dto.like.LikeResponseDto;
import org.blog.model.Like;
import org.blog.repository.LikeRepository;
import org.blog.service.LikeService;
import org.blog.service.PostService;
import org.blog.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final UserService userService;
    private final PostService postService;

    @Override
    public LikeResponseDto likePost(Long postId, Long userId) {
        if (!userService.existUser(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с id=" + userId + " не найден");
        }
        Optional<Like> likePost = likeRepository.findByLikeKey(userId, postId);

        return likePost.map(like -> deleteLike(like, postId)).orElseGet(() -> addLike(userId, postId));
    }

    private LikeResponseDto deleteLike(Like likePost, Long postId) {
        LikeResponseDto likeResponseDto = new LikeResponseDto();
        likeRepository.delete(likePost);
        postService.calculateLikes(-1, postId);
        likeResponseDto.setMessage("Ваш лайк удален");
        return likeResponseDto;
    }

    private LikeResponseDto addLike(Long userId, Long postId) {
        LikeResponseDto likeResponseDto = new LikeResponseDto();
        Like newLike = new Like();
        newLike.setUserId(userId);
        newLike.setPostId(postId);
        postService.calculateLikes(1, postId);
        likeRepository.save(newLike);
        likeResponseDto.setMessage("Вы лайкнули пост");
        return likeResponseDto;
    }
}

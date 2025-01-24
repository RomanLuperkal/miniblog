package org.blog.service;

import org.blog.base.TestBase;
import org.blog.configuration.LikeServiceTestConf;
import org.blog.model.Like;
import org.blog.repository.LikeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringJUnitConfig(classes = LikeServiceTestConf.class)
public class LikeServiceTest extends TestBase {
    @Autowired
    private LikeService likeService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeRepository likeRepository;

    @BeforeEach
    public void resetMocks() {
        reset(likeRepository, postService, userService);
    }

    @Test
    public void deleteLikeTest() {
        Like exeptedLike = getLike();
        when(userService.existUser(exeptedLike.getUserId())).thenReturn(true);
        when(likeRepository.findByLikeKey(exeptedLike.getUserId(), exeptedLike.getPostId()))
                .thenReturn(Optional.of(exeptedLike));
        doNothing().when(likeRepository).delete(exeptedLike);

        likeService.likePost(exeptedLike.getUserId(), exeptedLike.getPostId());

        verify(likeRepository, times(1)).delete(exeptedLike);
        verify(likeRepository, times(0)).save(any(Like.class));
        verify(postService, times(1)).calculateLikes(-1, exeptedLike.getPostId());
    }

    @Test
    public void addLikeTest() {
        Like exeptedLike = getLike();
        when(userService.existUser(exeptedLike.getUserId())).thenReturn(true);
        when(likeRepository.findByLikeKey(exeptedLike.getUserId(), exeptedLike.getPostId()))
                .thenReturn(Optional.empty());

        likeService.likePost(exeptedLike.getUserId(), exeptedLike.getPostId());

        verify(likeRepository, times(1)).save(exeptedLike);
        verify(likeRepository, times(0)).delete(any(Like.class));
        verify(postService, times(1)).calculateLikes(1, exeptedLike.getPostId());
    }

    @Test
    public void likePostTestWhenUserNotFound() {
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        when(userService.existUser(any(Long.class))).thenReturn(false);

        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> likeService.likePost(1L, 1L));

        assertEquals(e.getStatusCode(), expectedStatus);
        verify(likeRepository, times(0)).save(any(Like.class));
        verify(likeRepository, times(0)).delete(any(Like.class));
        verify(postService, times(0)).calculateLikes(any(Integer.class), any(Long.class));
    }
}

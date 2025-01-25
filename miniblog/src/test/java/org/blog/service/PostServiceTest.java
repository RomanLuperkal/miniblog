package org.blog.service;

import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.blog.base.TestBase;
import org.testconfiguration.PostServiceTestConf;
import org.blog.dto.post.*;
import org.blog.dto.user.UserResponseDto;
import org.blog.mapper.PostMapper;
import org.blog.model.Post;
import org.blog.repository.PostRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringJUnitConfig(classes = PostServiceTestConf.class)
public class PostServiceTest extends TestBase {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private PostMapper postMapper;

    private static HttpSession session;
    private static UserResponseDto userResponseDto;

    @BeforeAll
    @SneakyThrows
    public static void init() {
        userResponseDto = getUserResponseDto();

        session = new MockHttpSession();
        session.setAttribute("user", userResponseDto);

    }

    @BeforeEach
    public void resetMocks() {
        reset(postRepository, tagService);
    }

    @Test
    public void createPostTest() {
        PostCreateDto postCreateDto = getPostCreateDto();
        Post expectedPost = postMapper.postCreateDtoToPost(postCreateDto);
        expectedPost.setOwnerId(userResponseDto.getUserId());
        when(postRepository.save(expectedPost)).thenReturn(any(Post.class));

        postService.createPost(postCreateDto, session);

        verify(postRepository, times(1)).save(expectedPost);
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("provideTags")
    public void getPostsTest(List<String> tags) {
        Post expectedPost = getPost();
        Set<Post> expectedPosts = Set.of(expectedPost);
        int from = 0;
        int size = 10;
        List<PostResponseDto> postResponseDto = postMapper.postListToPostResponseDtoList(expectedPosts);
        ListPostResponseDto expectedPostResponseDto = new ListPostResponseDto();
        expectedPostResponseDto.setPosts(postResponseDto);
        when(postRepository.countPostByTags(tags, size, 0)).thenReturn(1L);
        when(postRepository.getPostsByTags(tags, size, 0)).thenReturn(expectedPosts);
        when(postMapper.postListToPostResponseDtoList(expectedPosts)).thenReturn(postResponseDto);
        when(postRepository.getPost(size, 0)).thenReturn(expectedPosts);

        ListPostResponseDto actualPosts = postService.getPosts(tags, from, size);

        assertEquals(actualPosts, expectedPostResponseDto);
    }

    @Test
    public void getPostTest() {
        Post expectedPost = getPost();
        FullPostResponseDto expectedPostResponseDto = fullPostResponseDto();
        when(postRepository.findPostWithComments(getPost().getPostId())).thenReturn(Optional.of(expectedPost));
        when(postMapper.postToFullPostResponseDto(expectedPost)).thenReturn(expectedPostResponseDto);

        FullPostResponseDto actualPost = postService.getPost(expectedPost.getPostId());

        assertEquals(actualPost, expectedPostResponseDto);
        verify(postRepository, times(1)).findPostWithComments(getPost().getPostId());
    }

    @Test
    public void getPostWhenPostNotFound() {
        Post expectedPost = getPost();
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        when(postRepository.findPostWithComments(getPost().getPostId())).thenReturn(Optional.empty());

        ResponseStatusException actualException = assertThrows(ResponseStatusException.class,
                () -> postService.getPost(expectedPost.getPostId()));

        assertEquals(actualException.getStatusCode(), expectedStatus);
        verify(postRepository, times(1)).findPostWithComments(getPost().getPostId());
    }

    @Test
    public void deletePostTest() {
        postService.deletePost(1L);
        verify(postRepository, times(1)).deleteById(1L);
    }

    @Test
    public void updatePostTest() {
        Post expectedPost = getPost();
        UpdatePostDto updatePostDto = getUpdatePostDto();
        FullPostResponseDto expectedPostResponseDto = fullPostResponseDto();
        when(postRepository.findPostWithComments(expectedPost.getPostId())).thenReturn(Optional.of(expectedPost));
        when(postRepository.save(expectedPost)).thenReturn(expectedPost);
        when(postMapper.postToFullPostResponseDto(expectedPost)).thenReturn(expectedPostResponseDto);

        FullPostResponseDto actualFullPostResponseDto = postService.updatePost(expectedPost.getPostId(), updatePostDto);

        assertEquals(actualFullPostResponseDto, expectedPostResponseDto);
        verify(postRepository, times(1)).findPostWithComments(expectedPost.getPostId());
        verify(postRepository, times(1)).save(expectedPost);
    }

    @Test
    public void calculateLikesTest() {
        Post expectedPost = getPost();
        Long likesCount = expectedPost.getLikesCount();
        when(postRepository.findById(expectedPost.getPostId())).thenReturn(Optional.of(expectedPost));

        postService.calculateLikes(1, expectedPost.getPostId());

        assertEquals(expectedPost.getLikesCount(), likesCount + 1);
        verify(postRepository, times(1)).findById(expectedPost.getPostId());
        verify(postRepository, times(1)).save(expectedPost);
    }

    @Test
    public void calculateLikesTestWhenPostNotFound() {
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> postService.calculateLikes(1, 1L));

        assertEquals(e.getStatusCode(), expectedStatus);
        verify(postRepository, times(1)).findById(1L);
        verify(postRepository, times(0)).save(any(Post.class));
    }

    static Stream<List<String>> provideTags() {
        return Stream.of(
                List.of("tag1", "tag2"),
                List.of()
        );
    }

}

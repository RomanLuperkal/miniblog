package org.blog.service;

import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.blog.base.PostTestBase;
import org.blog.configuration.PostServiceTestConf;
import org.blog.dto.post.ListPostResponseDto;
import org.blog.dto.post.PostCreateDto;
import org.blog.dto.post.PostResponseDto;
import org.blog.dto.user.UserResponseDto;
import org.blog.mapper.PostMapper;
import org.blog.model.Post;
import org.blog.repository.PostRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PostServiceTestConf.class)
public class PostServiceTest extends PostTestBase {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private PostMapper postMapper;

    private static HttpSession session ;
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

    @Test
    @SneakyThrows
    public void getPostTest() {
        Post expectedPost = getPost();
        List<String> expectedTags = List.of("tag1", "tag2");
        int from = 0;
        int size = 10;
        List<PostResponseDto> postResponseDto = postMapper.postListToPostResponseDtoList(Set.of(expectedPost));
        ListPostResponseDto expectedPostResponseDto = new ListPostResponseDto();
        expectedPostResponseDto.setPosts(postResponseDto);
        when(postRepository.countPostByTags(expectedTags, size, 0)).thenReturn(1L);
        when(postRepository.getPostsByTags(expectedTags, size, 0)).thenReturn(Set.of(expectedPost));

        ListPostResponseDto actualPosts = postService.getPosts(expectedTags, from, size);

        assertEquals(actualPosts, expectedPostResponseDto);
    }

    private static MockMultipartFile createMultipartFile(String resourcePath) throws IOException {
        var file = ResourceUtils.getFile("classpath:" + resourcePath);
        var fileContent = Files.readAllBytes(file.toPath());

        return new MockMultipartFile("image", file.getName(), "image/jpeg", fileContent);
    }

}

package org.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.blog.base.TestBase;
import org.blog.dto.comment.CreateCommentDto;
import org.blog.dto.comment.UpdateCommentDto;
import org.blog.dto.like.LikeResponseDto;
import org.blog.dto.post.PostCreateDto;
import org.blog.dto.post.UpdatePostDto;
import org.blog.dto.user.UserResponseDto;
import org.blog.model.Comment;
import org.blog.model.Post;
import org.blog.model.Tag;
import org.blog.repository.CommentRepository;
import org.blog.repository.PostRepository;
import org.blog.repository.TagRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testconfiguration.WebConfiguration;

import javax.sql.DataSource;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringJUnitWebConfig(classes = {WebConfiguration.class})
@TestPropertySource("classpath:application-test.properties")
public class PostControllerTest extends TestBase {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private CommentRepository commentRepository;

    private static MockHttpSession session;

    @BeforeAll
    @SneakyThrows
    public static void init() {
        session = new MockHttpSession();
        UserResponseDto user = new UserResponseDto();
        user.setUserId(1L);
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        session.setAttribute("user", user);
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void cleanRepository() {
        postRepository.deleteAll();
        tagRepository.deleteAll();
        commentRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    public void listPostsTest() {
        Post post = getPost();
        post.setPostId(null);
        postRepository.save(post);
        mockMvc.perform(get("/posts").session(session))

                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("post-list"))
                .andExpect(model().attributeExists("posts"))
                .andExpect(model().attributeExists("size"))
                .andExpect(model().attributeExists("from"))
                .andExpect(model().attributeExists("tags"))
                .andExpect(model().attributeExists("hasNext"))
                .andExpect(model().attributeExists("hasPrev"))
                .andExpect(xpath("//div[@class='post']").nodeCount(1))
                .andExpect(xpath("//div[@class='post'][1]//h2/a[text()='" + post.getPostName() + "']").exists())
                .andExpect(xpath("//div[@class='post'][1]//p[1][text()='" + post.getText() + "']").exists())
                .andExpect(xpath("//div[@class='post'][1]//p[2][text()='tag1,tag2']").exists())
                .andExpect(xpath("//div[@class='post'][1]//span[1][normalize-space(text())='0']").exists())
                .andExpect(xpath("//div[@class='post'][1]//span[2][normalize-space(text())='1']").exists())
                .andExpect(xpath("//img[@src]").exists());
    }

    @Test
    @SneakyThrows
    void addPostTest() {
        PostCreateDto postCreateDto = getPostCreateDto();
        mockMvc.perform(multipart("/posts/add").file(getMultipartFile()).session(session)
                        .param("postName", postCreateDto.getPostName())
                        .param("text", postCreateDto.getText())
                        .param("tags", "tag1", "tag2"))

                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
        Iterable<Post> actualPost = postRepository.findAll();
        assertTrue(actualPost.iterator().hasNext());
        actualPost.forEach(post -> {
            Tag expectedTag1 =  tagRepository.findById(1L).get();
            Tag expectedTag2 = tagRepository.findById(2L).get();
            assertEquals(post.getPostName(), postCreateDto.getPostName());
            assertEquals(post.getText(), postCreateDto.getText());
            assertEquals(post.getTags().size(), postCreateDto.getTags().size());
            assertEquals(expectedTag1.getTagName(), "tag1");
            assertEquals(expectedTag2.getTagName(), "tag2");
        });

    }

    @Test
    @SneakyThrows
    void getPostTest() {
        Post post = getPost();
        post.setPostId(null);
        Post savedPost = postRepository.save(post);
        mockMvc.perform(get("/posts/" + savedPost.getPostId()).session(session))

                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("post"))
                .andExpect(model().attributeExists("post"))
                .andExpect(xpath("//div[@class='post-content']").nodeCount(1))
                .andExpect(xpath("//div[@class='post-content']//h1[text()='" + post.getPostName() + "']").exists())
                .andExpect(xpath("//img[@src]").exists())
                .andExpect(xpath("//div[@class='post-content']//div[@class='tags mt-4']//span[normalize-space(text())='tag1,tag2']").exists());
    }

    @Test
    @SneakyThrows
    void deletePostTest() {
        Post post = getPost();
        post.setPostId(null);
        Post savedPost = postRepository.save(post);
        mockMvc.perform(post("/posts/" + savedPost.getPostId()).session(session)
                        .param("_method", "delete"))

                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
        Optional<Post> actualPost = postRepository.findById(savedPost.getPostId());
        assertFalse(actualPost.isPresent());
    }

    @Test
    @SneakyThrows
    void createCommentTest() {
        Post post = getPost();
        post.setPostId(null);
        post.setComments(null);
        Post savedPost = postRepository.save(post);
        CreateCommentDto createCommentDto = getCreateCommentDto();
        mockMvc.perform(post("/posts/" + savedPost.getPostId() + "/comments").session(session)
                        .param("postId", savedPost.getPostId().toString())
                        .param("ownerId", savedPost.getOwnerId().toString())
                        .param("text", createCommentDto.getText()))

                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/" + savedPost.getPostId()));
        Optional<Comment> actualComment = commentRepository.findById(1L);
        assertTrue(actualComment.isPresent());
        assertEquals(actualComment.get().getOwnerId(), 1L);
        assertEquals(actualComment.get().getPostId(), savedPost.getPostId());
        assertEquals(actualComment.get().getText(), createCommentDto.getText());
    }

    @Test
    @SneakyThrows
    void updateCommentTest() {
        Post post = getPost();
        post.setPostId(null);
        postRepository.save(post);
        UpdateCommentDto updateCommentDto = getUpdateCommentDto();
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(patch("/posts/comments/" + 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(updateCommentDto)))

                .andExpect(status().isOk());
        Optional<Comment> actualComment = commentRepository.findById(1L);
        assertTrue(actualComment.isPresent());
        assertEquals(actualComment.get().getText(), updateCommentDto.getText());
    }

    @Test
    @SneakyThrows
    void deleteCommentTest() {
        Post post = getPost();
        post.setPostId(null);
        postRepository.save(post);
        mockMvc.perform(delete("/posts/comments/" + 1L))

                .andExpect(status().isOk());
        Optional<Comment> actualComment = commentRepository.findById(1L);
        assertFalse(actualComment.isPresent());
    }

    @Test
    @SneakyThrows
    void likePostTest() {
        Post post = getPost();
        post.setPostId(null);
        Post savedPost = postRepository.save(post);
        LikeResponseDto exceptedJson = new LikeResponseDto();
        ObjectMapper objectMapper = new ObjectMapper();
        exceptedJson.setMessage("Вы лайкнули пост");
        mockMvc.perform(put("/posts/" + savedPost.getPostId() + "/like").session(session))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(exceptedJson)));
    }

    @Test
    @SneakyThrows
    void updatePostTest() {
        Post post = getPost();
        post.setPostId(null);
        Post savedPost = postRepository.save(post);
        UpdatePostDto updatePostDto = getUpdatePostDto();
        mockMvc.perform(multipart("/posts/" + savedPost.getPostId()).file(getMultipartFile()).session(session)
                        .param("_method", "patch")
                        .param("postId", savedPost.getPostId().toString())
                        .param("postName", updatePostDto.getPostName())
                        .param("text", updatePostDto.getText())
                        .param("tags", "tag1,tag2"))

                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("post"))
                .andExpect(model().attributeExists("post"))
                .andExpect(xpath("//div[@class='post-content']").nodeCount(1))
                .andExpect(xpath("//div[@class='post-content']//h1[text()='" + updatePostDto.getPostName() + "']").exists())
                .andExpect(xpath("//div[@class='post-content']//div[@class='post_text']//p[text()='" + updatePostDto.getText() + "']").exists())
                .andExpect(xpath("//img[@src]").exists())
                .andExpect(xpath("//div[@class='post-content']//div[@class='tags mt-4']//span[normalize-space(text())='tag1,tag2']").exists());
    }
}

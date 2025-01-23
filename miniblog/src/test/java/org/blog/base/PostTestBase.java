package org.blog.base;

import lombok.SneakyThrows;
import org.blog.dto.post.PostCreateDto;
import org.blog.dto.user.UserResponseDto;
import org.blog.model.Comment;
import org.blog.model.Post;
import org.blog.model.Tag;
import org.blog.model.User;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.util.Set;

public abstract class PostTestBase {

    private final static MultipartFile multipartFile = createMultipartFile("test_image.jpg");

    protected PostCreateDto getPostCreateDto() {
        PostCreateDto postCreateDto = new PostCreateDto();
        postCreateDto.setPostName("testPostName");
        postCreateDto.setText("testText");
        postCreateDto.setImage(multipartFile);
        postCreateDto.setTags(Set.of("tag1", "tag2"));
        return postCreateDto;
    }

    protected static UserResponseDto getUserResponseDto() {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setFirstName("testFirstName");
        userResponseDto.setLastName("testLastName");
        userResponseDto.setUserId(1L);
        return userResponseDto;
    }

    @SneakyThrows
    protected Post getPost() {
        Tag tag1 = new Tag();
        tag1.setTagName("tag1");
        tag1.setTagId(1L);
        tag1.setPostId(1L);
        Tag tag2 = new Tag();
        tag2.setTagName("tag2");
        tag2.setTagId(1L);
        tag2.setPostId(1L);

        User user = new User();
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setUserId(1L);
        user.setPwd("testPwd");

        Comment comment = new Comment();
        comment.setCommentId(1L);
        comment.setPostId(1L);
        comment.setOwner(user);

        Post post = new Post();
        post.setPostName("testPostName");
        post.setOwnerId(1L);
        post.setPostId(1L);
        post.setText("testText");
        post.setImage(multipartFile.getBytes());
        post.setTags(Set.of(tag1, tag2));
        post.setComments(Set.of(comment));
        return post;
    }

    @SneakyThrows
    private static MockMultipartFile createMultipartFile(String resourcePath) {
        var file = ResourceUtils.getFile("classpath:" + resourcePath);
        var fileContent = Files.readAllBytes(file.toPath());

        return new MockMultipartFile("image", file.getName(), "image/jpeg", fileContent);
    }
}

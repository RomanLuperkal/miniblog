package org.blog.base;

import lombok.SneakyThrows;
import org.blog.dto.comment.CreateCommentDto;
import org.blog.dto.comment.FullCommentDtoResponse;
import org.blog.dto.comment.ResponseCommentDto;
import org.blog.dto.comment.UpdateCommentDto;
import org.blog.dto.post.FullPostResponseDto;
import org.blog.dto.post.PostCreateDto;
import org.blog.dto.post.UpdatePostDto;
import org.blog.dto.user.UserResponseDto;
import org.blog.mapper.CommentMapper;
import org.blog.mapper.PostMapper;
import org.blog.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;

import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

public abstract class TestBase {

    private final static MockMultipartFile multipartFile = createMultipartFile("test_image.jpg");

    @Autowired(required = false)
    private CommentMapper commentMapper;
    @Autowired(required = false)
    private PostMapper postMapper;

    protected MockMultipartFile getMultipartFile() {
        return multipartFile;
    }

    protected Like getLike() {
        Like like = new Like();
        like.setPostId(1);
        like.setUserId(1);
        return like;
    }

    protected UpdateCommentDto getUpdateCommentDto() {
        UpdateCommentDto updateCommentDto = new UpdateCommentDto();
        updateCommentDto.setText("testText");
        return updateCommentDto;
    }

    protected ResponseCommentDto getResponseCommentDto() {
        return commentMapper.commentToResponseCommentDto(getComment());
    }

    protected CreateCommentDto getCreateCommentDto() {
        CreateCommentDto createCommentDto = new CreateCommentDto();
        createCommentDto.setOwnerId(1L);
        createCommentDto.setPostId(1L);
        createCommentDto.setText("testCommentText");
        return createCommentDto;
    }

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

    protected Comment getComment() {
        User user = new User();
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setUserId(1L);
        user.setPwd("testPwd");

        Comment comment = new Comment();
        comment.setCommentId(1L);
        comment.setPostId(1L);
        comment.setOwner(user);
        comment.setText("testText");
        comment.setOwnerId(user.getUserId());
        return comment;
    }

    @SneakyThrows
    protected Post getPost() {
        Tag tag1 = new Tag();
        tag1.setTagName("tag1");
        tag1.setTagId(1L);
        tag1.setPostId(1L);
        Tag tag2 = new Tag();
        tag2.setTagName("tag2");
        tag2.setTagId(2L);
        tag2.setPostId(1L);

        Comment comment = getComment();

        Post post = new Post();
        post.setPostName("testPostName");
        post.setOwnerId(1L);
        post.setPostId(1L);
        post.setText("testText");
        post.setImage(multipartFile.getBytes());
        post.setTags(new HashSet<>(Set.of(tag1, tag2)));
        post.setComments(Set.of(comment));
        return post;
    }

    @SneakyThrows
    protected FullPostResponseDto fullPostResponseDto() {
        FullPostResponseDto fullPostResponseDto = new FullPostResponseDto();
        fullPostResponseDto.setPostId(1L);
        fullPostResponseDto.setPostName("testPostName");
        fullPostResponseDto.setText("testText");
        fullPostResponseDto.setOwnerId(1L);
        fullPostResponseDto.setImage(postMapper.bytesToBase64(multipartFile.getBytes()));
        fullPostResponseDto.setTags("tag1, tag2");
        Comment comment = getComment();
        Set<FullCommentDtoResponse> fullCommentDtoResponses = Set.of(commentMapper.commentToFullCommentDtoResponse(comment));
        fullPostResponseDto.setComments(fullCommentDtoResponses);
        fullPostResponseDto.setCommentsCount(1);
        return fullPostResponseDto;
    }

    protected UpdatePostDto getUpdatePostDto() {
        UpdatePostDto updatePostDto = new UpdatePostDto();
        updatePostDto.setPostName("testPostName");
        updatePostDto.setText("testText");
        updatePostDto.setImage(multipartFile);
        updatePostDto.setTags(new HashSet<>(Set.of("tag1", "tag2")));
        return updatePostDto;
    }

    @SneakyThrows
    private static MockMultipartFile createMultipartFile(String resourcePath) {
        var file = ResourceUtils.getFile("classpath:" + resourcePath);
        var fileContent = Files.readAllBytes(file.toPath());

        return new MockMultipartFile("image", file.getName(), "image/jpeg", fileContent);
    }
}

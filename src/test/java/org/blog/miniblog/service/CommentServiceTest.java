package org.blog.miniblog.service;

import org.blog.miniblog.base.TestBase;
import org.blog.miniblog.dto.comment.CreateCommentDto;
import org.blog.miniblog.dto.comment.ResponseCommentDto;
import org.blog.miniblog.dto.comment.UpdateCommentDto;
import org.blog.miniblog.model.Comment;
import org.blog.miniblog.repository.CommentRepository;
import org.testconfiguration.CommentServiceTestConf;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringJUnitConfig(classes = CommentServiceTestConf.class)
public class CommentServiceTest extends TestBase {
    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach
    public void resetMocks() {
        reset(commentRepository);
    }

    @Test
    public void testAddComment() {
        CreateCommentDto createCommentDto = getCreateCommentDto();
        Comment expectedComment = getComment();
        ResponseCommentDto expectedResponseCommentDto = getResponseCommentDto();
        when(commentRepository.save(any(Comment.class))).thenReturn(expectedComment);

        ResponseCommentDto actualResponseCommentDto = commentService.createComment(createCommentDto);

        assertEquals(actualResponseCommentDto, expectedResponseCommentDto);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    public void updateCommentTest() {
        UpdateCommentDto updateCommentDto = getUpdateCommentDto();
        Comment expectedComment = getComment();
        when(commentRepository.findById(expectedComment.getCommentId())).thenReturn(Optional.of(expectedComment));
        when(commentRepository.save(expectedComment)).thenReturn(expectedComment);

        commentService.updateComment(updateCommentDto, expectedComment.getCommentId());

        assertEquals(updateCommentDto.getText(), expectedComment.getText());
        verify(commentRepository, times(1)).save(expectedComment);
        verify(commentRepository, times(1)).findById(expectedComment.getCommentId());
    }

    @Test
    public void updateCommentTestWhenCommentNotFound() {
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        UpdateCommentDto updateCommentDto = getUpdateCommentDto();
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> commentService.updateComment(updateCommentDto, 1L));

        assertEquals(e.getStatusCode(), expectedStatus);
        verify(commentRepository, times(0)).save(any(Comment.class));
        verify(commentRepository, times(1)).findById(1L);
    }

    @Test
    public void deleteCommentTest() {
        when(commentRepository.existsById(1L)).thenReturn(true);
        doNothing().when(commentRepository).deleteById(1L);

        commentService.deleteComment(1L);

        verify(commentRepository, times(1)).deleteById(1L);
        verify(commentRepository, times(1)).existsById(1L);
    }

    @Test
    public void deleteCommentTestWhenCommentNotFound() {
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        when(commentRepository.existsById(1L)).thenReturn(false);

        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> commentService.deleteComment(1L));

        assertEquals(e.getStatusCode(), expectedStatus);
        verify(commentRepository, times(0)).deleteById(1L);
        verify(commentRepository, times(1)).existsById(1L);
    }
}

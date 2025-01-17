package org.blog.service.impl;

import lombok.RequiredArgsConstructor;
import org.blog.dto.comment.CreateCommentDto;
import org.blog.dto.comment.ResponseCommentDto;
import org.blog.dto.comment.UpdateCommentDto;
import org.blog.mapper.CommentMapper;
import org.blog.model.Comment;
import org.blog.repository.CommentRepository;
import org.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public ResponseCommentDto createComment(CreateCommentDto comment) {
        Comment newComment = commentMapper.CreateCommentDtoToComment(comment);
        return commentMapper.commentToResponseCommentDto(commentRepository.save(newComment));
    }

    @Override
    public void updateComment(UpdateCommentDto updateCommentDto, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Комментария с id=" + commentId + " не существует"));
        comment.setText(comment.getText());
        commentRepository.save(comment);
    }
}

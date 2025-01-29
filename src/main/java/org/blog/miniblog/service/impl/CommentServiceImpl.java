package org.blog.miniblog.service.impl;

import lombok.RequiredArgsConstructor;
import org.blog.miniblog.dto.comment.CreateCommentDto;
import org.blog.miniblog.dto.comment.ResponseCommentDto;
import org.blog.miniblog.dto.comment.UpdateCommentDto;
import org.blog.miniblog.mapper.CommentMapper;
import org.blog.miniblog.model.Comment;
import org.blog.miniblog.repository.CommentRepository;
import org.blog.miniblog.service.CommentService;
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
        comment.setText(updateCommentDto.getText());
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Комментария с id=" + commentId + " не существует");
        }
    }
}

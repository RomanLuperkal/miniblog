package org.blog.miniblog.service;


import org.blog.miniblog.dto.comment.CreateCommentDto;
import org.blog.miniblog.dto.comment.ResponseCommentDto;
import org.blog.miniblog.dto.comment.UpdateCommentDto;

public interface CommentService {
    ResponseCommentDto createComment(CreateCommentDto comment);

    void updateComment(UpdateCommentDto comment, Long commentId);

    void deleteComment(Long commentId);
}

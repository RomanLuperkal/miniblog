package org.blog.service;

import org.blog.dto.comment.CreateCommentDto;
import org.blog.dto.comment.ResponseCommentDto;
import org.blog.dto.comment.UpdateCommentDto;

public interface CommentService {
    ResponseCommentDto createComment(CreateCommentDto comment);
    void updateComment(UpdateCommentDto comment, Long commentId);
    void deleteComment(Long commentId);
}

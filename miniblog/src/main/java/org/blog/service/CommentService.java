package org.blog.service;

import org.blog.dto.comment.CreateCommentDto;
import org.blog.dto.comment.ResponseCommentDto;

public interface CommentService {
    ResponseCommentDto createComment(CreateCommentDto comment);
}

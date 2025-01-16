package org.blog.mapper;

import org.blog.dto.comment.CreateCommentDto;
import org.blog.dto.comment.ResponseCommentDto;
import org.blog.model.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment CreateCommentDtoToComment(CreateCommentDto comment);

    ResponseCommentDto commentToResponseCommentDto(Comment comment);
}

package org.blog.mapper;

import org.blog.dto.comment.CreateCommentDto;
import org.blog.dto.comment.FullCommentDtoResponse;
import org.blog.dto.comment.ResponseCommentDto;
import org.blog.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment CreateCommentDtoToComment(CreateCommentDto comment);

    ResponseCommentDto commentToResponseCommentDto(Comment comment);

    @Mapping(target = "ownerFullName", expression = "java(comment.getOwner().getFirstName() + \" \" + comment.getOwner().getLastName())")
    FullCommentDtoResponse commentToFullCommentDtoResponse(Comment comment);
}

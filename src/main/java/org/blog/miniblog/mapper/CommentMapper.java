package org.blog.miniblog.mapper;


import org.blog.miniblog.dto.comment.CreateCommentDto;
import org.blog.miniblog.dto.comment.FullCommentDtoResponse;
import org.blog.miniblog.dto.comment.ResponseCommentDto;
import org.blog.miniblog.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment CreateCommentDtoToComment(CreateCommentDto comment);

    ResponseCommentDto commentToResponseCommentDto(Comment comment);

    @Mapping(target = "ownerFullName", expression = "java(comment.getOwner().getFirstName() + \" \" + comment.getOwner().getLastName())")
    FullCommentDtoResponse commentToFullCommentDtoResponse(Comment comment);
}

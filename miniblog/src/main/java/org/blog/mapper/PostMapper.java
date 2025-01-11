package org.blog.mapper;

import org.blog.dto.post.PostCreateDto;
import org.blog.model.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {

    Post postCreateDtoToPost(PostCreateDto postCreateDto);
}

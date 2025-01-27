package org.blog.mapper;

import org.blog.dto.user.UserCreateDto;
import org.blog.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userCreateDtoToUser(UserCreateDto userCreateDto);
}

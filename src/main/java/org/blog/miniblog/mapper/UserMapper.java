package org.blog.miniblog.mapper;


import org.blog.miniblog.dto.user.UserCreateDto;
import org.blog.miniblog.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userCreateDtoToUser(UserCreateDto userCreateDto);
}

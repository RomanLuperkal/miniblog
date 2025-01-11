package org.blog.service;

import org.blog.dto.user.UserCreateDto;
import org.blog.dto.user.UserResponseDto;

public interface UserService {

    void addUser(UserCreateDto user);

    UserResponseDto loginUser(String login, String password);
}

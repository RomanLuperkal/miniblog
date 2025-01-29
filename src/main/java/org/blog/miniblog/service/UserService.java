package org.blog.miniblog.service;

import org.blog.miniblog.dto.user.UserCreateDto;
import org.blog.miniblog.dto.user.UserResponseDto;

public interface UserService {

    void addUser(UserCreateDto user);

    UserResponseDto loginUser(String login, String password);

    boolean existUser(Long userId);
}

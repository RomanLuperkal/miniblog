package org.blog.miniblog.service.impl;

import lombok.RequiredArgsConstructor;

import org.blog.miniblog.dto.user.UserCreateDto;
import org.blog.miniblog.dto.user.UserResponseDto;
import org.blog.miniblog.mapper.UserMapper;
import org.blog.miniblog.model.User;
import org.blog.miniblog.repository.UserRepository;
import org.blog.miniblog.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public void addUser(UserCreateDto userCreateDto) {
        if (userRepository.existsByLogin(userCreateDto.getLogin())) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        User newUser = userMapper.userCreateDtoToUser(userCreateDto);
        userRepository.save(newUser);
    }

    @Override
    public UserResponseDto loginUser(String login, String password) {
        Optional<UserResponseDto> user = userRepository.findUserByLoginAndPwd(login, password);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return user.get();
    }

    @Override
    public boolean existUser(Long userId) {
        return userRepository.existsById(userId);
    }
}

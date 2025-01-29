package org.blog.miniblog.repository;

import org.blog.miniblog.dto.user.UserResponseDto;
import org.blog.miniblog.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<UserResponseDto> findUserByLoginAndPwd(String login, String pwd);
    boolean existsByLogin(String login);
}


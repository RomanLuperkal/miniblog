package org.blog.repository;

import org.blog.dto.user.UserResponseDto;
import org.blog.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<UserResponseDto> findUserByLoginAndPwd(String login, String pwd);
    boolean existsByLogin(String login);
}


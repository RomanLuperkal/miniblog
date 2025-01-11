package org.blog.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDto {
    private String login;
    private String pwd;
    private String firstName;
    private String lastName;
}

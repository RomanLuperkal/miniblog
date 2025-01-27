package org.blog.dto.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class UserResponseDto {
    private Long userId;
    private String firstName;
    private String lastName;
}

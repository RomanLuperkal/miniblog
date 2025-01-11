package org.blog.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("usr")
@Getter
@Setter
public class User {
    @Id
    @Column("user_id")
    private Long userId;
    private String login;
    private String pwd;
    @Column("firstname")
    private String firstName;
    @Column("lastname")
    private String lastName;
}

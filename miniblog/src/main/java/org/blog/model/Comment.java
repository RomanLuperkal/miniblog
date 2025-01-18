package org.blog.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@Setter
public class Comment {
    @Id
    @Column("comment_id")
    private Long commentId;
    @Column("owner_id")
    private Long ownerId;
    @Column("post_id")
    private Long postId;
    private String text;
    @Transient
    private User owner;
}

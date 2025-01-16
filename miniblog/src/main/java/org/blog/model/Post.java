package org.blog.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.Set;

@Getter
@Setter
public class Post {
    @Id
    @Column("post_id")
    private Long postId;

    @Column("owner_id")
    private Long ownerId;

    @Column("post_name")
    private String postName;

    private byte[] image;

    private String text;

    private String tag;

    @Column("comments_count")
    private Long commentsCount = 0L;

    @Column("likes_count")
    private Long likesCount = 0L;

    @MappedCollection(idColumn = "post_id")
    private Set<Comment> comments;
}

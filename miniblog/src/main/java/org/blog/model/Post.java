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

    @MappedCollection(idColumn = "post_id")
    private Set<Tag> tags;

    @Column("comments_count")
    private int commentsCount = 0;

    @Column("likes_count")
    private Long likesCount = 0L;

    @MappedCollection(idColumn = "post_id")
    private Set<Comment> comments;
}

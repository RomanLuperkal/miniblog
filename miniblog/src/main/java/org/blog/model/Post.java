package org.blog.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.Objects;
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

    @Transient
    private int commentsCount = 0;

    @Column("likes_count")
    private Long likesCount = 0L;

    @MappedCollection(idColumn = "post_id")
    private Set<Comment> comments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post post)) return false;
        return Objects.equals(postId, post.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(postId);
    }
}

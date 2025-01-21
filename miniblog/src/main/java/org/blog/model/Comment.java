package org.blog.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment comment)) return false;
        return Objects.equals(commentId, comment.commentId) && Objects.equals(text, comment.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, text);
    }
}

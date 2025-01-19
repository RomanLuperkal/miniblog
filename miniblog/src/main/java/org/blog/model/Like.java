package org.blog.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.io.Serializable;

@Getter
@Setter
public class Like {
    @Id
    private LikeKey likeKey;

    @Getter
    @Setter
    public static class LikeKey implements Serializable {
        @Column("user_id")
        private long userId;
        @Column("post_id")
        private long postId;
    }
}

package org.blog.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Like {
    private long userId;
    private long postId;
}

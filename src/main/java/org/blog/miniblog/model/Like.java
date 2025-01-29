package org.blog.miniblog.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Like {
    private long userId;
    private long postId;
}

package org.blog.miniblog.dto.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCommentDto {
    private long postId;
    private long ownerId;
    private String text;
}

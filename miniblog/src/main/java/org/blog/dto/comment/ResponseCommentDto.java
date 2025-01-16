package org.blog.dto.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseCommentDto {
    private long commentId;
    private long ownerId;
    private String text;
}

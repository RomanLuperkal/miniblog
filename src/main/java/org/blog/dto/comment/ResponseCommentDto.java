package org.blog.dto.comment;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ResponseCommentDto {
    private long commentId;
    private long ownerId;
    private String text;
}

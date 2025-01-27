package org.blog.dto.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FullCommentDtoResponse {
    private long commentId;
    private long ownerId;
    private String ownerFullName;
    private String text;
}

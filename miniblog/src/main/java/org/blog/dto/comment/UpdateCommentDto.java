package org.blog.dto.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCommentDto {
    private Long commentId;
    private String text;
    private Long ownerId;
    private Long postId;
}

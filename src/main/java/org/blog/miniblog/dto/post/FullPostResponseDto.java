package org.blog.miniblog.dto.post;

import lombok.Getter;
import lombok.Setter;
import org.blog.miniblog.dto.comment.FullCommentDtoResponse;


import java.util.Set;

@Getter
@Setter
public class FullPostResponseDto {
    private Long postId;
    private Long ownerId;
    private String postName;
    private String image;
    private String text;
    private String tags;
    private int commentsCount = 0;
    private Long likesCount = 0L;
    private Set<FullCommentDtoResponse> comments;
}

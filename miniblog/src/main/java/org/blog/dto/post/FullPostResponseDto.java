package org.blog.dto.post;

import lombok.Getter;
import lombok.Setter;
import org.blog.dto.comment.FullCommentDtoResponse;

import java.util.Set;

@Getter
@Setter
public class FullPostResponseDto {
    private Long postId;
    private Long ownerId;
    private String postName;
    private String image;
    private String text;
    private String tag;
    private Long commentsCount = 0L;
    private Long likesCount = 0L;
    private Set<FullCommentDtoResponse> comments;
}

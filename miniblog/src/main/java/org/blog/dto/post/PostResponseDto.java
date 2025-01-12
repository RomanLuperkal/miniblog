package org.blog.dto.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponseDto {
    private Long postId;
    private Long ownerId;
    private String postName;
    private String image;
    private String text;
    private String tag;
    private Long commentsCount = 0L;
    private Long likesCount = 0L;
}

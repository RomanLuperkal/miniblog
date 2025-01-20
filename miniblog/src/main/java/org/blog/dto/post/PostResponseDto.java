package org.blog.dto.post;

import lombok.Getter;
import lombok.Setter;
import org.blog.dto.comment.ResponseCommentDto;

import java.util.Set;

@Getter
@Setter
public class PostResponseDto {
    private Long postId;
    private Long ownerId;
    private String postName;
    private String image;
    private String text;
    private String tags;
    private int commentsCount = 0;
    private Long likesCount = 0L;
    private Set<ResponseCommentDto> comments;
}

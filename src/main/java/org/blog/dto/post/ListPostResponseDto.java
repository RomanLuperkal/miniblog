package org.blog.dto.post;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class ListPostResponseDto {
    private List<PostResponseDto> posts;
    private int size;
    private int from;
    private List<String> tags;
    private boolean prev;
    private boolean next;
}

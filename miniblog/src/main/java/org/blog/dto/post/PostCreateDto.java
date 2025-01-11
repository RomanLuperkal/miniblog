package org.blog.dto.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCreateDto {

    private String postName;

    private byte[] image;

    private String text;

    private String tag;
}

package org.blog.dto.post;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PostCreateDto {

    private String postName;

    //private MultipartFile image;

    private String text;

    private String tag;
}

package org.blog.dto.post;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Getter
@Setter
public class PostCreateDto {

    private String postName;

    private MultipartFile image;

    private String text;

    private Set<String> tags;
}

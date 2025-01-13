package org.blog.mapper;

import org.blog.dto.post.PostCreateDto;
import org.blog.dto.post.PostResponseDto;
import org.blog.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "image", source = "image", qualifiedByName = "getBytesFromMultipartFile")
    Post postCreateDtoToPost(PostCreateDto postCreateDto);

    @Mapping(target = "image", source = "image", qualifiedByName = "bytesToBase64")
    PostResponseDto postToPostResponseDto(Post post);

    List<PostResponseDto> postListToPostResponseDtoList(Iterable<Post> posts);

    @Named("bytesToBase64")
    default String bytesToBase64(byte[] image) {
        if (image == null) {
            return null;
        }
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(image);
    }

    @Named("getBytesFromMultipartFile")
    default byte[] getBytesFromMultipartFile(MultipartFile multipartFile) throws IOException {
        return multipartFile.getBytes();
    }
}

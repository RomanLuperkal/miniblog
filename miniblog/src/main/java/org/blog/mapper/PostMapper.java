package org.blog.mapper;

import org.blog.dto.post.FullPostResponseDto;
import org.blog.dto.post.PostCreateDto;
import org.blog.dto.post.PostResponseDto;
import org.blog.dto.post.UpdatePostDto;
import org.blog.model.Post;
import org.blog.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {CommentMapper.class, TagMapper.class})
public interface PostMapper {

    @Mapping(target = "image", source = "image", qualifiedByName = "getBytesFromMultipartFile")
    @Mapping(target = "tags", source = "tags", qualifiedByName = "setStringToSetTags")
    Post postCreateDtoToPost(PostCreateDto postCreateDto);

    @Mapping(target = "image", source = "image", qualifiedByName = "bytesToBase64")
    @Mapping(target = "tags", source = "tags", qualifiedByName = "getStringTagsFromSet")
    @Mapping(target = "commentsCount", expression = "java(post.getComments().size())")
    PostResponseDto postToPostResponseDto(Post post);

    List<PostResponseDto> postListToPostResponseDtoList(Iterable<Post> posts);

    @Mapping(target = "image", source = "image", qualifiedByName = "bytesToBase64")
    @Mapping(target = "tags", source = "tags", qualifiedByName = "getStringTagsFromSet")
    @Mapping(target = "commentsCount", expression = "java(post.getComments().size())")
    FullPostResponseDto postToFullPostResponseDto(Post post);

    @Mapping(target = "image", source = "image", qualifiedByName = "getBytesFromMultipartFile")
    Post mapToProduct(@MappingTarget Post post, UpdatePostDto updatePost);

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

    @Named("getStringTagsFromSet")
    default String getStringTagsFromSet(Set<Tag> tags) {
        List<String> stringTags = tags.stream().map(Tag::getTagName).toList();
        return String.join(",", stringTags);
    }
}

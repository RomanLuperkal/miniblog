package org.blog.service.impl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.blog.dto.post.*;
import org.blog.dto.user.UserResponseDto;
import org.blog.mapper.PostMapper;
import org.blog.model.Post;
import org.blog.repository.PostRepository;
import org.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostMapper postMapper;
    private final PostRepository postRepository;

    @Override
    public void createPostCreate(PostCreateDto postCreateDto, HttpSession session) {
        Post newPost = postMapper.postCreateDtoToPost(postCreateDto);
        UserResponseDto user = (UserResponseDto) session.getAttribute("user");
        newPost.setOwnerId(user.getUserId());
        postRepository.save(newPost);
    }

    @Override
    public ListPostResponseDto getPosts(List<String> tags, int from, int size) {
        long count;
        int offset = from * size;
        List<PostResponseDto> postResponseDtos;
        if (tags != null && !tags.isEmpty()) {
            count = postRepository.countPostByTags(String.join(",", tags), size + 1, offset);
            postResponseDtos = postMapper
                    .postListToPostResponseDtoList(postRepository.getPostsByTags(String.join(",", tags), size, offset));

            return createListPostResponseDto(postResponseDtos, from, count);
        }
        count = postRepository.countPost(size + 1, offset);
        postResponseDtos = postMapper
                .postListToPostResponseDtoList(postRepository.getPost(size, offset));
        return createListPostResponseDto(postResponseDtos, from, count);
    }

    private ListPostResponseDto createListPostResponseDto(List<PostResponseDto> postResponseDtos, int from,
                                                          long count) {
        ListPostResponseDto listPostResponseDto = new ListPostResponseDto();
        listPostResponseDto.setPosts(postResponseDtos);
        listPostResponseDto.setNext(count > postResponseDtos.size());
        listPostResponseDto.setPrev(from > 0);
        return listPostResponseDto;
    }

    @Override
    public FullPostResponseDto getPost(Long postId) {
        Post post = postRepository.findPostWithComments(postId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Пост не найден"));
        return postMapper.postToFullPostResponseDto(post);
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public FullPostResponseDto updatePost(Long postId, UpdatePostDto updatePostDto) {
        Post post = postRepository.findPostWithComments(postId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Пост не найден"));
        Post updatedPost = postRepository.save(postMapper.mapToProduct(post, updatePostDto));
        return postMapper.postToFullPostResponseDto(updatedPost);
    }

    @Override
    public void calculateLikes(int value, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Пост с id=" + postId + " не найден."));
        post.setLikesCount(post.getLikesCount() + value);
        postRepository.save(post);
    }
}

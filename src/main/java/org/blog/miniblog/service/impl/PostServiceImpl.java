package org.blog.miniblog.service.impl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.blog.miniblog.dto.post.*;
import org.blog.miniblog.dto.user.UserResponseDto;
import org.blog.miniblog.mapper.PostMapper;
import org.blog.miniblog.model.Post;
import org.blog.miniblog.model.Tag;
import org.blog.miniblog.repository.PostRepository;
import org.blog.miniblog.service.PostService;
import org.blog.miniblog.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final TagService tagService;

    @Override
    public void createPost(PostCreateDto postCreateDto, HttpSession session) {
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
            count = postRepository.countPostByTags(tags, size + 1, offset);
            postResponseDtos = postMapper
                    .postListToPostResponseDtoList(postRepository.getPostsByTags(tags, size, offset));

            return createListPostResponseDto(postResponseDtos, from, count);
        }
        count = postRepository.countPost(size + 1, offset);
        postResponseDtos = postMapper
                .postListToPostResponseDtoList(postRepository.getPost(size, offset));
        return createListPostResponseDto(postResponseDtos, from, count);
    }

    @Override
    public FullPostResponseDto getPost(Long postId) {
        Post post = postRepository.findPostWithComments(postId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Пост не найден"));
        FullPostResponseDto fullPostResponseDto = postMapper.postToFullPostResponseDto(post);
        fullPostResponseDto.setCommentsCount(post.getComments().size());
        return fullPostResponseDto;
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    @Transactional
    public FullPostResponseDto updatePost(Long postId, UpdatePostDto updatePostDto) {
        Post post = postRepository.findPostWithComments(postId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Пост не найден"));
        if (!post.getTags().isEmpty()) {
            List<Long> tagIds = post.getTags().stream().map(Tag::getTagId).toList();
            tagService.deleteTags(tagIds);
        }
        Post updatedPost = postRepository.save(postMapper.mapToPost(post, updatePostDto));
        return postMapper.postToFullPostResponseDto(updatedPost);
    }

    @Override
    public void calculateLikes(int value, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Пост с id=" + postId + " не найден."));
        post.setLikesCount(post.getLikesCount() + value);
        postRepository.save(post);
    }

    private ListPostResponseDto createListPostResponseDto(List<PostResponseDto> postResponseDtos, int from,
                                                          long count) {
        ListPostResponseDto listPostResponseDto = new ListPostResponseDto();
        listPostResponseDto.setPosts(postResponseDtos);
        listPostResponseDto.setNext(count > postResponseDtos.size());
        listPostResponseDto.setPrev(from > 0);
        return listPostResponseDto;
    }
}

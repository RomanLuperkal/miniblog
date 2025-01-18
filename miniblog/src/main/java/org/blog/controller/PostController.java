package org.blog.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.blog.dto.comment.CreateCommentDto;
import org.blog.dto.comment.UpdateCommentDto;
import org.blog.dto.post.FullPostResponseDto;
import org.blog.dto.post.ListPostResponseDto;
import org.blog.dto.post.PostCreateDto;
import org.blog.dto.user.UserResponseDto;
import org.blog.service.CommentService;
import org.blog.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    //
    // Отображение страницы с постами
    @GetMapping
    public String listPosts(Model model, HttpSession session, @RequestParam(defaultValue = "") List<String> tags,
                            @RequestParam(defaultValue = "10", name = "size") int size,
                            @RequestParam(defaultValue = "0") int from) {
        UserResponseDto user = (UserResponseDto) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getFirstName() + " " + user.getLastName());
        } else {
            model.addAttribute("userName", "Гость"); // Если пользователь не найден
        }
        ListPostResponseDto posts = postService.getPosts(tags, from, size);
        model.addAttribute("posts", posts.getPosts());
        model.addAttribute("size", size);
        model.addAttribute("from", from);
        model.addAttribute("tags", String.join(",", tags));
        model.addAttribute("hasNext", posts.isNext());
        model.addAttribute("hasPrev", posts.isPrev());
        return "post-list";
    }


    @PostMapping("/add")
    public String addPost(
            @ModelAttribute PostCreateDto postCreateDto,
            HttpSession session,
            Model model) {
        try {
            postService.createPostCreate(postCreateDto, session);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "post-list";
        }
        return "redirect:/posts"; // После сохранения перенаправляем на список постов
    }

    @GetMapping("{postId}")
    public String getPost(@PathVariable Long postId, Model model, HttpSession session) {
        FullPostResponseDto post = postService.getPost(postId);
        model.addAttribute("post", post);
        return "post";
    }

    @PostMapping(value = "{postId}", params = "_method=delete")
    public String deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return "redirect:/posts";
    }

    @PostMapping("{postId}/comments")
    public String createComment(@PathVariable Long postId, HttpSession session,
                                @ModelAttribute CreateCommentDto createCommentDto, Model model) {
        UserResponseDto user = (UserResponseDto) session.getAttribute("user");
        createCommentDto.setOwnerId(user.getUserId());
        commentService.createComment(createCommentDto);
        return "redirect:/posts/" + postId;
    }

    @PatchMapping("/comments/{commentId}")
    @ResponseBody
    public ResponseEntity<Void> updateComment(@RequestBody UpdateCommentDto updateCommentDto, @PathVariable Long commentId) {
        commentService.updateComment(updateCommentDto, commentId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/comments/{commentId}")
    @ResponseBody
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

}

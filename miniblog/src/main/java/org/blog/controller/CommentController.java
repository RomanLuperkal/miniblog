package org.blog.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.blog.dto.comment.CreateCommentDto;
import org.blog.dto.user.UserResponseDto;
import org.blog.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("{postId}")
    public String createComment(@PathVariable Long postId, HttpSession session,
                                @ModelAttribute CreateCommentDto createCommentDto, Model model) {
        UserResponseDto user = (UserResponseDto) session.getAttribute("user");
        createCommentDto.setOwnerId(user.getUserId());
        commentService.createComment(createCommentDto);
        return "redirect:/posts/" + postId;
    }
}

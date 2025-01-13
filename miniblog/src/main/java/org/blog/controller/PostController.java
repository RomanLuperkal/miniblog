package org.blog.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.blog.dto.post.PostCreateDto;
import org.blog.dto.user.UserResponseDto;
import org.blog.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // Отображение страницы с постами
    @GetMapping
    public String listPosts(Model model, HttpSession session) {
        UserResponseDto user = (UserResponseDto) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getFirstName() + " " + user.getLastName());
        } else {
            model.addAttribute("userName", "Гость"); // Если пользователь не найден
        }

        model.addAttribute("posts", postService.getPosts());
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
}

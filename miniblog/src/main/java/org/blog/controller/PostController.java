package org.blog.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.blog.dto.post.PostCreateDto;
import org.blog.dto.user.UserResponseDto;
import org.blog.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    //
    // Отображение страницы с постами
    @GetMapping
    public String listPosts(Model model, HttpSession session, @RequestParam(required = false) List<String> tags,
                            @RequestParam(defaultValue = "10", name = "size") Long size, @RequestParam(defaultValue = "0") Long from) {
        UserResponseDto user = (UserResponseDto) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getFirstName() + " " + user.getLastName());
        } else {
            model.addAttribute("userName", "Гость"); // Если пользователь не найден
        }

        model.addAttribute("posts", postService.getPosts(tags));
        model.addAttribute("size", size);
        session.setAttribute("from", from);
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

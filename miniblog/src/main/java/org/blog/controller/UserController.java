package org.blog.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.blog.dto.user.UserCreateDto;
import org.blog.dto.user.UserResponseDto;
import org.blog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam(name = "login") String login, @RequestParam(name = "password") String password,
                        Model model, HttpSession session) {
        try {
            UserResponseDto user = userService.loginUser(login, password);
            session.setAttribute("user", user);
            model.addAttribute("message", "Login successful!");
            return "login";
        } catch (Exception e) {
            model.addAttribute("error", "Неправильные имя пользователя или пароль");
            return "login";
        }
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserCreateDto user, Model model) {
        try {
            userService.addUser(user);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Пользователь уже существует!");
            return "register";
        }
    }
}

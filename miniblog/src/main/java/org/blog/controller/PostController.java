package org.blog.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.blog.dto.user.UserResponseDto;
import org.blog.model.Post;
import org.blog.repository.PostRepository;
import org.blog.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;

    // Отображение страницы с постами
    @GetMapping
    public String listPosts(Model model, HttpSession session) {
        Iterable<Post> posts = postRepository.findAll();
        UserResponseDto user = (UserResponseDto) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", "Вы вошли как: " + user.getFirstName() + " " + user.getLastName());
        } else {
            model.addAttribute("userName", "Вы вошли как: Гость"); // Если пользователь не найден
        }

        List<Map<String, Object>> postList = new ArrayList<>();
        for (Post post : posts) {
            Map<String, Object> postData = new HashMap<>();
            postData.put("postName", post.getPostName());
            postData.put("text", post.getText());
            postData.put("tag", post.getTag());
            postData.put("likesCount", post.getLikesCount());
            postData.put("commentsCount", post.getCommentsCount());

            // Преобразование байтового массива изображения в Data URL
            String base64Image = Base64.getEncoder().encodeToString(post.getImage());
            postData.put("image", "data:image/png;base64," + base64Image);

            postList.add(postData);
        }

        model.addAttribute("posts", postList);
        return "post-list";
    }


    @PostMapping("/add")
    public String addPost(
            @RequestParam(name = "postName") String postName,
            @RequestParam(name = "image") MultipartFile image,
            @RequestParam(name = "text") String text,
            @RequestParam(name = "tag") String tag,
            /*@RequestParam(name = "ownerId") Long ownerId,*/ // Передаётся ID текущего пользователя
            Model model) {
        try {
            Post post = new Post();
            post.setPostName(postName);
            post.setImage(image.getBytes()); // Сохраняем изображение как byte[]
            post.setText(text);
            post.setTag(tag);
            post.setOwnerId(1L); // Устанавливаем владельца поста
            postRepository.save(post);
        } catch (IOException e) {
            model.addAttribute("error", "Error saving the post. Try again.");
            return "post-list";
        }
        return "redirect:/posts"; // После сохранения перенаправляем на список постов
    }
}

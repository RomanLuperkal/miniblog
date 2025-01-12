package org.blog.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.blog.dto.post.PostCreateDto;
import org.blog.dto.user.UserResponseDto;
import org.blog.model.Post;
import org.blog.repository.PostRepository;
import org.blog.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;

    // Отображение страницы с постами
    @GetMapping
    public String listPosts(Model model, HttpSession session) {
        //Iterable<Post> posts = postRepository.findAll();
        UserResponseDto user = (UserResponseDto) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getFirstName() + " " + user.getLastName());
        } else {
            model.addAttribute("userName", "Гость"); // Если пользователь не найден
        }

        /*List<Map<String, Object>> postList = new ArrayList<>();
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
        }*/

        model.addAttribute("posts", postService.getPosts());
        return "post-list";
    }


    @PostMapping("/add")
    public String addPost(
            /*@RequestParam(name = "postName") String postName,
            @RequestParam(name = "image") MultipartFile image,
            @RequestParam(name = "text") String text,
            @RequestParam(name = "tag") String tag,*/
            @ModelAttribute PostCreateDto postCreateDto,
            HttpSession session,
            Model model) {
        try {
            postService.createPostCreate(postCreateDto, session);
            UserResponseDto user = (UserResponseDto) session.getAttribute("user");
            /*Post post = new Post();
            post.setPostName(postName);
            post.setImage(image.getBytes()); // Сохраняем изображение как byte[]
            post.setText(text);
            post.setTag(tag);
            post.setOwnerId(user.getUserId()); // Устанавливаем владельца поста
            postRepository.save(post);*/
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "post-list";
        }
        return "redirect:/posts"; // После сохранения перенаправляем на список постов
    }
}

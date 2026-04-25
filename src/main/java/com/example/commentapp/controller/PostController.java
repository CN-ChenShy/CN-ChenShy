package com.example.commentapp.controller;

import com.example.commentapp.entity.Post;
import com.example.commentapp.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    // 获取所有帖子
    @GetMapping("/list")
    public List<Post> list() {
        return postService.findAll();
    }

    // 获取帖子ID
    @GetMapping("/{id}")
    public Post getById(@PathVariable Integer id) {
        return postService.findById(id);
    }

    // 发帖-从Request取用户
    @PostMapping("/add")
    public String add(@RequestBody Post post, HttpServletRequest request) {
        String currentUser = (String) request.getAttribute("loginUsername");
        postService.addPost(post, currentUser);
        return "发帖成功";
    }

    // 删除-去掉@RequestParam username
    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Integer id,
            HttpServletRequest request
    ) {
        String currentUser = (String) request.getAttribute("loginUsername");
        postService.deletePost(id, currentUser);
        return "删除成功";
    }
}
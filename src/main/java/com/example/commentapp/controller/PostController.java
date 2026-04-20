package com.example.commentapp.controller;

import com.example.commentapp.entity.Post;
import com.example.commentapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    // 发帖
    @PostMapping("/add")
    public String add(@RequestBody Post post) {
        postService.addPost(post);
        return "发帖成功";
    }

    // 删除
    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Integer id,
            @RequestParam String username  // 前端传用户名
    ) {
        postService.deletePost(id, username);
        return "删除成功";
}
}
package com.example.commentapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    // 获取所有帖子
    @GetMapping("/list")
    public List<Post> list() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public Post getById(@PathVariable Integer id) {
        return postRepository.findById(id).orElse(null);
    }

    // 发帖（我自己用，不公开到网站）
    @PostMapping("/add")
    public String add(@RequestBody Post post) {
        //增加后端校验，只有admin能发帖子
        if (post.getUsername() == null || !post.getUsername().equals("admin")) {
            return "--403--仅限管理员可发帖";
        }

        postRepository.save(post);
        return "发帖成功";
    }
}
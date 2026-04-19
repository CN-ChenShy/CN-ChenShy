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
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    // 根据帖子ID查评论
    @GetMapping("/list/{postId}")
    public List<Comment> list(@PathVariable Integer postId) {
        return commentRepository.findByPostId(postId);
    }

    // 发表评论
    @PostMapping("/add")
    public String add(@RequestBody Comment comment) {
        commentRepository.save(comment);
        return "评论成功";
    }
}
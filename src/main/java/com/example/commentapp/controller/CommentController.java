package com.example.commentapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.commentapp.entity.Comment;
import com.example.commentapp.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService CommentService;

    // 根据帖子ID查评论
    @GetMapping("/list/{postId}")
    public List<Comment> list(@PathVariable Integer postId) {
        return CommentService.findByPostId(postId);
    }

    // 发表评论
    @PostMapping("/add")
    public String add(@RequestBody Comment comment) {
        CommentService.addComment(comment);
        return "评论成功";
    }

    //删除评论
    @DeleteMapping("/delete/{id}")
    public String delete(
        @PathVariable Integer id,
        @RequestParam String username
    ) {
        CommentService.deleteComment(id, username);
        return "评论删除成功";
}
}
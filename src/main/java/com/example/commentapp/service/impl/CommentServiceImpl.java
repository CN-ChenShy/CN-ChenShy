package com.example.commentapp.service.impl;

import com.example.commentapp.entity.Comment;
import com.example.commentapp.entity.Post;
import com.example.commentapp.repository.CommentRepository;
import com.example.commentapp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.commentapp.service.PostService;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostService postService;

    // 查询
    @Override
    public List<Comment> findByPostId(Integer postId) {
        return commentRepository.findByPostId(postId);
    }

    // 接收后端传入的用户名
    @Override
    public void addComment(Comment comment, String currentUsername) {
        // 校验帖子是否存在（保留你原来的逻辑）
        Post post = postService.findById(comment.getPostId());
        if (post == null) {
            throw new RuntimeException("帖子不存在，无法评论");
        }

        // 用户名由后端赋值，不相信前端传的！
        comment.setUsername(currentUsername);

        commentRepository.save(comment);
    }

    // 删除用后端用户名
    @Override
    public void deleteComment(Integer commentId, String currentUsername) {
        if (!"admin".equals(currentUsername)) {
            throw new RuntimeException("只有管理员可以删除评论");
        }
        commentRepository.deleteById(commentId);
    }
}
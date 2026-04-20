package com.example.commentapp.service.impl;

import com.example.commentapp.entity.Comment;
import com.example.commentapp.repository.CommentRepository;
import com.example.commentapp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    // 查询
    @Override
    public List<Comment> findByPostId(Integer postId) {
        return commentRepository.findByPostId(postId);
    }

    // 新增
    @Override
    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

    // 删除（管理员权限）
    @Override
    public void deleteComment(Integer commentId, String username) {
        if (!"admin".equals(username)) {
            throw new RuntimeException("只有管理员可以删除评论");
        }
        commentRepository.deleteById(commentId);
    }
}
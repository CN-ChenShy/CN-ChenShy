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

    // 新增
    @Override
    public void addComment(Comment comment) {
        // 校验帖子是否存在
        Post post = postService.findById(comment.getPostId());
    
        if (post == null) {
            throw new RuntimeException("帖子不存在，无法评论");
        }

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
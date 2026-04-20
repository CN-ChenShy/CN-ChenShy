package com.example.commentapp.service;
import com.example.commentapp.entity.Comment;
import java.util.List;

public interface CommentService {
    List<Comment> findByPostId(Integer postId);
    void addComment(Comment comment);
    void deleteComment(Integer commentId, String username);
}
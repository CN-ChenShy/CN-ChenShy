package com.example.commentapp.service;
import com.example.commentapp.entity.Comment;
import java.util.List;

public interface CommentService {
    List<Comment> findByPostId(Integer postId);
    void addComment(Comment comment, String currentUsername);
    void deleteComment(Integer commentId, String currentUsername);
}
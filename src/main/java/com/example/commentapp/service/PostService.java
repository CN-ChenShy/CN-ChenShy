package com.example.commentapp.service;

import com.example.commentapp.entity.Post;
import java.util.List;

public interface PostService {
    List<Post> findAll();
    Post findById(Integer id);
    void addPost(Post post);
    void deletePost(Integer postId, String username);
}
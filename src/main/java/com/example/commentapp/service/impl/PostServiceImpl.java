package com.example.commentapp.service.impl;

import com.example.commentapp.entity.Post;
import com.example.commentapp.repository.PostRepository;
import com.example.commentapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post findById(Integer id) {
        Optional<Post> optional = postRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public void addPost(Post post) {
        // 这里是业务逻辑：admin 校验
        if (post.getUsername() == null || !post.getUsername().equals("admin")) {
            throw new RuntimeException("--403--仅限管理员可发帖");
        }
        postRepository.save(post);
    }

    @Override
    public void deletePost(Integer postId, String username) {
        // 只有 admin 能删
        if (!"admin".equals(username)) {
            throw new RuntimeException("只有管理员可以删除帖子");
        }
        postRepository.deleteById(postId);
    }
}
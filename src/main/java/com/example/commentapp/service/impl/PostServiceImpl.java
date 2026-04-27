package com.example.commentapp.service.impl;

import com.example.commentapp.entity.Post;
import com.example.commentapp.repository.PostRepository;
import com.example.commentapp.repository.CommentRepository;
import com.example.commentapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post findById(Integer id) {
        Optional<Post> optional = postRepository.findById(id);
        return optional.orElse(null);
    }

    // 用户名从后端取，不从前端取
    @Override
    public void addPost(Post post, String currentUsername) {
        // 管理员校验（从后端取）
        if (currentUsername == null || !currentUsername.equals("admin")) {
            throw new RuntimeException("--403--仅限管理员可发帖");
        }
        post.setUsername(currentUsername); // 用户名后端赋值
        postRepository.save(post);
    }

    // 删除不需要前端传username
    @Override
    @Transactional // 加事务，保证同时删除
    public void deletePost(Integer postId, String currentUsername) {
        if (!"admin".equals(currentUsername)) {
            throw new RuntimeException("只有管理员可以删除帖子");
        }

        // 先删除该帖子下所有评论
        commentRepository.deleteByPostId(postId);

        // 再删除帖子
        postRepository.deleteById(postId);
    }
}
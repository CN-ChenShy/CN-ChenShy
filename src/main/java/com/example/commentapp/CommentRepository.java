package com.example.commentapp;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    //根据帖子的ID查询所有评论
    List<Comment> findByPostId(Integer postId);
}

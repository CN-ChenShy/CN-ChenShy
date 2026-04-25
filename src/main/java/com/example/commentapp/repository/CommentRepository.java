package com.example.commentapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.commentapp.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    //根据帖子的ID查询所有评论
    List<Comment> findByPostId(Integer postId);
    void deleteByPostId(Integer postid);
}

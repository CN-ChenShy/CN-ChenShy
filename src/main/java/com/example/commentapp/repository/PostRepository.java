package com.example.commentapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.commentapp.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

}
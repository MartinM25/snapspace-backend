package com.snapspace.controller;

import com.snapspace.model.Post;
import com.snapspace.util.JwtUtil;
import com.snapspace.service.PostService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

  @Autowired
  private PostService postService;

  @Autowired
  private JwtUtil jwtUtil;

  // Endpoint for creating a post
  @PostMapping("/create")
  public ResponseEntity<Post> createPost(@RequestBody String content, @RequestHeader("Authorization") String authHeader) {

    String token = null;
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      token = authHeader.substring(7); // Remove "Bearer " prefix
    }

    if (token == null) {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN); // No token provided
    }

    String creatorId = JwtUtil.extractUserId(token);

    if (content.length() > 255) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Post newPost = postService.createPost(content, creatorId);
    return new ResponseEntity<>(newPost, HttpStatus.CREATED);
  }

  // Endpoint for liking a post
  @PostMapping("/like/{postId}")
  public ResponseEntity<Post> likePost(@PathVariable String postId) {
    Post post = postService.likePost(postId);
    return new ResponseEntity<>(post, HttpStatus.OK);
  }

}

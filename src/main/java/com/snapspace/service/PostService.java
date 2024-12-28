package com.snapspace.service;

import com.snapspace.model.Post;
import com.snapspace.repository.IPostRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PostService {

  @Autowired
  private IPostRepository postRepository;

  // Save a new post
  public Post createPost(String content, String creatorId) {
    Post post = new Post(content, creatorId);
    return postRepository.save(post);
  }

  // Increment likes on a post
  public Post likePost(String postId) {
    Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
    post.setLikes(post.getLikes() + 1);
    return postRepository.save(post);
  }
}

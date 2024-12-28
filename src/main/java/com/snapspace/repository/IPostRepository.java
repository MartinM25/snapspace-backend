package com.snapspace.repository;

import com.snapspace.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IPostRepository extends MongoRepository<Post, String> {

}


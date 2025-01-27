package org.blog.repository;

import org.blog.model.Post;

import java.util.Optional;

public interface CustomPostRepository {
    Optional<Post> findPostWithComments(Long postId);
}

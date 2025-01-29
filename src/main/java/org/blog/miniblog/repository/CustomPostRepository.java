package org.blog.miniblog.repository;

import org.blog.miniblog.model.Post;

import java.util.Optional;

public interface CustomPostRepository {
    Optional<Post> findPostWithComments(Long postId);
}

package org.blog.miniblog.repository;

import org.blog.miniblog.model.Like;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository {
     Optional<Like> findByLikeKey(Long userId, Long postId);
     void save(Like like);
     void delete(Like like);
}

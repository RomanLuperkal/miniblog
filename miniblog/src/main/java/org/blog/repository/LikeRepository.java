package org.blog.repository;

import org.blog.model.Like;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository {
     Optional<Like> findByLikeKey(Long userId, Long postId);
     void save(Like like);
     void delete(Like like);
}

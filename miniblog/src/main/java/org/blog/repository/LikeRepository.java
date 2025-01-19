package org.blog.repository;

import org.blog.model.Like;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends CrudRepository<Like, Like.LikeKey> {
    boolean existsLikeByLikeKey(Like.LikeKey likeKey);
    //Like findLikeByLikeKey(Like.LikeKey likeKey);

}

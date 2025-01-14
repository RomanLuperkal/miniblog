package org.blog.repository;

import org.blog.model.Post;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

    @Query("""
            SELECT * FROM Post p WHERE p.tag in (:tags)
            """)
    List<Post> getPostsByTags(@Param("tags")List<String> tags);
}

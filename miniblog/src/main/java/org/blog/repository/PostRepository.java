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
            SELECT * FROM Post p WHERE p.tag LIKE CONCAT('%', :tags, '%')
            LIMIT :size OFFSET :offset
            """)
    List<Post> getPostsByTags(@Param("tags")String tags, @Param("size")int size,  @Param("offset")int offset);

    @Query("""
            SELECT COUNT(*) FROM Post p WHERE p.tag LIKE CONCAT('%', :tags, '%')
            """)
    long countPostByTags(@Param("tags") String tags);

    @Query("""
            SELECT * FROM Post p
            LIMIT :size OFFSET :offset
            """)
    List<Post> getPost(@Param("size")int size,  @Param("offset")int offset);
}

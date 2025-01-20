package org.blog.repository;

import org.blog.model.Post;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepository extends CrudRepository<Post, Long>, CustomPostRepository {

    @Query("""
            SELECT distinct (p.post_id), owner_id, post_name, image, text, comments_count, likes_count, tag_id, t.post_id, tag_name
                  FROM Post p
                           JOIN Tag t ON p.post_id = t.post_id
                  WHERE t.tag_name IN (:tags)
                  ORDER BY post_id DESC
                  LIMIT :size OFFSET :offset
            """)
    List<Post> getPostsByTags(@Param("tags")List<String> tags, @Param("size")int size,  @Param("offset")int offset);

    @Query("""
            SELECT COUNT(*)
            FROM (SELECT distinct (p.post_id), owner_id, post_name, image, text, comments_count, likes_count, tag_id, t.post_id, tag_name
                  FROM Post p
                           JOIN Tag t ON p.post_id = t.post_id
                  WHERE t.tag_name IN (:tags)
                  LIMIT :size OFFSET :offset) as res;
            """)
    Long countPostByTags(@Param("tags") List<String> tags, @Param("size")int size,  @Param("offset")int offset);

    @Query("""
            SELECT * FROM Post p
            ORDER BY post_id DESC
            LIMIT :size OFFSET :offset
            """)
    List<Post> getPost(@Param("size")int size,  @Param("offset")int offset);

    @Query("""
            SELECT COUNT(*) FROM (SELECT p.post_id FROM Post p
            LIMIT :size OFFSET :offset) as res
            """)
    Long countPost(@Param("size")int size,  @Param("offset") int offset);
}

package org.blog.repository.impl;

import lombok.RequiredArgsConstructor;
import org.blog.extractor.post.PostWithCommentsExtractor;
import org.blog.model.Post;
import org.blog.repository.CustomPostRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomPostRepositoryImpl implements CustomPostRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public Optional<Post> findPostWithComments(Long postId) {
        String sql = """
            SELECT p.post_id,
                   p.owner_id,
                   post_name,
                   image,
                   p.text,
                   tag,
                   comments_count,
                   likes_count,
                   comment_id,
                   c.owner_id as owner_comment_id,
                   c.post_id as post_comment_id,
                   c.text as comment_text,
                   user_id,
                   login,
                   pwd,
                   firstname,
                   lastname
            FROM post p
                              LEFT JOIN comment c ON p.post_id = c.post_id
                              LEFT JOIN usr u ON c.owner_id = u.user_id
            WHERE p.post_id = :postId
        """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("postId", postId);

        return Optional.ofNullable(jdbcOperations.query(sql, params, new PostWithCommentsExtractor()));
    }
}

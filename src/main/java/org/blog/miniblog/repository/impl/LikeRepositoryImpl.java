package org.blog.miniblog.repository.impl;

import lombok.RequiredArgsConstructor;
import org.blog.miniblog.model.Like;
import org.blog.miniblog.repository.LikeRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public Optional<Like> findByLikeKey(Long userId, Long postId) {
        String sql = "SELECT * FROM likes WHERE user_id = :userId AND post_id = :postId";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("postId", postId);

        return jdbcOperations.query(sql, params, (rs, rowNum) -> {
            Like like = new Like();
            like.setUserId(rs.getLong("user_id"));
            like.setPostId(rs.getLong("post_id"));
            return like;
        }).stream().findFirst();
    }

    @Override
    public void save(Like like) {
        String sql = "INSERT INTO likes (user_id, post_id) VALUES (:userId, :postId)";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", like.getUserId())
                .addValue("postId", like.getPostId());

        jdbcOperations.update(sql, params);
    }

    @Override
    public void delete(Like like) {
        String sql = "DELETE FROM likes where user_id = :userId AND post_id = :postId";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", like.getUserId())
                .addValue("postId", like.getPostId());

        jdbcOperations.update(sql, params);
    }
}

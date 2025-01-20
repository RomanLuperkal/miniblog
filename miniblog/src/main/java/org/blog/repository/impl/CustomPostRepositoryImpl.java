package org.blog.repository.impl;

import lombok.RequiredArgsConstructor;
import org.blog.model.Comment;
import org.blog.model.Post;
import org.blog.model.Tag;
import org.blog.model.User;
import org.blog.repository.CustomPostRepository;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
                   t.tag_name,
                   t.tag_id,
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
                              LEFT JOIN tag t ON p.post_id = t.post_id
            WHERE p.post_id = :postId
        """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("postId", postId);

        ResultSetExtractor<Post> resultSetExtractor = (rs) -> {
            Post post = null;
            Set<Comment> comments = new HashSet<>();
            Set<Tag> tags = new HashSet<>();

            while (rs.next()) {
                if (post == null) {
                    post = new Post();
                    post.setPostId(rs.getLong("post_id"));
                    post.setOwnerId(rs.getLong("owner_id"));
                    post.setPostName(rs.getString("post_name"));
                    post.setImage(rs.getBytes("image"));
                    post.setText(rs.getString("text"));
                    post.setCommentsCount(rs.getInt("comments_count"));
                    post.setLikesCount(rs.getLong("likes_count"));
                }

                long commentId = rs.getLong("comment_id");
                if (commentId != 0) {
                    Comment comment = new Comment();
                    comment.setCommentId(commentId);
                    comment.setOwnerId(rs.getLong("owner_comment_id"));
                    comment.setText(rs.getString("comment_text"));
                    comment.setPostId(rs.getLong("post_comment_id"));

                    User owner = new User();
                    owner.setUserId(rs.getLong("owner_id"));
                    owner.setFirstName(rs.getString("firstname"));
                    owner.setLastName(rs.getString("lastname"));
                    comment.setOwner(owner);

                    comments.add(comment);
                }

                long tagId = rs.getLong("tag_id");
                if (tagId != 0) {
                    Tag tag = new Tag();
                    tag.setTagId(tagId);
                    tag.setPostId(rs.getLong("post_id"));
                    tag.setTagName(rs.getString("tag_name"));
                    tags.add(tag);
                }
            }

            if (post != null) {
                post.setComments(comments);
                post.setTags(tags);
            }
            return post;
        };

        return Optional.ofNullable(jdbcOperations.query(sql, params, resultSetExtractor));
    }
}

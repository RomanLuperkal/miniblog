package org.blog.extractor.post;

import org.blog.model.Comment;
import org.blog.model.Post;
import org.blog.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class PostWithCommentsExtractor implements ResultSetExtractor<Post> {

    @Override
    public Post extractData(ResultSet rs) throws SQLException, DataAccessException {
        Post post = null;
        Set<Comment> comments = new HashSet<>();

        while (rs.next()) {
            if (post == null) {
                post = new Post();
                post.setPostId(rs.getLong("post_id"));
                post.setOwnerId(rs.getLong("owner_id"));
                post.setPostName(rs.getString("post_name"));
                post.setImage(rs.getBytes("image"));
                post.setText(rs.getString("text"));
                post.setTag(rs.getString("tag"));
                post.setCommentsCount(rs.getLong("comments_count"));
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
        }

        if (post != null) {
            post.setComments(comments);
        }
        return post;
    }
}

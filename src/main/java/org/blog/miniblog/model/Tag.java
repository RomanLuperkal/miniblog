package org.blog.miniblog.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.util.Objects;

@Getter
@Setter
public class Tag {
    @Id
    @Column("tag_id")
    private Long tagId;
    @Column("post_id")
    private Long postId;
    @Column("tag_name")
    private String tagName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag tag)) return false;
        return Objects.equals(tagId, tag.tagId) && Objects.equals(tagName, tag.tagName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagId, tagName);
    }
}

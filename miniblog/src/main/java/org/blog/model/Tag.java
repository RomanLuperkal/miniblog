package org.blog.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

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
}

package org.blog.repository;

import org.blog.model.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public interface PostRepository extends CrudRepository<Post, Long> {
}

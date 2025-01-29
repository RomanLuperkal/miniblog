package org.blog.miniblog.service;

import java.util.List;

public interface TagService {
    void deleteTags(List<Long> tagIds);
}

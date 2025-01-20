package org.blog.mapper;

import org.blog.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface TagMapper {

    @Mapping(target = "tagName", source = "tag")
    Tag mapToTag(String tag);

    Set<Tag> setStringToSetTags(Set<String> tags);
}

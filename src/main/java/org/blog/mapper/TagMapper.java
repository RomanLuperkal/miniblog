package org.blog.mapper;

import org.blog.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface TagMapper {

    @Mapping(target = "tagName", source = "tag")
    Tag mapToTag(String tag);

    @Named("setStringToSetTags")
    Set<Tag> setStringToSetTags(Set<String> tags);
}

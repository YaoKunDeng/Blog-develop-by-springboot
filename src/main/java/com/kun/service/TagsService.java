package com.kun.service;

import com.kun.pojo.Tag;
import com.kun.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface TagsService {

    Tag saveTag(Tag tag);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    //分页查询
    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag();

    List<Tag> listTag(String ids);

    Tag updateTag(Long id, Tag Tag);

    void deleteTag(Long id);

}

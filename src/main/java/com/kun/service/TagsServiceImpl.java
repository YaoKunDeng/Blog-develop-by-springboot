package com.kun.service;

import com.kun.NotFoundException;
import com.kun.dao.TagsRepository;
import com.kun.dao.TypeRepository;
import com.kun.pojo.Tag;
import com.kun.pojo.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagsServiceImpl implements TagsService {

    @Autowired
    private TagsRepository tagsRepository;

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagsRepository.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagsRepository.getById(id);
    }

    @Override
    @Transactional
    public Tag getTagByName(String name) {
        return tagsRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagsRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagsRepository.getById(id);
        if(t == null){
            throw  new NotFoundException("id 不存在该类型");
        }
        BeanUtils.copyProperties(tag,t);

        return tagsRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagsRepository.deleteById(id);
    }
}

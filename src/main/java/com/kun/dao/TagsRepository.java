package com.kun.dao;

import com.kun.pojo.Tag;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagsRepository extends JpaRepository<Tag, Long> {

    Tag findByName(String name);
}

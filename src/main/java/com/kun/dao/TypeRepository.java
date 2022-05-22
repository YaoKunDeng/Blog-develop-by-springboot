package com.kun.dao;

import com.kun.pojo.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type, Long> {

    Type findByName(String name);
}

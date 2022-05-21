package com.kun.service;

import com.kun.pojo.User;
import org.springframework.stereotype.Repository;


public interface UserService {
    User checkUser(String username, String password);
}

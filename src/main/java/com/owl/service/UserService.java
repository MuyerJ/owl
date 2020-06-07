package com.owl.service;

import com.owl.dao.UserDao;
import com.owl.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getUserById(String userId){
        User user = userDao.getUserById(userId);
        return user;
    }
}

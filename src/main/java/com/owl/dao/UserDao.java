package com.owl.dao;

import com.owl.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    User getUserById(String id);
}

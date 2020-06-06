package com.owl.converter;

import com.owl.model.User;
import org.springframework.core.convert.converter.Converter;

public class UserConverter implements Converter<String, User> {

    @Override
    public User convert(String string) {
        String[] split = string.split("#");
        System.out.println(string);
        User user = new User();
//        user.setName(split[0]);
//        user.setId(split[1]);
        user.setName("qq");
        user.setId("11");
        return user;
    }
}

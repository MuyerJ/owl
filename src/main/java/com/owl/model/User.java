package com.owl.model;

import java.io.Serializable;

public class User implements Serializable {


    /** 用户id */
    private String id;

    /** 用户姓名 */
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

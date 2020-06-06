package com.owl.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class User implements Serializable {
    /** 用户id */
    private String id;

    /** 用户姓名 */
    private String name;

    /** 课程 */
    private List<String> subject;

    /** 各科分数 */
    private Map<String,Integer> score;

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

    public List<String> getSubject() {
        return subject;
    }

    public void setSubject(List<String> subject) {
        this.subject = subject;
    }

    public Map<String, Integer> getScore() {
        return score;
    }

    public void setScore(Map<String, Integer> score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", subject=" + subject +
                ", score=" + score +
                '}';
    }
}

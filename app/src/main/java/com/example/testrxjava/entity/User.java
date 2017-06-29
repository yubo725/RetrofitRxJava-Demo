package com.example.testrxjava.entity;

import java.io.Serializable;

/**
 * Created by yubo on 2017/6/29.
 */

public class User implements Serializable {

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }
}

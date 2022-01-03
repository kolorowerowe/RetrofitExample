package com.example.retrofitexample.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {
    private Meta meta;
    private List<User> data;

    public UserResponse(Meta meta, List<User> users) {
        this.meta = meta;
        this.data = users;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<User> getUsers() {
        return data;
    }

    public void setUsers(List<User> users) {
        this.data = users;
    }
}


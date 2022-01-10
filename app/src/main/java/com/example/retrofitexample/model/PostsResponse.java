package com.example.retrofitexample.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostsResponse {
    private Meta meta;
    private List<Post> data;

    public PostsResponse(Meta meta, List<Post> data) {
        this.meta = meta;
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Post> getData() {
        return data;
    }

    public void setData(List<Post> data) {
        this.data = data;
    }
}

package com.example.retrofitexample.model;

import java.util.List;

public class PostResponse {
    private Meta meta;
    private Post data;

    public PostResponse(Meta meta, Post data) {
        this.meta = meta;
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Post getData() {
        return data;
    }

    public void setData(Post data) {
        this.data = data;
    }
}

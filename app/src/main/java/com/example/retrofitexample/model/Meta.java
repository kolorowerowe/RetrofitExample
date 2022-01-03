package com.example.retrofitexample.model;

public class Meta {
    private Pagination pagination;

    public Meta(Pagination pagination) {
        this.pagination = pagination;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}


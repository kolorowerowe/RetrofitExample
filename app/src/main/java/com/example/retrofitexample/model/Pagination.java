package com.example.retrofitexample.model;

public class Pagination {
    private int total;
    private int pages;
    private int page;
    private int limit;
    private Links links;

    public Pagination(int total, int pages, int page, int limit, Links links) {
        this.total = total;
        this.pages = pages;
        this.page = page;
        this.limit = limit;
        this.links = links;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }
}


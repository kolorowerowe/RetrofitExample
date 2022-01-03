package com.example.retrofitexample.model;

public class Links {
    private String previous;
    private String current;
    private String next;

    public Links(String previous, String current, String next) {
        this.previous = previous;
        this.current = current;
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}

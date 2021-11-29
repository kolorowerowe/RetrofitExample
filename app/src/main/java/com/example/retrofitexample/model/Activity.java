package com.example.retrofitexample.model;


public class Activity {
    public String getActivity() {
        return activity;
    }

    public String getType() {
        return type;
    }

    public Double getAccessibility() {
        return accessibility;
    }

    private String activity;
    private String type;
    private Double accessibility;

    public Activity(String activity, String type, Double accessibility) {
        this.activity = activity;
        this.type = type;
        this.accessibility = accessibility;
    }
}

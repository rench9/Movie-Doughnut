package com.yahoo.r4hu7.moviesdoughnut.ui.dependency.adapter;

public class Filter {
    private int sortOrder;
    private String name;

    public Filter(int id, String name) {
        this.sortOrder = id;
        this.name = name;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public String getName() {
        return name;
    }
}

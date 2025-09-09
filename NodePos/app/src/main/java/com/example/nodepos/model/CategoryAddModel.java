package com.example.nodepos.model;

public class CategoryAddModel {
    private String id;
    private String name;

    public CategoryAddModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }

    // Supaya dropdown hanya menampilkan nama
    @Override
    public String toString() {
        return name;
    }
}

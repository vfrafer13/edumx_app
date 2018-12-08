package com.example.nezzi.edumx.models;

import java.io.Serializable;

public class Category implements Serializable {

    private int id;
    private String name;
    private String iconUrl;

    public Category() {}

    public Category(int id, String name, String iconUrl) {
        this.setId(id);
        this.setName(name);
        this.setIconUrl(iconUrl);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}

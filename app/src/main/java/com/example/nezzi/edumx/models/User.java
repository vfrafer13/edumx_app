package com.example.nezzi.edumx.models;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String name;
    private String email;
    private int sex;
    private int role;

    public User(int id, String name, String email, int sex, int role) {
        this.setId(id);
        this.setName(name);
        this.setEmail(email);
        this.setSex(sex);
        this.setRole(role);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}

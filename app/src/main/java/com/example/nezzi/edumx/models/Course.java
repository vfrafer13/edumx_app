package com.example.nezzi.edumx.models;

import java.io.Serializable;

public class Course implements Serializable {

    private int id;
    private String name;
    private String description;
    private double price;
    private int instructor;
    private int duration;
    private String requirements;
    private double rating;
    private String topics;

    public Course () {

    }

    public Course(int id, String name, String description, double price, int instructor, int duration, String requirements, double rating, String topics) {
        this.setId(id);
        this.setName(name);
        this.setDescription(description);
        this.setPrice(price);
        this.setInstructor(instructor);
        this.setDuration(duration);
        this.setRequirements(requirements);
        this.setRating(rating);
        this.setTopics(topics);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInstructor() {
        return instructor;
    }

    public void setInstructor(int instructor) {
        this.instructor = instructor;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }
}

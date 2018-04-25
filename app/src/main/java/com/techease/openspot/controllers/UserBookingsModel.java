package com.techease.openspot.controllers;

/**
 * Created by Adamnoor on 3/22/2018.
 */

public class UserBookingsModel {
    String id;
    String name;
    String location;
    String information;
    String image;
    String type;
    String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

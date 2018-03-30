package com.techease.openspot.controllers;

/**
 * Created by Adamnoor on 3/21/2018.
 */

public class GroundDetailTimesModel {

    int timeId;
    String timeFrom;
    String timeTo;
    String price;
    String type;
    String IsBooked;


    public String getIsBooked() {
        return IsBooked;
    }

    public void setIsBooked(String isBooked) {
        IsBooked = isBooked;
    }


    public int getTimeId() {
        return timeId;
    }

    public void setTimeId(int timeId) {
        this.timeId = timeId;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}


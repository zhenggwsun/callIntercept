package com.eric.callintercept.dao.object;

import android.provider.ContactsContract;

import java.util.Date;

public class CallLogModel {

    private String name;

    private String number;

    private Date date;

    private Integer duration;

    private Integer type;

    public CallLogModel(String name, String number, Date date, Integer duration, Integer type) {
        this.name = name;
        this.number = number;
        this.date = date;
        this.duration = duration;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

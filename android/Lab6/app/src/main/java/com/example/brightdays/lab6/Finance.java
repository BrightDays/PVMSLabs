package com.example.brightdays.lab6;

/**
 * Created by User-PC on 01.04.2015.
 */
public class Finance {

    private String category;
    private String type;
    private String date;
    private int value;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    Finance()
    {

    }

    Finance(String category, String type, String date, int value) {
        this.category = category;
        this.type = type;
        this.date = date;
        this.value = value;
    }
}

package com.example.arnarfreyr.dicegame2000;

/**
 * Created by arnarfreyr on 27.6.2017.
 */

public class UserData {

    private String NAME = null;
    private Integer SCORE = 0;
    private String DATE = null;

    public UserData() {}

    public void setName(String name) {
        NAME = name;
    }

    public String getName() {
        return NAME;
    }

    public void setScore(Integer score) {
        SCORE = score;
    }

    public Integer getScore() {
        return SCORE;
    }

    public void setDate(String date) {
        DATE = date;
    }

    public String getDate() {
        return DATE;
    }
}

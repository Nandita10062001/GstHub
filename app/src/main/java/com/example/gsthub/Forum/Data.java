package com.example.gsthub.Forum;

import java.io.Serializable;

public class Data implements Serializable {
    public String Username, Description, Time, Date;

    public Data(String username, String description, String time, String date) {
        Username = username;
        Description = description;
        Time = time;
        Date = date;

    }

    public String getUsername() { return Username; }
    public void setUsername(String username) { Username = username; }

    public String getDescription() { return Description; }
    public void setDescription(String description) { Description = description; }

    public String getTime() { return Time;}
    public void setTime(String time) {Time = time;}

    public String getDate() {return Date;}
    public void setDate(String date) {Date = date;}
}

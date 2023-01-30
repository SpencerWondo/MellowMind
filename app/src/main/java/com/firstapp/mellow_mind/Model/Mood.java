package com.firstapp.mellow_mind.Model;

public class Mood {
    private String moodKey;
    private String Date;
    private String Mood;

    public Mood(String moodKey, String Date, String Mood){
        this.moodKey = moodKey;
        this.Date = Date;
        this.Mood = Mood;
    }

    public Mood(){

    }


    public String getMoodKey() {
        return moodKey;
    }

    public void setMoodKey(String moodKey) {
        this.moodKey = moodKey;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getMood() {
        return Mood;
    }

    public void setMood(String mood) {
        Mood = mood;
    }
}

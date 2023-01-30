package com.firstapp.mellow_mind.Model;

public class Task {
    private String Date;
    private String Date_num;
    private String TaskID;
    private String UserID;

    private Boolean clean;
    private Boolean exercise;
    private Boolean love;
    private Boolean scare;
    private Boolean shower;
    private Boolean sleep;
    private Boolean teeth;
    private Boolean water;


    public Task (String Date, String Date_num, String TaskID, String UserID, Boolean clean, Boolean exercise, Boolean love, Boolean scare, Boolean shower, Boolean sleep, Boolean teeth, Boolean water){
        this.Date = Date;
        this.Date_num = Date_num;
        this.TaskID = TaskID;
        this.UserID = UserID;


        this.clean = clean;
        this.exercise = exercise;
        this.love = love;
        this.scare = scare;
        this.shower = shower;
        this.sleep = sleep;
        this.teeth = teeth;
        this.water = water;
    }

    public Task(){

    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDate_num() {
        return Date_num;
    }

    public void setDate_num(String date_num) {
        Date_num = date_num;
    }

    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String taskID) {
        TaskID = taskID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public Boolean getClean() {
        return clean;
    }

    public void setClean(Boolean clean) {
        this.clean = clean;
    }

    public Boolean getExercise() {
        return exercise;
    }

    public void setExercise(Boolean exercise) {
        this.exercise = exercise;
    }

    public Boolean getLove() {
        return love;
    }

    public void setLove(Boolean love) {
        this.love = love;
    }

    public Boolean getScare() {
        return scare;
    }

    public void setScare(Boolean scare) {
        this.scare = scare;
    }

    public Boolean getShower() {
        return shower;
    }

    public void setShower(Boolean shower) {
        this.shower = shower;
    }

    public Boolean getSleep() {
        return sleep;
    }

    public void setSleep(Boolean sleep) {
        this.sleep = sleep;
    }

    public Boolean getTeeth() {
        return teeth;
    }

    public void setTeeth(Boolean teeth) {
        this.teeth = teeth;
    }

    public Boolean getWater() {
        return water;
    }

    public void setWater(Boolean water) {
        this.water = water;
    }
}

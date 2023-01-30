package com.firstapp.mellow_mind.Model;

public class User {
    private String bio;
    private String currentMood;
    private String fullname;
    private String id;
    private String imageURL;
    private String username;
    private String email;
    private String create_date;

    private String degree;
    private String address;
    private String phone;
    private String website;

    public long followers;
    public long likes;
    public long views;
    public long posts;

    public User(String bio, String degree, String address, String phone, String website, String currentMood, String fullname, String id, String imageURL, String username, String email, String create_date, long followers, long likes, long views, long posts){
        this.bio = bio;
        this.currentMood = currentMood;
        this.fullname = fullname;
        this.id = id;
        this.imageURL = imageURL;
        this.username = username;
        this.email = email;
        this.create_date = create_date;

        this.degree = degree;
        this.address = address;
        this.phone = phone;
        this.website = website;

        this.followers = followers;
        this.likes = likes;
        this.views = views;
        this.posts = posts;

    }

    public User(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCurrentMood() {
        return currentMood;
    }

    public void setCurrentMood(String currentMood) {
        this.currentMood = currentMood;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public long getFollowers() {
        return followers;
    }

    public void setFollowers(long followers) {
        this.followers = followers;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public long getPosts() {
        return posts;
    }

    public void setPosts(long posts) {
        this.posts = posts;
    }
}


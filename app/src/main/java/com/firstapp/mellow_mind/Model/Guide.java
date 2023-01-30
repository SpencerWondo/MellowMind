package com.firstapp.mellow_mind.Model;

import java.util.Comparator;

public class Guide {
    private String title;
    private String time;
    private String body;
    private String guideID;
    private String keyword;
    private String imageURL;

    private String author;
    private String authorID;
    private String date;

    public long likes;
    public long views;


    public Guide (String title, String time, String body, String guideID, String keyword, String imageURL, long likes, long views, String author, String authorID, String date){
        this.title = title;
        this.time = time;
        this.body = body;
        this.keyword = keyword;
        this.imageURL = imageURL;
        this.guideID = guideID;
        this.likes = likes;
        this.views = views;
        this.author = author;
        this.authorID = authorID;
        this.date = date;
    }



    public static Comparator<Guide> GuideTrending = new Comparator<Guide>() {
        @Override
        public int compare(Guide o1, Guide o2) {
            return Long.compare(o2.getLikes(), o1.getLikes());
        }
    };

    public static Comparator<Guide> GuidePopular = new Comparator<Guide>() {
        @Override
        public int compare(Guide o1, Guide o2) {
            return Long.compare(o2.getViews(), o1.getViews());
        }
    };

    public static Comparator<Guide> GuideAZ = new Comparator<Guide>() {
        @Override
        public int compare(Guide o1, Guide o2) {
            return o1.getKeyword().compareTo(o2.getKeyword());
        }
    };

    public static Comparator<Guide> GuideZA = new Comparator<Guide>() {
        @Override
        public int compare(Guide o1, Guide o2) {
            return o2.getKeyword().compareTo(o1.getKeyword());
        }
    };

    public static Comparator<Guide> GuideON = new Comparator<Guide>() {
        @Override
        public int compare(Guide o1, Guide o2) {
            return o1.getDate().compareTo(o2.getDate());
        }
    };



    public Guide(){

    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getGuideID() {
        return guideID;
    }

    public void setGuideID(String guideID) {
        this.guideID = guideID;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}



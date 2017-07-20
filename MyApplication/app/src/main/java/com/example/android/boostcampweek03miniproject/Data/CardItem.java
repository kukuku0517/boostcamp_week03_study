package com.example.android.boostcampweek03miniproject.Data;

import java.util.Date;

/**
 * Created by samsung on 2017-07-13.
 */


public class CardItem {

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private String image, title, content;
    private String location,phone;
    private int distance, likes;
    private boolean isClicked;
    private Date date;

    public CardItem(String image, String title, String content, String location, String phone, int distance, int likes, boolean isClicked, Date date) {
        this.image = image;
        this.title = title;
        this.content = content;
        this.location = location;
        this.phone = phone;
        this.distance = distance;
        this.likes = likes;
        this.isClicked = isClicked;
        this.date = date;
    }

    //TODO 1: firebase사용할때 필요함. 왜인지는 잘..
    public CardItem(){

    }


}
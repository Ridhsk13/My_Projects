package com.example.ridhs.bookmyhall;

/**
 * Created by RIDHS on 4/29/2017.
 */

public class Review {

    String user_id;
    String hall_id;
    String review_id;
    String date;
    String review_msg;

    public Review() {
    }

    public Review(String user_id, String hall_id, String review_id, String date, String review_msg) {
        this.user_id = user_id;
        this.hall_id = hall_id;
        this.review_id = review_id;
        this.date = date;
        this.review_msg = review_msg;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getHall_id() {
        return hall_id;
    }

    public void setHall_id(String hall_id) {
        this.hall_id = hall_id;
    }

    public String getReview_id() {
        return review_id;
    }

    public void setReview_id(String review_id) {
        this.review_id = review_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReview_msg() {
        return review_msg;
    }

    public void setReview_msg(String review_msg) {
        this.review_msg = review_msg;
    }
}

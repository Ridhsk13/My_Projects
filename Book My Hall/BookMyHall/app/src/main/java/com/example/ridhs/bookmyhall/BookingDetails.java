package com.example.ridhs.bookmyhall;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by RIDHS on 4/24/2017.
 */

public class BookingDetails /*implements Comparable<BookingDetails>*/{

    //String booking_id;
    String user_id;
    String hall_id;
    String booking_date;
    String booking_time;
    String hall_name;

    public BookingDetails() {
    }

    public String getHall_name() {
        return hall_name;
    }

    public void setHall_name(String hall_name) {
        this.hall_name = hall_name;
    }

    public BookingDetails(String user_id, String hall_id, String booking_date, String booking_time, String hall_name) {
        this.user_id = user_id;
        this.hall_id = hall_id;
        this.booking_date = booking_date;
        this.booking_time = booking_time;
        this.hall_name = hall_name;
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

    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }

    public String getBooking_time() {
        return booking_time;
    }

    public void setBooking_time(String booking_time) {
        this.booking_time = booking_time;
    }

  /*  public Date getDate(String mydate) throws ParseException {
        //String strDate = "2013-05-15T10:00:00-0700";
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        Date date = dateFormat.parse(mydate);
        return  date;
    }*/
/*    @Override
    public int compareTo(@NonNull BookingDetails bookingDetail){

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

        try {
            return dateFormat.parse(this.getBooking_date()).compareTo(dateFormat.parse(bookingDetail.getBooking_date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
       return 0;
        *//*
        try {
            return getDate(this.getBooking_date()).compareTo(getDate(bookingDetail.getBooking_date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;

*//*    }*/
}

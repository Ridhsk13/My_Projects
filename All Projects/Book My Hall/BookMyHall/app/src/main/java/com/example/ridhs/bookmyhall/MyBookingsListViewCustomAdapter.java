package com.example.ridhs.bookmyhall;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by RIDHS on 3/27/2017.
 */

public class MyBookingsListViewCustomAdapter extends BaseAdapter {

    String[] hallName;
    String[] date;
    String[] timeSlot;
    Activity context;
    ArrayList<BookingDetails> listOfHalls;
    ArrayList<String> listBookingId;


    public MyBookingsListViewCustomAdapter(Activity context, ArrayList<BookingDetails> listOfHalls, ArrayList<String> listBookingId) {
        this.hallName = hallName;
        this.date = date;
        this.timeSlot = timeSlot;
        this.context = context;
        this.listOfHalls = listOfHalls;
        this.listBookingId = listBookingId;
    }

    @Override
    public int getCount() {
        return listOfHalls.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView;

        if (convertView == null) {

            new View(context);
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.activity_my_bookings_listview_row, parent, false);

        } else {
            rowView = convertView;
        }

        TextView textViewHallName = (TextView) rowView.findViewById(R.id.activity_my_bookings_hall_name);
        TextView textViewDate = (TextView) rowView.findViewById(R.id.activity_my_bookings_date);
        TextView textViewTimeSlot = (TextView) rowView.findViewById(R.id.activity_my_bookings_time_slot);
        Button buttonCancle = (Button) rowView.findViewById(R.id.activity_my_bookings_cancel);

        textViewHallName.setText(listOfHalls.get(position).getHall_name());
        textViewDate.setText("Reservation Date : " + listOfHalls.get(position).getBooking_date());
        textViewTimeSlot.setText("Time Slot : " + listOfHalls.get(position).getBooking_time());

        buttonCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReviewDialog(position);
            }
        });
        return rowView;
    }
    public void showReviewDialog(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirm Cancel Booking");
        builder.setMessage("Are you sure you want to cancel your booking");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                        .getReference("User/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());
                databaseReference.child("user_bookings").child(listBookingId.get(position)).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Bookings");
                        databaseReference1.child(listBookingId.get(position)).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context, "Booking Cancelled successfully", Toast.LENGTH_SHORT).show();
                                context.recreate();
                            }
                        });
                    }
                });
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
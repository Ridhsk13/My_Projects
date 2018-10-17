package com.example.ridhs.bookmyhall;

import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MyBookings extends AppCompatActivity {

    public ListView listViewBookingList;
    public DatabaseReference referenceUser;
    public DatabaseReference referenceBookings;
    public ArrayList<BookingDetails> listOfBookedHalls = new ArrayList<BookingDetails>();
    public ArrayList<String> bookingIdList = new ArrayList<String>();

    private String[] hallName = {
            "Hall Name 1",
            "Hall Name 2",
            "Hall Name 3"};
    private String[] date = {
            "01/02/17",
            "05/14/17",
            "08/29/17"};
    private String[] timeSlot = {
            "9:00 - 11:30",
            "21:00 - 23:30",
            "15:00 - 17:30"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);

        getSupportActionBar().setTitle("My Bookings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        referenceUser = FirebaseDatabase.getInstance().getReference("User/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
        referenceBookings = FirebaseDatabase.getInstance().getReference("Bookings");
        listViewBookingList = (ListView) findViewById(R.id.activity_my_bookings_listview_booking_list);

        fetchDataFromServer();
    }

    public void setData() {
        listViewBookingList.setAdapter(new MyBookingsListViewCustomAdapter(this, listOfBookedHalls, bookingIdList));
    }

    public void fetchDataFromServer() {

        referenceUser.child("user_bookings").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    bookingIdList.add(child.getKey());
                    Log.d("User Bookings", child.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        referenceBookings.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if (checkId(child)) {
                        Log.d("Reference Bookings ", child.getKey());
                        listOfBookedHalls.add(child.getValue(BookingDetails.class));
                    }
                }
                setData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean checkId(DataSnapshot child) {
        Log.d("Check Id ", child.getKey());
        boolean gotTheKey = false;

        for (int i = 0; i < bookingIdList.size(); i++) {
            if (Objects.equals(bookingIdList.get(i), child.getKey())) {
                Log.d("Got the key ", child.getKey());
                gotTheKey = true;
                break;
            }
        }
        return gotTheKey;
    }
}

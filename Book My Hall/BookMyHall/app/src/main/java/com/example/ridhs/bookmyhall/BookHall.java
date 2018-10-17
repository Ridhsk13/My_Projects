package com.example.ridhs.bookmyhall;

import android.app.DatePickerDialog;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BookHall extends AppCompatActivity implements View.OnClickListener{

    Calendar calendar;
    EditText editTextDate;
    ImageView imageViewDate;
    Spinner spinnerTimeSlot;
    Button buttonCnfBooking;
    DatePickerDialog.OnDateSetListener date;
    DatabaseReference mReferenceUser,mReferenceBookings;
    FirebaseUser user;
    public String hallId,hallName;
    MyCustomProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_hall);

        progressDialog = new MyCustomProgressDialog(this);

        hallId = getIntent().getStringExtra("hall_id");
        hallName = getIntent().getStringExtra("hall_name");

        user = FirebaseAuth.getInstance().getCurrentUser();

        getSupportActionBar().setTitle(hallName);
        //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        //CreateProfile createProfile = new CreateProfile();
        mReferenceUser = FirebaseDatabase.getInstance().getReference("User/"+user.getUid());
        mReferenceBookings = FirebaseDatabase.getInstance().getReference("Bookings");

        calendar = Calendar.getInstance();

        editTextDate = (EditText) findViewById(R.id.book_hall_editText_date);
        imageViewDate = (ImageView) findViewById(R.id.book_hall_imageView_date);
        spinnerTimeSlot = (Spinner) findViewById(R.id.book_hall_spinner_time_slot);

        buttonCnfBooking = (Button) findViewById(R.id.book_hall_button_cnf_booking);

        imageViewDate.setOnClickListener(this);
        buttonCnfBooking.setOnClickListener(this);

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateEditText();
            }
        };

    }

    private void updateEditText() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editTextDate.setText(sdf.format(calendar.getTime()));
    }
    /*public int findLastIndex(){
        final int[] lastBookingCount = {0};
        mReferenceUser.child(user.getUid()).child("user_bookings").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lastBookingCount[0] = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return lastBookingCount[0];
    }
*/
    public void confirmBooking(){

  //      int nextIndex = findLastIndex();
        progressDialog.showProgressDialog("Loading...");
        DatabaseReference tempRef = mReferenceUser
                .child("user_bookings")
                .push();
        String key = tempRef.getKey();
        tempRef.setValue(hallId).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //Toast.makeText(BookHall.this, "Booking added to profile",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(BookHall.this, "Booking Failed. Try again.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        BookingDetails bookingDetails = new BookingDetails();
        bookingDetails.setUser_id(user.getUid());
        bookingDetails.setHall_id(hallId);
        bookingDetails.setHall_name(hallName);
        bookingDetails.setBooking_date(editTextDate.getText().toString());
        bookingDetails.setBooking_time(spinnerTimeSlot.getSelectedItem().toString());

        mReferenceBookings.child(key).setValue(bookingDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.hideProgressDialog();
                if (task.isSuccessful()){
                    Toast.makeText(BookHall.this, "Booking Successfully done",Toast.LENGTH_SHORT).show();
                    //Toast.makeText(BookHall.this, "Sorry this hall is booked. You are waitlisted.",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(BookHall.this, "Booking Failed. Try again.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.book_hall_imageView_date:
                new DatePickerDialog(BookHall.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.book_hall_button_cnf_booking:
                confirmBooking();
                //Log.d("Time Slot : " , spinnerTimeSlot.getSelectedItem().toString());
                break;
            default:
                Toast.makeText(BookHall.this, "Unknown Error. Try Again!", Toast.LENGTH_SHORT).show();
        }
    }
}

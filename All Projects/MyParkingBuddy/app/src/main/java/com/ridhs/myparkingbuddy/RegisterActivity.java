package com.ridhs.myparkingbuddy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    Button register;
    EditText fullName, email, phone, password, confirmpassword, school;
    Spinner option1, option2, option3;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference referenceUser;
    ArrayList<String> locationNames = new ArrayList<String>();
    DatabaseReference referenceLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = (Button) findViewById(R.id.activity_register_RegisterBT);
        fullName = (EditText) findViewById(R.id.activity_register_fullnameET);
        email = (EditText) findViewById(R.id.activity_register_emailET);
        phone = (EditText) findViewById(R.id.activity_register_phoneET);
        school = (EditText) findViewById(R.id.activity_register_schoolET);
        password = (EditText) findViewById(R.id.activity_register_passwordET);
        confirmpassword = (EditText) findViewById(R.id.activity_register_confirmpasswordET);
        option1 = (Spinner) findViewById(R.id.activity_register_preferred1spinner);
        option2 = (Spinner) findViewById(R.id.activity_register_preferred2spinner);
        option3 = (Spinner) findViewById(R.id.activity_register_preferred3spinner);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        referenceUser = firebaseDatabase.getReference("userdetails");
        referenceLocations = firebaseDatabase.getReference("parkinglocations");

        getLocationNames();
       //if (locationNames.isEmpty()) {
        //    Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
        //} else {
            System.out.println(locationNames);
       // }


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 registerUser(email.getText().toString(),password.getText().toString());
            }
        });

    }

    public void getLocationNames() {
        final ArrayList<String> namelist = new ArrayList<String>();

        referenceLocations.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    namelist.add(String.valueOf(snapshot.child("loc_name").getValue()));
                }
                Log.d("NameList",namelist.toString());
                initializeSpinner(namelist);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void initializeSpinner(ArrayList<String> namelist){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, namelist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        option1.setAdapter(adapter);
        option2.setAdapter(adapter);
        option3.setAdapter(adapter);
    }
    public void registerUser(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    firebaseUser = firebaseAuth.getCurrentUser();
                    submitDataToFirebase();
                } else {
                    System.out.println(task.getResult());
                    Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void submitDataToFirebase() {
        User user = new User();
        user.setEmail(email.getText().toString());
        user.setPhone(phone.getText().toString());
        user.setUser_name(fullName.getText().toString());
        user.setSchool(school.getText().toString());
        user.setPref1(option1.getSelectedItem().toString());
        user.setPref2(option2.getSelectedItem().toString());
        user.setPref3(option3.getSelectedItem().toString());
        System.out.println(user);
        referenceUser.child(firebaseUser.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

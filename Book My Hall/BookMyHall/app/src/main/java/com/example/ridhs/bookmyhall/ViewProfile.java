package com.example.ridhs.bookmyhall;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ViewProfile extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextName, /*editTextEmail,*/ editTextContact;
    private Button buttonUpdateProfile, buttonUpdatePwd, buttonViewCancleBookings, buttonSubmit;
    private FirebaseUser firebaseUser;
    private DatabaseReference referenceUser, referenceBookings;
    private MyCustomProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new MyCustomProgressDialog(this);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        referenceUser = FirebaseDatabase.getInstance().getReference("User/"+firebaseUser.getUid());

        editTextName = (EditText) findViewById(R.id.view_profile_editText_name);
        //editTextEmail = (EditText) findViewById(R.id.view_profile_editText_email);
        editTextContact = (EditText) findViewById(R.id.view_profile_editText_contact);

        buttonSubmit = (Button) findViewById(R.id.view_profile_button_submit);
        buttonUpdateProfile  = (Button) findViewById(R.id.view_profile_button_update_profile);
        buttonUpdatePwd = (Button) findViewById(R.id.view_profile_button_update_password);
        buttonViewCancleBookings = (Button) findViewById(R.id.view_profile_button_view_cancel_bookings);

        buttonSubmit.setVisibility(View.GONE);

        buttonUpdateProfile.setOnClickListener(this);
        buttonUpdatePwd.setOnClickListener(this);
        buttonViewCancleBookings.setOnClickListener(this);
        setValues();

    }
    public void setValues(){
        editTextName.setEnabled(false);
        editTextContact.setEnabled(false);

        buttonSubmit.setVisibility(View.GONE);
        buttonViewCancleBookings.setVisibility(View.VISIBLE);
        buttonUpdatePwd.setVisibility(View.VISIBLE);
        buttonUpdateProfile.setVisibility(View.VISIBLE);



        referenceUser.child("user_name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                editTextName.setText((CharSequence) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        referenceUser.child("user_contact_no").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                editTextContact.setText((CharSequence) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void changeFunctionalty(){
        editTextName.setEnabled(true);
        //editTextEmail.setEnabled(true);
        editTextContact.setEnabled(true);

        buttonViewCancleBookings.setVisibility(View.GONE);
        buttonUpdatePwd.setVisibility(View.GONE);
        buttonUpdateProfile.setVisibility(View.GONE);
        buttonSubmit.setVisibility(View.VISIBLE);

        buttonSubmit.setOnClickListener(this);
    }

    public void updateUserProfile(){

        progressDialog.showProgressDialog("Updating Profile...");
        final boolean[] isSuccessfull = {true};
        OnCompleteListener listener = new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (!task.isSuccessful()){
                    isSuccessfull[0] = false;
                }
            }
        };
        if (!TextUtils.isEmpty(editTextName.getText().toString())){
            referenceUser.child("user_name").setValue(editTextName.getText().toString()).addOnCompleteListener(listener);
        }else {
            editTextName.setError("Required");
        }

        if (!TextUtils.isEmpty(editTextContact.getText().toString())){
            referenceUser.child("user_contact_no").setValue(editTextContact.getText().toString()).addOnCompleteListener(listener);
        }else {
            editTextContact.setError("Required");
        }

        if (isSuccessfull[0]){
            progressDialog.hideProgressDialog();
            setValues();
            Toast.makeText(ViewProfile.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
        }else {
            progressDialog.hideProgressDialog();
            Toast.makeText(ViewProfile.this, "Profile Updation failed. Try Again", Toast.LENGTH_SHORT).show();
        }

    }
    public void ShowMyCustomDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View view = inflater.inflate(R.layout.activity_create_profile_update_password, null);
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        progressDialog.showProgressDialog("Loading...");
                        //EditText editTextOld = (EditText) findViewById(R.id.dialog_update_profile_old_password);
                        EditText editTextNew = (EditText) view.findViewById(R.id.dialog_update_profile_new_password);
                        EditText editTextCnf = (EditText) view.findViewById(R.id.dialog_update_profile_cnf_password);
                        //ValidateData(editTextOld.getText().toString(),editTextNew.getText().toString(),editTextCnf.getText().toString());
                        if (TextUtils.isEmpty(editTextNew.getText().toString())) {
                            editTextNew.setError("Required");
                        } else if (TextUtils.isEmpty((editTextCnf.getText().toString()))) {
                            editTextCnf.setError("Required");
                        } else {
                            editTextNew.setError(null);
                            editTextCnf.setError(null);
                            if(!ValidateData(editTextNew.getText().toString(), editTextCnf.getText().toString())){
                                editTextCnf.setError("Password mismatch!");
                            }else {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                user.updatePassword(editTextNew.getText().toString())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(ViewProfile.this, "Password Updated Successfully", Toast.LENGTH_SHORT).show();
                                                    Log.d("Update Password", "User password updated.");
                                                }
                                            }
                                        });
                            }
                        }
                        progressDialog.hideProgressDialog();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    public boolean ValidateData(String newPassword, String cnfPassword) {
        if (!newPassword.equals(cnfPassword)) {
            return false;
        }
        return true;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.view_profile_button_submit:
                updateUserProfile();
                break;
            case R.id.view_profile_button_update_password:
                ShowMyCustomDialog();
                break;
            case R.id.view_profile_button_update_profile:
                changeFunctionalty();
                break;
            case R.id.view_profile_button_view_cancel_bookings:
                Intent intent = new Intent(ViewProfile.this, MyBookings.class);
                startActivity(intent);
                break;
            default:
                Toast.makeText(ViewProfile.this, "Unknown Error", Toast.LENGTH_SHORT).show();
        }
    }
}

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateProfile extends AppCompatActivity {

    public DatabaseReference mDatabaseReference;
    public FirebaseDatabase mFirebaseInstance;
    private TextView textViewEmail, textViewPassword;
    private EditText editTextName, editTextContact;
    private Button buttonUpdatePassword, buttonSubmit, buttonViewPassword;
    public MyCustomProgressDialog progressDialog;

    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        progressDialog = new MyCustomProgressDialog(this);

        textViewEmail = (TextView) findViewById(R.id.create_profile_textView_email);

        user = FirebaseAuth.getInstance().getCurrentUser();
        textViewEmail.setText("Email: " + user.getEmail());
        //textViewPassword = (TextView) findViewById(R.id.create_profile_textView_password);

        editTextName = (EditText) findViewById(R.id.create_profile_editText_name);
        editTextContact = (EditText) findViewById(R.id.create_profile_editText_contact_no);

        buttonSubmit = (Button) findViewById(R.id.create_profile_button_submit);
        buttonUpdatePassword = (Button) findViewById(R.id.create_profile_button_update_password);

        buttonUpdatePassword.setVisibility(View.GONE);
       // buttonViewPassword = (Button) findViewById(R.id.create_profile_button_view_password);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseInstance.getReference("User");

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean error = true;
                progressDialog.showProgressDialog("Loading...");
                if (!TextUtils.isEmpty(editTextName.getText().toString())) {

                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                            .setDisplayName(editTextName.getText().toString())
                            .build();

                    user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("Create Profile", "Profile Updated");
                            }
                        }
                    });
                    error = false;
                    mDatabaseReference.child(user.getUid()).child("user_name").setValue(editTextName.getText().toString());
                } else {
                    error = true;
                    editTextName.setError("Required");
                }
                if (!TextUtils.isEmpty(editTextContact.getText().toString())) {
                    error = false;
                    mDatabaseReference.child(user.getUid()).child("user_contact_no").setValue(editTextContact.getText().toString());
                } else {
                    error = true;
                    editTextContact.setError("Required");
                }
                progressDialog.hideProgressDialog();

                if (!error){
                    Toast.makeText(CreateProfile.this, "Profile created Successfull",Toast.LENGTH_SHORT).show();
                    Intent intentMainScreen = new Intent(CreateProfile.this, MainScreen.class);
                    intentMainScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intentMainScreen);
                }
            }
        });


        buttonUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMyCustomDialog();
            }
        });

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
}

package com.example.ridhs.bookmyhall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    public ProgressDialog mProgressDialog;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextCnfPassword;
    private Button buttonRegister;
    private MyCustomProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new MyCustomProgressDialog(this);

        editTextEmail = (EditText) findViewById(R.id.register_editText_email);
        editTextPassword = (EditText) findViewById(R.id.register_editText_password);
        editTextCnfPassword = (EditText) findViewById(R.id.register_editText_cnf_password);

        buttonRegister = (Button) findViewById(R.id.register_button_register);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });

        mAuth = FirebaseAuth.getInstance();

    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        progressDialog.showProgressDialog("Loading...");
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Toast.makeText(Register.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Register.this, "Registration Successfull",Toast.LENGTH_SHORT).show();
                            Intent intentCreateProfile = new Intent(Register.this, CreateProfile.class);
                            startActivity(intentCreateProfile);
                        }
                        progressDialog.hideProgressDialog();
                    }
                });
        //Intent intent = new Intent(Register.this, MainScreen.class);
        //startActivity(intent);
    }

    /*private void sendEmailVerification() {
        // Disable button
        findViewById(R.id.verify_email_button).setEnabled(false);

        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button
                        findViewById(R.id.verify_email_button).setEnabled(true);

                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(Register.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }*/

    private boolean validateForm() {
        boolean valid = true;

        String email = editTextEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Required.");
            valid = false;
        } else {
            editTextEmail.setError(null);
        }

        String password = editTextPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Required field");
            valid = false;
        } else {
            editTextPassword.setError(null);
        }

        String cnfpassword = editTextCnfPassword.getText().toString();
        if (TextUtils.isEmpty(cnfpassword)) {
            editTextCnfPassword.setError("Required field");
            valid = false;
        } else {
            editTextCnfPassword.setError(null);
        }

        if (!password.equals(cnfpassword)) {
            editTextCnfPassword.setError("Password must match");
            valid = false;
        } else {
            editTextCnfPassword.setError(null);
        }

        return valid;
    }

}

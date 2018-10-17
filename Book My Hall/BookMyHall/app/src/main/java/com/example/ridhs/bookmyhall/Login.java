package com.example.ridhs.bookmyhall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Login_Activity";

    private TextView textViewForgotPassword;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonRegister;
    private Button buttonGuest;
    private MyCustomProgressDialog progressDialog;
    private SharedPreferences preferences, userDetailsPref;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = getApplicationContext().getSharedPreferences(MyConstants.MY_PREFERENCE, MODE_PRIVATE);
        //userDetailsPref = getApplicationContext().getSharedPreferences(MyConstants.USER_PREFERENCES, MODE_PRIVATE);

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new MyCustomProgressDialog(this);

        editTextEmail = (EditText) findViewById(R.id.login_editText_email);
        editTextPassword = (EditText) findViewById(R.id.login_editText_password);

        textViewForgotPassword = (TextView) findViewById(R.id.login_textView_forgotPassword);

        buttonLogin = (Button) findViewById(R.id.login_button_login);
        buttonRegister = (Button) findViewById(R.id.login_button_register);
        buttonGuest = (Button) findViewById(R.id.login_button_guest);

        buttonLogin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
        buttonGuest.setOnClickListener(this);
        textViewForgotPassword.setOnClickListener(this);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intentMainScreen = new Intent(Login.this, MainScreen.class);
                    startActivity(intentMainScreen);
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    private void signIn(final String email, final String password) {
        Log.d(TAG, "signIn:" + email);

        progressDialog.showProgressDialog("Loading...");
        if (email.equals("admin")) {
            if (password.equals("admin")) {
                progressDialog.hideProgressDialog();

                preferences.edit().putString(MyConstants.USER_TYPE_KEY, MyConstants.USER_TYPE_ADMIN).apply();
  //              userDetailsPref.edit().putString(MyConstants.USER_EMAIL, email).apply();
  //              userDetailsPref.edit().putString(MyConstants.USER_PASSWORD, password).apply();

                Intent intent = new Intent(Login.this, MainScreen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } else {
                progressDialog.hideProgressDialog();
                Toast.makeText(Login.this, "Admin password is incorrect.", Toast.LENGTH_SHORT).show();
            }
        } else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                            progressDialog.hideProgressDialog();
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "signInWithEmail:failed", task.getException());
                                Toast.makeText(Login.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(Login.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                                preferences.edit().putString(MyConstants.USER_TYPE_KEY, MyConstants.USER_TYPE_RESERVER).apply();
//                                userDetailsPref.edit().putString(MyConstants.USER_EMAIL, email).apply();
//                                userDetailsPref.edit().putString(MyConstants.USER_PASSWORD, password).apply();

                                Intent intent = new Intent(Login.this, MainScreen.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
        }
    }

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
            editTextPassword.setError("Required.");
            valid = false;
        } else {
            editTextPassword.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button_login:
                if (validateForm()) {
                    signIn(editTextEmail.getText().toString(), editTextPassword.getText().toString());
                }
                break;
            case R.id.login_button_register:
                Intent iRegister = new Intent(Login.this, Register.class);
                startActivity(iRegister);
                break;
            case R.id.login_button_guest:
                preferences.edit().putString(MyConstants.USER_TYPE_KEY, MyConstants.USER_TYPE_VISITOR).apply();
                Intent iGuest = new Intent(Login.this, MainScreen.class);
                iGuest.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(iGuest);
                finish();
                break;
            case R.id.login_textView_forgotPassword:
                Intent forgotPassword = new Intent(Login.this, ForgotPassword.class);
                startActivity(forgotPassword);
                break;
            default:
                Toast.makeText(Login.this, "Invalid Option!", Toast.LENGTH_SHORT).show();
        }
    }
}


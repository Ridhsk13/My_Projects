package com.ridhs.myblackboard;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private FirebaseAuth mAuth;
    private EditText editTextEmail, editTextPwd;
    private Button buttonLogin;
    private Button buttonRegister;
    private TextView textViewForgotPwd;
    MyCustomProgressDialog customProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.login_et_email);
        editTextPwd = (EditText) findViewById(R.id.login_et_pwd);
        buttonLogin = (Button) findViewById(R.id.login_bt_login);
        buttonRegister = (Button) findViewById(R.id.login_bt_register);
        textViewForgotPwd = (TextView) findViewById(R.id.login_tv_forgot);

        buttonLogin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
        textViewForgotPwd.setOnClickListener(this);
        customProgressDialog = new MyCustomProgressDialog(this);
        //startActivity(new Intent(Login.this, MainActivity.class));
    }


    public void attemptLogin(String email, String password) {
        customProgressDialog.showProgressDialog("Loading....");
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the mUser. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in mUser can be handled in the listener.
                        customProgressDialog.hideProgressDialog();
                        if (!task.isSuccessful()) {
                            Log.w("Login_activity", "signInWithEmail:failed", task.getException());
                            Toast.makeText(Login.this, "Login Failed. Try Again.",
                                    Toast.LENGTH_SHORT).show();
                        } else if (task.isSuccessful()) {
                            Intent intentMain = new Intent(Login.this, MainActivity.class);
                            intentMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intentMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intentMain);
                        } else {
                            Toast.makeText(Login.this, "Unknown Error, Try again.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_bt_login:
                attemptLogin(editTextEmail.getText().toString(), editTextPwd.getText().toString());
                break;
            case R.id.login_bt_register:
                Intent intentReg = new Intent(Login.this, Register.class);
                startActivity(intentReg);
                break;
            case R.id.login_tv_forgot:
                //nothing
            default:
                Toast.makeText(Login.this, "Unknown Error, Try again.",
                        Toast.LENGTH_SHORT).show();
        }
    }
}

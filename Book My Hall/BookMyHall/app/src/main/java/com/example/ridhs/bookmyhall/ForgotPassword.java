package com.example.ridhs.bookmyhall;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;

public class ForgotPassword extends AppCompatActivity {

    Button buttonSubmit;
    FirebaseAuth firebaseAuth;
    EditText editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editTextEmail = (EditText) findViewById(R.id.forgot_password_editText_email);
        buttonSubmit = (Button) findViewById(R.id.forgot_password_button_submit);

        firebaseAuth = FirebaseAuth.getInstance();

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.fetchProvidersForEmail(editTextEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                        if (task.isSuccessful()){
                            //Log.d("Result value", String.valueOf(task.getResult().getProviders()));
                            if (task.getResult().getProviders().isEmpty()){
                                Log.d("R", "empty array");

                                firebaseAuth.sendPasswordResetEmail(editTextEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(ForgotPassword.this, "Password has been sent to your registered email id", Toast.LENGTH_SHORT).show();
                                            Intent iLogin = new Intent(ForgotPassword.this, Login.class);
                                            iLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(iLogin);
                                        }else {
                                            Toast.makeText(ForgotPassword.this, "Unkown Error. Try again!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Log.d("R", "non-empty array");
                                Toast.makeText(ForgotPassword.this, "Given Email is not registered", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
            }
        });



    }
}

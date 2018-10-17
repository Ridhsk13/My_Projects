package com.ridhs.myblackboard;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    EditText editTextEmail, editTextName, editTextPwd, editTextCnfPwd, editTextContact,
            editTextAddLine1, editTextAddline2, editTextAddCity, editTextAddState, editTextPincode;
    Button buttonReg, buttonContinue;
    RadioGroup radioGroupUserType;
    RadioButton radioButtonStu, radioButtonInst;
    FirebaseDatabase mFirebaseDatabase;
    FirebaseUser mUser;
    LinearLayout linearLayoutMsg, linearLayoutForm;
    MyCustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        //linearLayoutMsg = (LinearLayout) findViewById(R.id.register_ll_msg);
        linearLayoutForm = (LinearLayout) findViewById(R.id.register_ll_form);

        editTextName = (EditText) findViewById(R.id.register_et_name);
        editTextEmail = (EditText) findViewById(R.id.register_et_email);
        editTextPwd = (EditText) findViewById(R.id.register_et_pwd);
        editTextCnfPwd = (EditText) findViewById(R.id.register_et_cnfpwd);
        editTextContact = (EditText) findViewById(R.id.register_et_contact);
        editTextAddLine1 = (EditText) findViewById(R.id.register_et_add_line_1);
        editTextAddline2 = (EditText) findViewById(R.id.register_et_add_line_2);
        editTextAddCity = (EditText) findViewById(R.id.register_et_add_city);
        editTextAddState = (EditText) findViewById(R.id.register_et_add_state);
        editTextPincode = (EditText) findViewById(R.id.register_et_add_pincode);

        radioGroupUserType = (RadioGroup) findViewById(R.id.register_rg_user_type);

        buttonReg = (Button) findViewById(R.id.register_bt_register);
        //buttonContinue = (Button) findViewById(R.id.register_bt_continue);

        //linearLayoutMsg.setVisibility(View.INVISIBLE);
        customProgressDialog = new MyCustomProgressDialog(this);

        buttonReg.setOnClickListener(this);
        //buttonContinue.setOnClickListener(this);


    }

    public void attemptRegister(final String email, final String password) {
        customProgressDialog.showProgressDialog("Loading....");
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the mUser. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in mUser can be handled in the listener.
                        customProgressDialog.hideProgressDialog();
                        if (!task.isSuccessful()) {
                            Toast.makeText(Register.this, "Registration Fail. Try Again",
                                    Toast.LENGTH_SHORT).show();
                        } else if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "Registration Successfull",
                                    Toast.LENGTH_SHORT).show();

                            if (registerUserInfo()){
                                Intent intentMain = new Intent(Register.this, MainActivity.class);
                                intentMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intentMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intentMain);
                            } else {
                                Toast.makeText(Register.this, "User data not updated due to unknown error",
                                        Toast.LENGTH_SHORT).show();
                            }


                           /* mUser = FirebaseAuth.getInstance().getCurrentUser();
                            mUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    linearLayoutForm.setVisibility(View.INVISIBLE);
                                    linearLayoutMsg.setVisibility(View.VISIBLE);
                                }
                            });*/


                        } else {
                            Toast.makeText(Register.this, "Unknown Error. Try again.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }

    public boolean isFormValid() {

        String email = editTextEmail.getText().toString();
        String pwd = editTextPwd.getText().toString();
        String cnfPwd = editTextCnfPwd.getText().toString();
        String name = editTextName.getText().toString();
        String contact_no = editTextContact.getText().toString();

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Required");
            return false;
        } else if (TextUtils.isEmpty(name)) {
            editTextName.setError("Required");
            return false;
        } else if (TextUtils.isEmpty(pwd)) {
            editTextPwd.setError("Required");
            return false;
        } else if (TextUtils.isEmpty(cnfPwd)) {
            editTextCnfPwd.setError("Required");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Invalid Email");
            return false;
        } else if (!pwd.equals(cnfPwd)) {
            editTextCnfPwd.setError("Password does not match");
            return false;
        } else if (TextUtils.isEmpty(contact_no)) {
            editTextContact.setError("Required");
            return false;
        } else {
            editTextCnfPwd.setError(null);
            editTextEmail.setError(null);
            editTextPwd.setError(null);
            return true;
        }
    }
    boolean isUserInfoRegistered = true;
    public boolean registerUserInfo(){

        DatabaseReference myRef = mFirebaseDatabase.getReference("Users");

        int userTypeId = radioGroupUserType.getCheckedRadioButtonId();
        //String userType = null;
        User.UserAddress userAddress = new User.UserAddress();
        userAddress.setAddress_line_1(editTextAddLine1.getText().toString());
        userAddress.setAddress_line_2(editTextAddline2.getText().toString());
        userAddress.setCity(editTextAddCity.getText().toString());
        userAddress.setPincode(editTextPincode.getText().toString());
        userAddress.setState(editTextAddState.getText().toString());

       /* if (userTypeId == R.id.register_rb_user_type_stu){
            userType = "stu" ;
        } else if (userTypeId == R.id.register_rb_user_type_inst){
            userType = "inst" ;;
        } else {
            isUserInfoRegistered[0] = false;
        }*/

        User user = new User();

        user.setUser_name(editTextName.getText().toString());
        user.setUser_contact_no(editTextContact.getText().toString());
        user.setUser_email(editTextEmail.getText().toString());
        if (userTypeId == R.id.register_rb_user_type_stu){
            user.setUser_type("stu");
        } else if (userTypeId == R.id.register_rb_user_type_inst){
            user.setUser_type("inst");
        } else {
            isUserInfoRegistered = false;
        }
        user.setUser_address(userAddress);
        Log.d("User Id : " , mAuth.getCurrentUser().getUid());
        myRef.child(mAuth.getCurrentUser().getUid()).setValue(user);
        Log.d("boolean value", String.valueOf(isUserInfoRegistered));
        return isUserInfoRegistered;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_bt_register:
                if (isFormValid()) {
                    attemptRegister(editTextEmail.getText().toString(), editTextPwd.getText().toString());
                }
                break;
            /*case R.id.register_bt_continue:
                //String userid = mUser.getEmail();


                mAuth.signOut();
                mAuth.signInWithEmailAndPassword(editTextEmail.getText().toString(), editTextPwd.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Register_email : ", String.valueOf(mUser.isEmailVerified()));
                        if (!mUser.isEmailVerified()){
                            Toast.makeText(Register.this, "Email is not verified yet!",Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intentMain = new Intent(Register.this, MainActivity.class);
                            startActivity(intentMain);
                        }
                    }
                });

                break;*/
            default:
                Toast.makeText(Register.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }
}

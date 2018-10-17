package com.example.ridhs.bookmyhall;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class HallDescription extends AppCompatActivity implements View.OnClickListener {

    private Button buttonBookHall, buttonAddReview;
    private ImageView imageViewMain, imageViewEx1, imageViewEx2, imageViewEx3, imageViewEx4;
    private TextView textViewDesc, textViewFloorArea, textViewCapacity, textViewAddress, textViewReview;
    public HallDetails hallDetail;
    public String hallId;
    private DatabaseReference referenceReview, referenceUser, referenceHall;
    private FirebaseUser user;
    private SharedPreferences preferences;
    private Button buttonEditHall, buttonDeleteHall, buttonSubmit, buttonEditReview;
    private EditText editTextDesc, editTextFloorArea, editTextCapacity, editTextAddress;
    private LinearLayout linearLayoutFeatures, linearLayoutAdminFeatures;


    private LinearLayout linearLayoutEditFeatures, linearLayoutEditFeaturesSub1, linearLayoutEditFeaturesSub2, linearLayoutEditFeaturesSub3;
    private ImageView imageViewEditFeatures;
    private EditText editTextFeatureKey1, editTextFeatureKey2, editTextFeatureKey3;
    private EditText editTextFeatureValue1, editTextFeatureValue2, editTextFeatureValue3;
    private ImageView imageviewFeatureDelete1, imageviewFeatureDelete2, imageviewFeatureDelete3;
    private Button buttonAddExtraFeature, buttonAddExtraFeatureSubmit;
    private LinearLayout linearLayoutExtraFeatureDisplay;


    private TextView textViewDummyReview, textViewDummyReview2, textViewDummyReview3;
    private ImageView imageViewEditReview, imageViewDeleteReview, imageViewDeleteReview2, imageViewDeleteReview3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences(MyConstants.MY_PREFERENCE, MODE_PRIVATE);

        if (preferences.getString(MyConstants.USER_TYPE_KEY, null).equals(MyConstants.USER_TYPE_ADMIN)) {
            setContentView(R.layout.admin_activity_hall_description);
        } else {
            setContentView(R.layout.activity_hall_description);
        }

        user = FirebaseAuth.getInstance().getCurrentUser();

        hallId = getIntent().getStringExtra("hall_id");
        hallDetail = (HallDetails) getIntent().getSerializableExtra("hall_detail");

        referenceReview = FirebaseDatabase.getInstance().getReference("Reviews");
        referenceUser = FirebaseDatabase.getInstance().getReference("User/" + user.getUid());
        referenceHall = FirebaseDatabase.getInstance().getReference("Hall/" + hallId);

        getSupportActionBar().setTitle(hallDetail.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeVariables();
        setData(this);
    }

    public void initializeVariables() {
        imageViewMain = (ImageView) findViewById(R.id.hall_desc_imageView_main);
        imageViewEx1 = (ImageView) findViewById(R.id.hall_desc_imageView_ex1);
        imageViewEx2 = (ImageView) findViewById(R.id.hall_desc_imageView_ex2);
        imageViewEx3 = (ImageView) findViewById(R.id.hall_desc_imageView_ex3);
        imageViewEx4 = (ImageView) findViewById(R.id.hall_desc_imageView_ex4);

        textViewDesc = (TextView) findViewById(R.id.hall_desc_textView_desc);
        textViewFloorArea = (TextView) findViewById(R.id.hall_desc_textView_floor_area);
        textViewCapacity = (TextView) findViewById(R.id.hall_desc_textView_capacity);
        textViewAddress = (TextView) findViewById(R.id.hall_desc_textView_address);
        textViewReview = (TextView) findViewById(R.id.hall_desc_textView_review_0);

        buttonBookHall = (Button) findViewById(R.id.hall_desc_button_book_hall);
        buttonAddReview = (Button) findViewById(R.id.hall_desc_button_write_review);
        buttonEditReview = (Button) findViewById(R.id.hall_desc_button_edit_review);

        if (buttonEditReview != null) {
            buttonEditReview.setVisibility(View.GONE);
        }

        if (preferences.getString(MyConstants.USER_TYPE_KEY, null).equals(MyConstants.USER_TYPE_VISITOR)) {
            buttonBookHall.setVisibility(View.GONE);
            buttonAddReview.setVisibility(View.GONE);
            buttonEditReview.setVisibility(View.GONE);
        }
        if (preferences.getString(MyConstants.USER_TYPE_KEY, null).equals(MyConstants.USER_TYPE_ADMIN)) {
            editTextAddress = (EditText) findViewById(R.id.hall_desc_editText_admin_address);
            editTextCapacity = (EditText) findViewById(R.id.hall_desc_editText_admin_capacity);
            editTextDesc = (EditText) findViewById(R.id.hall_desc_editText_admin_hall_description);
            editTextFloorArea = (EditText) findViewById(R.id.hall_desc_editText_admin_floor_area);

            buttonEditHall = (Button) findViewById(R.id.hall_desc_button_admin_edit_hall);
            buttonDeleteHall = (Button) findViewById(R.id.hall_desc_button_admin_delete_hall);
            buttonSubmit = (Button) findViewById(R.id.hall_desc_button_admin_submit);

            linearLayoutFeatures = (LinearLayout) findViewById(R.id.hall_desc_linearLayout_features);
            linearLayoutAdminFeatures = (LinearLayout) findViewById(R.id.hall_desc_linearLayout_admin_features);

            imageViewEditFeatures = (ImageView) findViewById(R.id.hall_desc_imageView_admin_edit_features);

            linearLayoutEditFeatures = (LinearLayout) findViewById(R.id.hall_desc_linearLayout_admin_edit_features);
            linearLayoutEditFeaturesSub1 = (LinearLayout) findViewById(R.id.hall_desc_linearLayout_admin_edit_features_sub_1);
            linearLayoutEditFeaturesSub2 = (LinearLayout) findViewById(R.id.hall_desc_linearLayout_admin_edit_features_sub_2);
            linearLayoutEditFeaturesSub3 = (LinearLayout) findViewById(R.id.hall_desc_linearLayout_admin_edit_features_sub_3);
            linearLayoutExtraFeatureDisplay = (LinearLayout) findViewById(R.id.hall_desc_linearLayout_admin_extra_feature_display);

            editTextFeatureKey1 = (EditText) findViewById(R.id.hall_desc_editText_admin_feature_key_1);
            editTextFeatureKey2 = (EditText) findViewById(R.id.hall_desc_editText_admin_feature_key_2);
            editTextFeatureKey3 = (EditText) findViewById(R.id.hall_desc_editText_admin_feature_key_3);

            editTextFeatureValue1 = (EditText) findViewById(R.id.hall_desc_editText_admin_feature_value_1);
            editTextFeatureValue2 = (EditText) findViewById(R.id.hall_desc_editText_admin_feature_value_2);
            editTextFeatureValue3 = (EditText) findViewById(R.id.hall_desc_editText_admin_feature_value_3);

            imageviewFeatureDelete1 = (ImageView) findViewById(R.id.hall_desc_imageView_admin_delete_feature_1);
            imageviewFeatureDelete2 = (ImageView) findViewById(R.id.hall_desc_imageView_admin_delete_feature_2);
            imageviewFeatureDelete3 = (ImageView) findViewById(R.id.hall_desc_imageView_admin_delete_feature_3);

            buttonAddExtraFeature = (Button) findViewById(R.id.hall_desc_admin_add_hall_feature);
            buttonAddExtraFeatureSubmit = (Button) findViewById(R.id.hall_desc_admin_add_hall_submit);

            imageViewEditFeatures.setOnClickListener(this);
            imageviewFeatureDelete1.setOnClickListener(this);
            imageviewFeatureDelete2.setOnClickListener(this);
            imageviewFeatureDelete3.setOnClickListener(this);
            buttonAddExtraFeature.setOnClickListener(this);
            buttonAddExtraFeatureSubmit.setOnClickListener(this);

            textViewDummyReview = (TextView) findViewById(R.id.hall_desc_textView_admin_review_1);
            textViewDummyReview2 = (TextView) findViewById(R.id.hall_desc_textView_admin_review_2);
            textViewDummyReview3 = (TextView) findViewById(R.id.hall_desc_textView_admin_review_3);


            imageViewDeleteReview = (ImageView) findViewById(R.id.hall_desc_imageView_admin_delete_review_1);
            imageViewDeleteReview2 = (ImageView) findViewById(R.id.hall_desc_imageView_admin_delete_review_2);
            imageViewDeleteReview3 = (ImageView) findViewById(R.id.hall_desc_imageView_admin_delete_review_3);

            imageViewEditReview = (ImageView) findViewById(R.id.hall_desc_imageView_admin_edit_reviews);

            imageViewEditReview.setOnClickListener(this);
            imageViewDeleteReview.setOnClickListener(this);
            imageViewDeleteReview2.setOnClickListener(this);
            imageViewDeleteReview3.setOnClickListener(this);
        }

        if (buttonAddReview != null) {
            buttonBookHall.setOnClickListener(this);
        }
        if (buttonBookHall != null) {
            buttonAddReview.setOnClickListener(this);
        }
        if (buttonSubmit != null) {
            buttonSubmit.setOnClickListener(this);
        }
        if (buttonEditHall != null) {
            buttonEditHall.setOnClickListener(this);
        }
        if (buttonDeleteHall != null) {
            buttonDeleteHall.setOnClickListener(this);
        }
        if (buttonEditReview != null) {
            buttonEditReview.setOnClickListener(this);
        }
    }

    public void setImageview(Activity context, String url, ImageView imageView) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(url, opts);
        opts.inJustDecodeBounds = false;

        //String url = hallDetail.getImages().get(0);
        Log.d("URL : ", url);
        Picasso.with(context)
                .load(url)
                .fit()
                .centerCrop()
                .error(R.drawable.placeholder)
                .into(imageView);
    }

    public void setData(Activity context) {

        if (hallDetail.getImages() != null) {

            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(hallDetail.getImages().get(0), opts);
            opts.inJustDecodeBounds = false;

            Picasso.with(context)
                    .load(hallDetail.getImages().get(0))
                    .transform(ImageTransformation.getTransformation(imageViewMain))
                    .error(R.drawable.placeholder)
                    .into(imageViewMain);
            for (int i = 1; i < hallDetail.getImages().size(); i++) {
                ImageView tempImageView = null;
                if (i == 1) {
                    tempImageView = imageViewEx1;
                }
                if (i == 2) {
                    tempImageView = imageViewEx2;
                }
                if (i == 3) {
                    tempImageView = imageViewEx3;
                }
                if (i == 4) {
                    tempImageView = imageViewEx4;
                }
                setImageview(context, hallDetail.getImages().get(i), tempImageView);

            }
        }

        /*if (hallDetail.getImages().get(0) != null) {
            setImageview(context, hallDetail.getImages().get(0), imageViewMain);
        }
        if (hallDetail.getImages().get(0) != null) {
            setImageview(context, hallDetail.getImages().get(0), imageViewMain);
        }
        if (hallDetail.getImages().get(1) != null) {
            setImageview(context, hallDetail.getImages().get(1), imageViewEx1);
        }
        if (hallDetail.getImages().get(2) != null) {
            setImageview(context, hallDetail.getImages().get(2), imageViewEx2);
        }
        if (hallDetail.getImages().get(3) != null) {
            setImageview(context, hallDetail.getImages().get(3), imageViewEx3);
        }
        if (hallDetail.getImages().get(4) != null) {
            setImageview(context, hallDetail.getImages().get(4), imageViewEx4);
        }
*/
        if (hallDetail.getHallDesc() != null) {
            textViewDesc.setText(hallDetail.getHallDesc());
        } else {
            textViewDesc.setText("Information not available");
        }
        if (hallDetail.getAddress() != null) {
            textViewAddress.setText(hallDetail.getAddress());
        } else {
            textViewAddress.setText("Information not available");
        }
        if (hallDetail.getCapacity() != null) {
            textViewCapacity.setText(String.valueOf(hallDetail.getCapacity()));
        } else {
            textViewCapacity.setText("Information not available");
        }
        if (hallDetail.getFloorArea() != null) {
            textViewFloorArea.setText(String.valueOf(hallDetail.getFloorArea()));
        } else {
            textViewFloorArea.setText("Information not available");
        }
        if (hallDetail.getReviews() != null) {
            int count = hallDetail.getReviews().keySet().size();
            Random random = new Random();
            int r = random.nextInt(count);
            int i = 0;
            for (Map.Entry<String, String> entry : hallDetail.getReviews().entrySet()) {
                if (i == r) {
                    textViewReview.setText("\" " + entry.getValue() + " \"");
                    break;
                }
                i++;
            }
        } else {
            textViewReview.setText("Information not available");
        }
        referenceUser.child("reviews").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if (child.getValue().equals(hallId)) {
                        if (buttonAddReview != null) {
                            buttonAddReview.setEnabled(false);
                            buttonEditReview.setVisibility(View.VISIBLE);
                        }
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void showReviewDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(HallDescription.this);
        // Get the layout inflater
        LayoutInflater inflater = HallDescription.this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View view = inflater.inflate(R.layout.activity_hall_description_add_review, null);
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Add Review", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        DatabaseReference tempReference = referenceUser.child("reviews").push();
                        String key = tempReference.getKey();
                        tempReference.setValue(hallId);
                        EditText editText = (EditText) view.findViewById(R.id.add_review_dialog_editText_review_msg);

                        referenceHall.child("reviews").child(key).setValue(editText.getText().toString());

                        Review review = new Review();
                        review.setHall_id(hallId);
                        review.setUser_id(user.getUid());
                        review.setReview_id(key);
                        review.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                        review.setReview_msg(editText.getText().toString());

                        referenceReview.child(key).setValue(review).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(HallDescription.this, "Review Successfully Submitted", Toast.LENGTH_SHORT).show();
                                    buttonAddReview.setEnabled(false);
                                } else {
                                    Toast.makeText(HallDescription.this, "Something went wrong. Try Again", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

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

    public void showEditReviewDialog() {




        final String[] userReviewId = {null};
        final AlertDialog.Builder builder = new AlertDialog.Builder(HallDescription.this);
        LayoutInflater inflater = HallDescription.this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.activity_hall_description_add_review, null);
        final EditText editText = (EditText) view.findViewById(R.id.add_review_dialog_editText_review_msg);

        editText.setText("Big Hall");
        /*DatabaseReference tempReference = referenceUser.child("reviews");
        tempReference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if (Objects.equals(child.getValue(), hallId)) {

                        userReviewId[0] = child.getKey();
                        Log.d("Review ID ", child.getKey());
                        Log.d("Review ID ", String.valueOf(child.getValue()));
                    }
                    //Log.d("Review ID ",child.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        referenceHall.child("reviews").child(userReviewId[0]).child("review_msg").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                editText.setText((CharSequence) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Add Review", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        /*Review review = new Review();
                        review.setHall_id(hallId);
                        review.setUser_id(user.getUid());
                        review.setReview_id(key);
                        review.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                        review.setReview_msg(editText.getText().toString());
*/
                        Toast.makeText(HallDescription.this, "Review Edited Successfully", Toast.LENGTH_SHORT).show();
                        /*referenceReview.child(userReviewId[0]).child("review_msg").setValue(editText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(HallDescription.this, "Review Edited Successfully", Toast.LENGTH_SHORT).show();
                                    buttonAddReview.setEnabled(false);
                                } else {
                                    Toast.makeText(HallDescription.this, "Something went wrong. Try Again", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });*/
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(HallDescription.this, "Review deleted Successfully", Toast.LENGTH_SHORT).show();
                        buttonAddReview.setEnabled(true);
                        buttonEditReview.setVisibility(View.GONE);
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void viewAdminFunctionality() {

        textViewDesc.setVisibility(View.GONE);
        textViewAddress.setVisibility(View.GONE);

        linearLayoutFeatures.setVisibility(View.GONE);
        linearLayoutAdminFeatures.setVisibility(View.VISIBLE);

        editTextCapacity.setVisibility(View.VISIBLE);
        editTextFloorArea.setVisibility(View.VISIBLE);
        editTextAddress.setVisibility(View.VISIBLE);
        editTextDesc.setVisibility(View.VISIBLE);

        editTextDesc.setText(hallDetail.getHallDesc());
        editTextAddress.setText(hallDetail.getAddress());
        editTextFloorArea.setText(hallDetail.getFloorArea());
        editTextCapacity.setText(hallDetail.getCapacity().toString());

        buttonSubmit.setVisibility(View.VISIBLE);
        buttonDeleteHall.setVisibility(View.GONE);
        buttonEditHall.setVisibility(View.GONE);

    }

    public void updateHallDetails() {
        MyCustomProgressDialog progressDialog = new MyCustomProgressDialog(this);
        progressDialog.showProgressDialog("Updating...");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Hall");
        DatabaseReference tempReference = databaseReference.child(hallId);

        final boolean[] isSuccessfull = {true};
        OnCompleteListener listener = new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

                if (!task.isSuccessful()) {
                    isSuccessfull[0] = false;
                } else {
                    isSuccessfull[0] = true;
                }
            }
        };
        tempReference.child("hallDesc").setValue(editTextDesc.getText().toString()).addOnCompleteListener(listener);
        tempReference.child("capacity").setValue(Long.valueOf(editTextCapacity.getText().toString())).addOnCompleteListener(listener);
        tempReference.child("floorArea").setValue(editTextFloorArea.getText().toString()).addOnCompleteListener(listener);
        tempReference.child("address").setValue(editTextAddress.getText().toString()).addOnCompleteListener(listener);

        if (isSuccessfull[0]) {
            progressDialog.hideProgressDialog();
            Toast.makeText(HallDescription.this, "Hall details updated Successfully", Toast.LENGTH_SHORT).show();
            onBackPressed();
            //finish();
        } else {
            progressDialog.hideProgressDialog();
            Toast.makeText(HallDescription.this, "Hall details update failed", Toast.LENGTH_SHORT).show();
        }

    }

    public void showHallDeleteDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Delete");
        builder.setMessage("Are you sure you want to delete " + hallDetail.getName());
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Hall");
                databaseReference.child(hallId).removeValue();
                buttonDeleteHall.setEnabled(false);
                buttonEditHall.setEnabled(false);
                Toast.makeText(HallDescription.this, "Hall Successfully deleted", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hall_desc_button_book_hall:
                Intent intentCnfBooking = new Intent(HallDescription.this, BookHall.class);
                intentCnfBooking.putExtra("hall_id", hallId);
                intentCnfBooking.putExtra("hall_name", hallDetail.getName());
                startActivity(intentCnfBooking);
                break;
            case R.id.hall_desc_button_write_review:
                showReviewDialog();
                break;
            case R.id.hall_desc_button_admin_edit_hall:
                viewAdminFunctionality();
                break;
            case R.id.hall_desc_button_admin_submit:
                updateHallDetails();
                break;
            case R.id.hall_desc_button_admin_delete_hall:
                //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Hall");
                //databaseReference.child(hallId).removeValue();
                showHallDeleteDialog();
                break;
            case R.id.hall_desc_button_edit_review:
                showEditReviewDialog();
                break;
            case R.id.hall_desc_imageView_admin_edit_features:
                linearLayoutFeatures.setVisibility(View.GONE);
                linearLayoutAdminFeatures.setVisibility(View.VISIBLE);
                linearLayoutEditFeatures.setVisibility(View.VISIBLE);
                buttonAddExtraFeatureSubmit.setVisibility(View.VISIBLE);
                buttonAddExtraFeature.setVisibility(View.VISIBLE);
                editTextFloorArea.setText("80' x 133'");
                editTextCapacity.setText("576");

                break;
            case R.id.hall_desc_imageView_admin_delete_feature_1:
                linearLayoutEditFeaturesSub1.setVisibility(View.GONE);
                break;
            case R.id.hall_desc_imageView_admin_delete_feature_2:
                linearLayoutEditFeaturesSub2.setVisibility(View.GONE);
                break;
            case R.id.hall_desc_imageView_admin_delete_feature_3:
                linearLayoutEditFeaturesSub3.setVisibility(View.GONE);
                break;
            case R.id.hall_desc_admin_add_hall_feature:
                if (linearLayoutEditFeaturesSub1.getVisibility() != View.VISIBLE) {
                    linearLayoutEditFeaturesSub1.setVisibility(View.VISIBLE);
                } else if (linearLayoutEditFeaturesSub2.getVisibility() != View.VISIBLE) {
                    linearLayoutEditFeaturesSub2.setVisibility(View.VISIBLE);
                } else if (linearLayoutEditFeaturesSub2.getVisibility() != View.VISIBLE) {
                    linearLayoutEditFeaturesSub2.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.hall_desc_admin_add_hall_submit:
                if (linearLayoutEditFeaturesSub2.getVisibility() == View.VISIBLE) {
                    linearLayoutEditFeaturesSub2.setVisibility(View.GONE);
                }
                if (linearLayoutEditFeaturesSub2.getVisibility() == View.VISIBLE) {
                    linearLayoutEditFeaturesSub2.setVisibility(View.GONE);
                }
                linearLayoutFeatures.setVisibility(View.VISIBLE);
                linearLayoutAdminFeatures.setVisibility(View.GONE);
                linearLayoutEditFeatures.setVisibility(View.GONE);
                buttonAddExtraFeatureSubmit.setVisibility(View.GONE);
                buttonAddExtraFeature.setVisibility(View.GONE);
                linearLayoutExtraFeatureDisplay.setVisibility(View.VISIBLE);
                break;

            case R.id.hall_desc_imageView_admin_delete_review_1:
                textViewDummyReview.setVisibility(View.GONE);
                imageViewDeleteReview.setVisibility(View.GONE);
                //textViewReview.setVisibility(View.VISIBLE);
                break;

            case R.id.hall_desc_imageView_admin_delete_review_2:
                textViewDummyReview2.setVisibility(View.GONE);
                imageViewDeleteReview2.setVisibility(View.GONE);
                //textViewReview.setVisibility(View.VISIBLE);
                break;

            case R.id.hall_desc_imageView_admin_delete_review_3:
                textViewDummyReview3.setVisibility(View.GONE);
                imageViewDeleteReview3.setVisibility(View.GONE);
                //textViewReview.setVisibility(View.VISIBLE);
                break;

            case R.id.hall_desc_imageView_admin_edit_reviews:
                imageViewDeleteReview.setVisibility(View.VISIBLE);
                imageViewDeleteReview2.setVisibility(View.VISIBLE);
                imageViewDeleteReview3.setVisibility(View.VISIBLE);
                break;

            default:
                Toast.makeText(HallDescription.this, "Unknown Error!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

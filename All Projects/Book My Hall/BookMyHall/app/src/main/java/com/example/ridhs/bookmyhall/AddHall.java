package com.example.ridhs.bookmyhall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AddHall extends AppCompatActivity {
    private Button mWriteData;
    private Button add_feature;
    private DatabaseReference mRootRef;
    // Create a storage reference from our app
    private StorageReference mStorage;
    private static int RESULT_LOAD_IMG = 1;
    private Uri selectedImage;

    private EditText hallNameID;
    private EditText hallDescID;
    private EditText addressID;
    private EditText capacityID;
    private EditText floorAreaID;

    private String hallName;
    private String hallDesc;
    private String address;
    private String capacity;
    private String floorArea;
    private String imgDecodableString;
    private String imageLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hall);

        getSupportActionBar().setTitle("Add Hall");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        add_feature = (Button) findViewById(R.id.add_hall_feature);
        add_feature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        //to browse images
        //  Button buttonLoadImage = (Button)findViewById(R.id.buttonLoadPicture);
        hallNameID = (EditText) findViewById(R.id.hall_name_input);
        hallDescID = (EditText) findViewById(R.id.hall_desc_input);
        addressID = (EditText) findViewById(R.id.address_input);
        capacityID = (EditText) findViewById(R.id.capacity_input);
        floorAreaID = (EditText) findViewById(R.id.floor_area_input);

        //to write data to firebase
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();//returns the root     directory of storage
        mWriteData = (Button) findViewById(R.id.add_hall);
        mWriteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hallName = hallNameID.getText().toString();
                hallDesc = hallDescID.getText().toString();
                capacity = capacityID.getText().toString();
                address = addressID.getText().toString();
                floorArea = floorAreaID.getText().toString();
                StorageReference imagePath =
                        mStorage.child(hallName).child(hallName + "_image");

                imagePath.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot
                                                  taskSnapshot) {

                    }
                });
                // Firebase usersRef = ref.child("Users");
                imageLink =
                        "gs://bookmyhall-2f74a.appspot.com/" + hallName + "/" + hallName + "_image";
                Map<String, String> userData = new HashMap<String, String>();
                userData.put("hallDesc", hallDesc);
                userData.put("name", hallName);
                userData.put("capacity", capacity);
                userData.put("address", address);
                userData.put("floorArea", floorArea);
                userData.put("image", imageLink);
                mRootRef.child("Halls/").push().setValue(userData);

                finish();
            }
        });
    }

    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.imgView);
                // Set the Image in ImageView after decoding the String

                //  imgView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                //File f = new File(imgDecodableString);
                //Picasso.with(AddHall.this).load(f).into(imgView);
                imgView.setVisibility(View.VISIBLE);
                Toast.makeText(this, selectedImage.getLastPathSegment(),
                        Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

}

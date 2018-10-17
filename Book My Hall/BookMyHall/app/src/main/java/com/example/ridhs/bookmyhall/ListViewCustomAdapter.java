package com.example.ridhs.bookmyhall;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by RIDHS on 3/26/2017.
 */

public class ListViewCustomAdapter extends BaseAdapter {

    String[] hallName;
    String[] hallDescription;
    ArrayList<HallDetails> hallDetailsList;
    ArrayList<HallDetails> hallDetailsListTemp = new ArrayList<>();
    ArrayList<String> hallId;
    Activity context;
    String userType;

    public ListViewCustomAdapter(Activity context, ArrayList<HallDetails> hallDetailsList, String user_type, ArrayList<String> hallId) {
        this.hallName = hallName;
        this.hallDescription = hallDescription;
        this.context = context;
        this.hallDetailsList = hallDetailsList;
        this.userType = user_type;
        this.hallId = hallId;
        this.hallDetailsListTemp.addAll(hallDetailsList);
    }

    @Override
    public int getCount() {
        return hallDetailsList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView;
        ImageView imageViewDelete = null;

        if (convertView == null) {

            new View(context);
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (userType.equals(MyConstants.USER_TYPE_ADMIN)) {
                rowView = inflater.inflate(R.layout.admin_activity_main_screen_listview_row, parent, false);
                imageViewDelete = (ImageView) rowView.findViewById(R.id.listview_row_image_delete);
            } else {
                rowView = inflater.inflate(R.layout.reserver_activity_main_screen_listview_row, parent, false);
            }
        } else {
            rowView = convertView;
        }
        //if (position == 4) {




            //LayoutInflater inflater = context.getLayoutInflater();
            //rowView = inflater.inflate(R.layout.reserver_activity_main_screen_listview_row, parent,false);

            //View rowView;
            //final String[] extra = new String[1];


            ImageView imageView = (ImageView) rowView.findViewById(R.id.listview_row_image);
            TextView textViewHallName = (TextView) rowView.findViewById(R.id.listview_row_title);
            TextView textViewHallDesc = (TextView) rowView.findViewById(R.id.listview_row_description);
            LinearLayout linearLayout = (LinearLayout) rowView.findViewById(R.id.listview_row_linearLayout);

            if (imageViewDelete != null) {
                imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Onclick", "Working");
                    /*DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Hall");
                    databaseReference.child(hallId.get(position)).removeValue();
                    context.recreate();*/
                        showReviewDialog(position);
                    }
                });
            }
            if (linearLayout != null) {
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentHallDetails = new Intent(context, HallDescription.class);
                        intentHallDetails.putExtra("hall_id", hallId.get(position));
                        intentHallDetails.putExtra("hall_detail", hallDetailsList.get(position));
                        context.startActivity(intentHallDetails);
                    }
                });
            }
            String url = hallDetailsList.get(position).getImages().get(0);

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

            textViewHallName.setText(hallDetailsList.get(position).getName());

            //Log.d("Desc : " ,hallDetailsList.get(position).getHallDesc());
            textViewHallDesc.setText(hallDetailsList.get(position).getHallDesc());
            //textViewHallName.setText(hallName[position]);
            //textViewHallDesc.setText(hallDesc[position]);

        //}
        return rowView;
    }

    public void showReviewDialog(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirm Delete");
        builder.setMessage("Are you sure you want to delete " + hallDetailsList.get(position).getName());
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Hall");
                databaseReference.child(hallId.get(position)).removeValue();
                context.recreate();
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
    public void notifyDataSetChanged() {
       // context.recreate();
        super.notifyDataSetChanged();
    }

    public void filter(String charText) {
        Log.d("Filer function ","Working");
        charText = charText.toLowerCase(Locale.getDefault());
        hallDetailsList.clear();
        if (charText.length() == 0) {
            Log.d("Filer function if part ","Working");
            hallDetailsList.addAll(hallDetailsListTemp);
            notifyDataSetChanged();
        } else {
            Log.d("Filer else part","Working");
            for (HallDetails hallDetails : hallDetailsListTemp) {
                if (hallDetails.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    Log.d("Hall details ",hallDetails.getName());
                    hallDetailsList.add(hallDetails);
                }
            }
            notifyDataSetChanged();
        }

    }
}

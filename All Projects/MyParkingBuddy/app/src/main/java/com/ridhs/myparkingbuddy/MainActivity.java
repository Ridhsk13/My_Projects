package com.ridhs.myparkingbuddy;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.maps.CameraUpdate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback{
    Button btn_start;
    private static final int REQUEST_PERMISSIONS = 100;
    boolean boolean_permission = false;
    TextView tv_latitude, tv_longitude, tv_address, tv_area, tv_locality;
    SharedPreferences mPref;
    SharedPreferences.Editor medit;
    Double latitude, longitude;
    Geocoder geocoder;

    FirebaseDatabase database;
    DatabaseReference myRefParkingLocation, referenceUser;
    FirebaseAuth firebaseAuthUser;

    public ArrayList<ParkingLocation> locationlist = new ArrayList<ParkingLocation>();

    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    PendingResult<LocationSettingsResult> result;
    final static int REQUEST_LOCATION = 199;
    String username, useremail;
    LinearLayout linearLayout ;
    TextView textViewName ;
    TextView textViewEmail ;
    ListView listViewSuggestedFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        database = FirebaseDatabase.getInstance();
        firebaseAuthUser = FirebaseAuth.getInstance();
        myRefParkingLocation = database.getReference("parkinglocations");
        referenceUser = database.getReference("userdetails");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        linearLayout = (LinearLayout) navigationView.getHeaderView(0);
        textViewName = (TextView) linearLayout.getChildAt(0);
        textViewEmail = (TextView) linearLayout.getChildAt(1);

        listViewSuggestedFriends = (ListView) findViewById(R.id.home_screen_lv_suggested_friends);


    }

    public void startLocationSrvice() {
        System.out.println("This is working");
        fn_permission();
        if (boolean_permission) {
            Intent intent = new Intent(getApplicationContext(), GoogleService.class);
            startService(intent);
            getDataFromDatabase();
            suggestFriendsBasedOnPreference();
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
    }
    public void setData(){
        final ArrayList<User> users = new ArrayList<>();
        referenceUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<User> tempUsers = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    //if (suggestedUser.contains(snapshot.getKey())){
                        User user = new User();
                        user.setUser_name(String.valueOf(snapshot.child("user_name").getValue()));
                        user.setEmail(String.valueOf(snapshot.child("email").getValue()));
                        user.setPhone(String.valueOf(snapshot.child("phone").getValue()));
                        user.setPref1(String.valueOf(snapshot.child("pref1").getValue()));
                        user.setPref2(String.valueOf(snapshot.child("pref2").getValue()));
                        user.setPref3(String.valueOf(snapshot.child("pref3").getValue()));
                        user.setSchool(String.valueOf(snapshot.child("school").getValue()));
                        user.setLatitude(Double.valueOf(String.valueOf(snapshot.child("current_location").child("latitude").getValue())));
                        user.setLongitude(Double.valueOf(String.valueOf(snapshot.child("current_location").child("longitude").getValue())));

                        users.add(user);
                        System.out.println(user);
                    //}
                }
                //users.addAll(tempUsers);
                System.out.println(users);
                setMyAdapter(users);

            }
            public void setMyAdapter(ArrayList<User> users1){
                System.out.println(users);
                MyCustomAdapter myCustomAdapter = new MyCustomAdapter(users1,MainActivity.this);
                listViewSuggestedFriends.setAdapter(myCustomAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void suggestFriendsBasedOnPreference(){
        referenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> tempSuggestedUser = new ArrayList<String>();
                ArrayList<String> userPref = new ArrayList<String>();
                userPref.add(String.valueOf(dataSnapshot.child(firebaseAuthUser.getCurrentUser().getUid()).child("pref1").getValue()));
                userPref.add(String.valueOf(dataSnapshot.child(firebaseAuthUser.getCurrentUser().getUid()).child("pref2").getValue()));
                userPref.add(String.valueOf(dataSnapshot.child(firebaseAuthUser.getCurrentUser().getUid()).child("pref3").getValue()));
                username = String.valueOf(dataSnapshot.child(firebaseAuthUser.getCurrentUser().getUid()).child("user_name").getValue());
                useremail = String.valueOf(dataSnapshot.child(firebaseAuthUser.getCurrentUser().getUid()).child("email").getValue());
                if (!username.isEmpty()){
                    textViewName.setText(username);
                }
                if (!useremail.isEmpty()){
                    textViewEmail.setText(useremail);
                }
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ArrayList<String> tempPref = new ArrayList<String>();
                    tempPref.add(String.valueOf(snapshot.child("pref1").getValue()));
                    tempPref.add(String.valueOf(snapshot.child("pref2").getValue()));
                    tempPref.add(String.valueOf(snapshot.child("pref3").getValue()));

                    if(preferenceMatches(userPref,tempPref)){
                        tempSuggestedUser.add(snapshot.getKey());
                    }
                }
                Log.d("Temp Users", String.valueOf(tempSuggestedUser));
                suggestedUser.addAll(tempSuggestedUser);
                setData();
                Log.d("Temp Suggested Users", String.valueOf(suggestedUser));
            }
            public boolean preferenceMatches(ArrayList<String> user, ArrayList<String> temp){
                boolean flag = false;
                for(String tmp1: user) {
                    for(String tmp2: temp) {
                        if(tmp1.compareTo(tmp2) == 0) {
                            flag = true;
                        }
                    }
                }
                return  flag;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public final int LOCATION_REQUEST = 100;

    public void checkLocationService() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Location Service is disabled");  // GPS not found
            builder.setMessage("Turn on the location service to run app perfectly"); // Want to enable?
            builder.setPositiveButton("Turn me On", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    startLocationSrvice();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "You must enable location service to use this app", Toast.LENGTH_SHORT).show();
                    finishAndRemoveTask();
                }
            });
            builder.create().show();
        } else {
            //boolean_permission = true;
            startLocationSrvice();
        }
    }

    public void getDataFromDatabase() {
        final ArrayList<ParkingLocation> mylist = new ArrayList<ParkingLocation>();
        myRefParkingLocation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot mydata : dataSnapshot.getChildren()) {

                    ParkingLocation location = new ParkingLocation();
                    location.setLoc_name(String.valueOf(mydata.child("loc_name").getValue()));
                    location.setLoc_address(String.valueOf(mydata.child("loc_address").getValue()));
                    location.setLoc_latitude(String.valueOf(mydata.child("loc_cordinates").child("latitude").getValue()));
                    location.setLoc_longitude(String.valueOf(mydata.child("loc_cordinates").child("longitude").getValue()));
                    mylist.add(location);

                    System.out.println(mylist);
                    //ystem.out.println(mylist.get(0).getLoc_name());
                    Log.d("Database Keys", mydata.getKey());
                }
                setMyLocationlist(mylist);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    ArrayList<Double> distances = new ArrayList<Double>();

    public void setMyLocationlist(ArrayList<ParkingLocation> list) {
        locationlist = list;
        for (int i = 0; i < locationlist.size(); i++) {

            Double loc_latitude = Double.valueOf(locationlist.get(i).getLoc_latitude());
            Double loc_longitude = Double.valueOf(locationlist.get(i).getLoc_longitude());

            Log.d("loc_latitude", String.valueOf(loc_latitude));
            Log.d("loc_longitude", String.valueOf(loc_longitude));
            Log.d("latitude", String.valueOf(latitude));
            Log.d("longitude", String.valueOf(longitude));

            Double lat1 = latitude;
            Double lon1 = longitude;
            Double lat2 = loc_latitude;
            Double lon2 = loc_longitude;

            Double theta = lon1 - lon2;
            Double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                    + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
                    * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);
            dist = dist * 60; // 60 nautical miles per degree of seperation
            dist = dist * 1852; // 1852 meters per nautical mile
            distances.add(dist);
        }
        System.out.println(distances);
        findNearByParkingSpot(distances, locationlist);
    }
    public Double findDistance (Double lat1,Double lon1,Double lat2,Double lon2){
        Double theta = lon1 - lon2;
        Double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60; // 60 nautical miles per degree of seperation
        dist = dist * 1852; // 1852 meters per nautical mile
        return dist;
    }
    public void findNearByParkingSpot(ArrayList<Double> distances, ArrayList<ParkingLocation> locationlist) {
        System.out.println(distances);
        System.out.println(locationlist);

        Collections.sort(distances);
        int i = 0;
        while (i < 3 && i < distances.size()) {
            suggestFriendsBasedOnParkingSpot(locationlist.get(i));
            i++;
        }
    }

    Set<String> suggestedUser = new HashSet<>();
    public void suggestFriendsBasedOnParkingSpot(final ParkingLocation location) {
        referenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> temp = new ArrayList<String>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    System.out.println(location.getLoc_name());
                    Double lat2 = Double.valueOf(location.getLoc_latitude());
                    Double lat1 = Double.valueOf((Double) snapshot.child("current_location").child("latitude").getValue());
                    Double lon2 = Double.valueOf(location.getLoc_longitude());
                    Double lon1 = Double.valueOf((Double) snapshot.child("current_location").child("longitude").getValue());
                    Double dist = findDistance(lat1,lon1,lat2,lon2);
                    if (dist < 500){
                        temp.add(snapshot.getKey());
                    }
                }
                suggestedUser = new HashSet<String>(temp);
                System.out.println(suggestedUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    private void fn_permission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION))) {
                boolean_permission = true;
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_PERMISSIONS);
            }
        } else {
            boolean_permission = true;
            //startLocationSrvice();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    boolean_permission = true;
                    startLocationSrvice();
                } else {
                    Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();

                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("onActivityResult()", Integer.toString(resultCode));

        //final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case REQUEST_LOCATION:
                switch (resultCode) {
                    case Activity.RESULT_OK: {
                        // All required changes were successfully made
                        Toast.makeText(MainActivity.this, "Location enabled by user!", Toast.LENGTH_LONG).show();
                        break;
                    }
                    case Activity.RESULT_CANCELED: {
                        // The user was asked to change settings, but chose not to
                        Toast.makeText(MainActivity.this, "Location not enabled, user cancelled.", Toast.LENGTH_LONG).show();
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            latitude = Double.valueOf(intent.getStringExtra("latutide"));
            longitude = Double.valueOf(intent.getStringExtra("longitude"));
            location = new LatLng(latitude,longitude);
            mMap.addMarker(new MarkerOptions().position(location).title("You are Here!"));
            CameraUpdate center = CameraUpdateFactory.newLatLng(location);
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
            mMap.moveCamera(center);
            mMap.animateCamera(zoom);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(GoogleService.str_receiver));

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.login) {
            // Handle the camera action
        } else if (id == R.id.parking_preferences) {

        } else if (id == R.id.friends) {

        } else if (id == R.id.Logout) {

            firebaseAuthUser.signOut();
            Toast.makeText(MainActivity.this, "You have Successfully logged out",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, WelcomeScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        } /*else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private GoogleMap mMap;
    public LatLng location ;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        checkLocationService();
        //location = new LatLng(latitude, longitude);

    }
}

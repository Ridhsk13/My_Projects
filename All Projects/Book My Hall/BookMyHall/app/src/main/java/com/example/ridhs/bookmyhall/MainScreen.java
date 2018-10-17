package com.example.ridhs.bookmyhall;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;


public class MainScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listViewHallList;
    private DatabaseReference mRef, mDatabase;
    private FirebaseDatabase firebaseDatabase;
    private TextView textViewNavHeaderName;
    private ArrayList<String> hallId = new ArrayList<String>();
    private ArrayList<HallDetails> mainHallList = new ArrayList<HallDetails>();
    private ArrayList<HallDetails> mainHallListTemp = new ArrayList<HallDetails>();

    private FirebaseAuth firebaseAuth;
    private SharedPreferences preferences;
    private LinearLayout linearLayoutMain;
    private ProgressBar progressBar;

    private EditText editTextSearch;
    public ImageView imageViewSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        preferences = getSharedPreferences(MyConstants.MY_PREFERENCE, MODE_PRIVATE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Book My Hall");
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference("Hall");

        progressBar = (ProgressBar) findViewById(R.id.activity_main_progressbar);
        linearLayoutMain = (LinearLayout) findViewById(R.id.activity_main_screen_linearLayout_main);
        linearLayoutMain.setVisibility(View.GONE);

        getHallFromDatabase();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        textViewNavHeaderName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_textView_welcome_name);
        if (preferences.getString(MyConstants.USER_TYPE_KEY, null).equals(MyConstants.USER_TYPE_ADMIN)) {
            navigationView.inflateMenu(R.menu.admin_activity_main_screen_drawer);
            textViewNavHeaderName.setText("Welcome, Admin");
        } else if (preferences.getString(MyConstants.USER_TYPE_KEY, null).equals(MyConstants.USER_TYPE_RESERVER)) {
            navigationView.inflateMenu(R.menu.reserver_activity_main_screen_drawer);
            if (user.getDisplayName() != null) {
                textViewNavHeaderName.setText("Welcome, " + firebaseAuth.getCurrentUser().getDisplayName());
            } else {
                textViewNavHeaderName.setText("Welcome, HallReserver");
            }
        } else {
            navigationView.inflateMenu(R.menu.visitor_activity_main_screen_drawer);
            textViewNavHeaderName.setText("Welcome, Visitor");
        }


    }

    public void getHallFromDatabase() {

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    hallId.add(child.getKey());
                    mainHallList.add(child.getValue(HallDetails.class));
                }
                Log.d("Results:", "Data read completed Successfully");
                progressBar.setVisibility(View.GONE);
                setData(mainHallList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Results:", "Failed to read value");
            }
        });
    }

    public void setData(ArrayList<HallDetails> hallList) {
        progressBar.setVisibility(View.GONE);
        linearLayoutMain.setVisibility(View.VISIBLE);

        listViewHallList = (ListView) findViewById(R.id.activity_main_screen_listview_hall_list);
        final ListViewCustomAdapter adapter = new
                ListViewCustomAdapter(this, hallList, preferences.getString(MyConstants.USER_TYPE_KEY, null), hallId);
        listViewHallList.setAdapter(adapter);

        if (!preferences.getString(MyConstants.USER_TYPE_KEY, null).equals(MyConstants.USER_TYPE_ADMIN)) {
            listViewHallList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intentHallDetails = new Intent(MainScreen.this, HallDescription.class);
                    intentHallDetails.putExtra("hall_id", hallId.get(position));
                    intentHallDetails.putExtra("hall_detail", mainHallList.get(position));
                    startActivity(intentHallDetails);
                }
            });
        }

        editTextSearch = (EditText) findViewById(R.id.main_screen_editText_search);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = editTextSearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }
        });
        /*imageViewSearch = (ImageView) findViewById(R.id.main_screen_button_search);
        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainScreen.this, "Working click", Toast.LENGTH_SHORT).show();
                String text = editTextSearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }
        });*/
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }

    //@Override
    /*public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu_add_hall) {
            Intent intent = new Intent(MainScreen.this, AddHall.class);
            startActivity(intent);
        } else if (id == R.id.nav_menu_logout_reserver) {
            firebaseAuth.signOut();
            Intent intent = new Intent(MainScreen.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_menu_logout_admin) {
            Intent intent = new Intent(MainScreen.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_menu_profile) {
            Intent intent = new Intent(MainScreen.this, ViewProfile.class);
            startActivity(intent);
        } else if (id == R.id.nav_menu_register) {
            Intent intent = new Intent(MainScreen.this, Register.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            Log.d("Navigation :", "Error");
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

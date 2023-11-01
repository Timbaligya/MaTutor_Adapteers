package com.example.matutor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewProgressReportsLearner extends AppCompatActivity {

    Button tutorSwitch;
    ExtendedFloatingActionButton menuFabBtn;
    FloatingActionButton viewProfile, viewProgressReport, viewBookings, viewBookingsHistory, viewReviewsHistory;
    Boolean allFabVisible; //checks for visibility of sub fabs
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // removes status bar
        setContentView(R.layout.activity_view_progress_reports_learner);

        //assignment
        tutorSwitch = findViewById(R.id.switchButton);
        menuFabBtn = findViewById(R.id.menuFab);
        viewProfile = findViewById(R.id.viewProfileFab);
        viewProgressReport = findViewById(R.id.viewReportsFab);
        viewBookings = findViewById(R.id.viewBookingsFab);
        viewBookingsHistory = findViewById(R.id.viewHistoryFab);
        viewReviewsHistory = findViewById(R.id.viewReviewsFab);
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.dashboard); //profile

        viewProfile.setVisibility(View.GONE);
        viewProgressReport.setVisibility(View.GONE);
        viewBookings.setVisibility(View.GONE);
        viewBookingsHistory.setVisibility(View.GONE);
        viewReviewsHistory.setVisibility(View.GONE);


        //switch profile type
        tutorSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ViewProgressReportTutor.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
                finish();
            }
        });

        //set boolean variable as false
        allFabVisible = false;

        //shrink extended fab + click listener
        menuFabBtn.shrink();
        menuFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!allFabVisible) {
                    //extend extended fab and set sub fab as visible
                    menuFabBtn.extend();
                    viewProfile.show();
                    viewProgressReport.show();
                    viewBookings.show();
                    viewBookingsHistory.show();
                    viewReviewsHistory.show();
                    allFabVisible = true;

                } else {
                    //hid sub fabs
                    menuFabBtn.shrink();
                    viewProfile.hide();
                    viewProgressReport.hide();
                    viewBookings.hide();
                    viewBookingsHistory.hide();
                    viewReviewsHistory.hide();
                    allFabVisible = false;
                }
            }
        });

        //sub fab click listeners
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
                finish();
            }
        });

        viewProgressReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Current page!", Toast.LENGTH_SHORT).show();
            }
        });

        viewBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Bookings.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
                finish();
            }
        });

        viewBookingsHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BookingsHistory.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
                finish();
            }
        });

        viewReviewsHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReviewsHistory.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
                finish();
            }
        });

        //navbar navigation
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.posting) {
                    startActivity(new Intent(getApplicationContext(), Posting.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                else if (itemId == R.id.dashboard) {
                    startActivity(new Intent(getApplicationContext(), Dashboard.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                else if (itemId == R.id.content) {
                    startActivity(new Intent(getApplicationContext(), Content.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                else if (itemId == R.id.create) {
                    return true;
                }
                else if (itemId == R.id.notif) {
                    startActivity(new Intent(getApplicationContext(), Notification.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Posting.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
        finish();
    }
}
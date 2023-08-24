package com.example.matutor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class LearnerProfilePreview extends AppCompatActivity {

    Button accept, reject, close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // removes status bar
        setContentView(R.layout.activity_learner_profile_preview);

        accept = findViewById(R.id.acceptButton);
        reject = findViewById(R.id.rejectButton);
        close = findViewById(R.id.closeButton);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ScheduleSession.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
                finish();
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rejectConfirmation();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DashboardTutor.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
                finish();
            }
        });

    }

    private void rejectConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Do you really want to reject this offer?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), NotificationTutor.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), DashboardTutor.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
        finish();
    }

}
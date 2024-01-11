package com.example.matutor;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import android.util.Log;

import com.example.matutor.adapters.selectPost_adapter;
import com.example.matutor.databinding.ActivitySelectPostingBinding;
import com.example.matutor.data.selectPost_data;
import com.example.matutor.models.selectPost_model;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SelectPosting extends AppCompatActivity {

    private String userType;
    private ActivitySelectPostingBinding binding;

    private selectPost_adapter selectPostAdapter;

    private selectPost_model selectPostModel;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectPostingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding.interestedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Firestore", "Starting Schedule Session activity");
                Intent intent = new Intent(SelectPosting.this, ScheduleSession.class);
                startActivity(intent);
            }
        });

        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Posting.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
                finish();
            }
        });

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userEmail = currentUser.getEmail();

            CollectionReference createdPostRef = FirebaseFirestore.getInstance()
                    .collection("createdPosts")
                    .document("createdPost_" + userType)
                    .collection(userEmail);

            Query query = createdPostRef.orderBy("userEmail", Query.Direction.DESCENDING);

            FirestoreRecyclerOptions<selectPost_data> options = new FirestoreRecyclerOptions.Builder<selectPost_data>()
                    .setQuery(query, selectPost_data.class)
                    .setLifecycleOwner(this) // For automatic lifecycle management
                    .build();
        } else {
            Toast.makeText(this, "User not authenticated. (ViewCreatedPost, setUpRecyclerView)", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
        finish();
    }
}

package com.example.matutor.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import static android.content.Context.MODE_PRIVATE;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.matutor.SelectPosting;
import com.example.matutor.adapters.allPost_adapter;
import com.example.matutor.adapters.allPost_adapter.OnItemClickListener;
import com.example.matutor.data.allPost_data;
import com.example.matutor.models.allPost_model;
import com.example.matutor.databinding.FragmentAllPostingBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class AllPostingFragment extends Fragment implements allPost_adapter.OnItemClickListener {
    private String userType;
    private FragmentAllPostingBinding binding;
    private allPost_adapter adapter;
    private allPost_model model;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAllPostingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        SharedPreferences pref = getContext().getSharedPreferences("user_type", MODE_PRIVATE);
        userType = pref.getString("user_type", "");

        model = new ViewModelProvider(this).get(allPost_model.class);

        // Fetch data in the fragment
        fetchPosts(userType);

        // Set up RecyclerView and adapter
        setupRecyclerView();

        return view;
    }

    private void setupRecyclerView() {
        // Assuming you have a RecyclerView in your layout with id "recyclerView"
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userEmail = currentUser.getEmail();

            CollectionReference createdPostRef = FirebaseFirestore.getInstance()
                    .collection("createdPosts")
                    .document("createdPost_" + userType)
                    .collection(userEmail);

            Query query = createdPostRef.orderBy("userEmail", Query.Direction.DESCENDING);

            // Create FirestoreRecyclerOptions
            FirestoreRecyclerOptions<allPost_data> options = new FirestoreRecyclerOptions.Builder<allPost_data>()
                    .setQuery(query, allPost_data.class)
                    .build();

            // Create the adapter
            adapter = new allPost_adapter(options);

            // Set the item click listener
            adapter.setOnItemClickListener(this);

            // Set the adapter for the RecyclerView
            binding.recyclerView.setAdapter(adapter);
        }
    }

    private void fetchPosts(String userType) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();

            firestore.collection("createdPosts")
                    .document("createdPost_" + userType)
                    .collection("userEmail")
                    .get()
                    .addOnSuccessListener(querySnapshot -> {
                        for (DocumentSnapshot userSnapshot : querySnapshot.getDocuments()) {
                            String userEmail = userSnapshot.getId();
                            fetchPostsForUser(userType, userEmail);
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("Firestore", "Error fetching user emails: " + e.getMessage());
                    });
        }
    }

    private void fetchPostsForUser(String userType, String userEmail) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        firestore.collection("createdPosts")
                .document("createdPost_" + userType)
                .collection("userEmail")
                .document(userEmail)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        allPost_data allPostData = documentSnapshot.toObject(allPost_data.class);
                        if (allPostData != null) {
                            allPostData.setUserEmail(userEmail);
                            allPostData.setPostId(documentSnapshot.getId());

                            // Pass the data to the ViewModel
                            model.updateAllPosts(Collections.singletonList(allPostData));

                            Log.d("Firestore", "Successfully fetched post data for " + userEmail + ": " + allPostData);
                        } else {
                            Log.e("Firestore", "Document snapshot is null for user: " + userEmail);
                        }
                    } else {
                        Log.e("Firestore", "Document does not exist for user: " + userEmail);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error fetching user data: " + e.getMessage());
                });
    }

    @Override
    public void onSeeMoreClick(DocumentSnapshot documentSnapshot, int position) {
        allPost_data clickedPost = documentSnapshot.toObject(allPost_data.class);
        if (clickedPost != null) {
            // Example: Open a new activity with detailed information
            Intent intent = new Intent(getContext(), SelectPosting.class);
            intent.putExtra("postId", clickedPost.getPostId());
            startActivity(intent);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }
}
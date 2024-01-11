package com.example.matutor.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matutor.adapters.posting_adapter;
import com.example.matutor.data.allPost_data;
import com.example.matutor.databinding.FragmentAllPostingBinding;
import com.example.matutor.R;
import com.example.matutor.SelectPosting;
import com.example.matutor.models.allPost_model;
import com.example.matutor.models.createdPost_model;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.DocumentSnapshot;
import android.content.Context;
import android.content.Intent;

public class AllPostingFragment extends Fragment {

    private FragmentAllPostingBinding binding;
    private posting_adapter allPostAdapter;
    private allPost_model allPostModel;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAllPostingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Initialize RecyclerView and adapter
        RecyclerView recyclerView = binding.recyclerView;

        allPostModel = new ViewModelProvider(this).get(allPost_model.class);

        // Configure the query
        Query query = firestore.collection("createdPosts");
        FirestoreRecyclerOptions<allPost_data> options = new FirestoreRecyclerOptions.Builder<allPost_data>()
                .setQuery(query, allPost_data.class)
                .build();

        allPostAdapter = new posting_adapter(options);
        allPostAdapter.setOnItemClickListener(new posting_adapter.OnItemClickListener() {
            @Override
            public void onSeeMoreClick(DocumentSnapshot documentSnapshot, int position) {
                // Handle both item click and "See More" button click
                Log.d("Firestore", "Starting SelectPosting activity");
                Context context = view.getContext();
                Intent intent = new Intent(getContext(), SelectPosting.class);
                intent.putExtra("postId", documentSnapshot.getId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(allPostAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadAllPosts();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Firestore", "Fragment onStart");
        allPostAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("Firestore", "Fragment onStop");
        allPostAdapter.stopListening();
    }

    private void loadAllPosts() {
        allPostModel.loadAllPosts();
    }
}

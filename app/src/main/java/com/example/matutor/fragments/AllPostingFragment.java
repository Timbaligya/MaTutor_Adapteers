package com.example.matutor.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matutor.adapters.posting_adapter;
import com.example.matutor.data.allPost_data;
import com.example.matutor.databinding.FragmentAllPostingBinding;
import com.example.matutor.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class AllPostingFragment extends Fragment {

    private FragmentAllPostingBinding binding;
    private posting_adapter allPostAdapter;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_posting, container, false);

        // Initialize RecyclerView and adapter
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        // Configure the query
        Query query = firestore.collection("createdPosts");
        FirestoreRecyclerOptions<allPost_data> options = new FirestoreRecyclerOptions.Builder<allPost_data>()
                .setQuery(query, allPost_data.class)
                .build();

        allPostAdapter = new posting_adapter(options);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(allPostAdapter);


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
}

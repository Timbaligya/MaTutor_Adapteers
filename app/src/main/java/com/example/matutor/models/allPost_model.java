package com.example.matutor.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.matutor.data.allPost_data;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.FieldPath;

import com.google.android.gms.tasks.OnFailureListener;
import androidx.annotation.NonNull;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class allPost_model extends ViewModel {

    private String userType;
    private MutableLiveData<List<allPost_data>> allPosts = new MutableLiveData<>();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public LiveData<List<allPost_data>> getAllPosts() {
        return allPosts;
    }

    public void loadAllPosts() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            Log.d("Firestore", "User Email: " + userEmail);

            firestore.collection("createdPosts")
                    .whereIn(FieldPath.documentId(), Arrays.asList("createdPost_learner", "createdPost_tutor"))
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot querySnapshots) {
                            List<allPost_data> allPostList = new ArrayList<>();
                            Log.d("Firestore", "onSuccess: " + querySnapshots.size() + " documents retrieved.");

                            for (QueryDocumentSnapshot document : querySnapshots) {
                                // Directly access the user-specific subcollection
                                CollectionReference userCollectionRef = document.getReference().collection(userEmail);
                                userCollectionRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot userSnapshots) {
                                        for (QueryDocumentSnapshot userDocument : userSnapshots) {
                                            allPost_data createdPostData = userDocument.toObject(allPost_data.class);
                                            allPostList.add(createdPostData);
                                        }
                                        allPosts.setValue(allPostList);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e("Firestore", "Error fetching user collection: " + e.getMessage());
                                    }
                                });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Firestore", "Error fetching createdPosts collection: " + e.getMessage());
                        }
                    });
        }
    }

}
package com.example.matutor.models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.matutor.data.allPost_data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class allPost_model extends ViewModel {
    private MutableLiveData<List<allPost_data>> allPosts = new MutableLiveData<>();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public LiveData<List<allPost_data>> getAllPosts() {
        return allPosts;
    }

    // This method updates the LiveData with the new list of posts
    public void updateAllPosts(List<allPost_data> postsList) {
        allPosts.postValue(postsList);
    }

    public void loadAllPosts() {
        filterAllPosts();
    }

    private void filterAllPosts() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            filterPostsByType("createdPost_learner");
            filterPostsByType("createdPost_tutor");
        }
    }

    private void filterPostsByType(String filterType) {
        firestore.collection("createdPosts")
                .document(filterType)
                .collection("userEmail")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<allPost_data> currentList = new ArrayList<>();

                    for (QueryDocumentSnapshot userDocument : querySnapshot) {
                        String userEmail = userDocument.getId();

                        firestore.collection("createdPosts")
                                .document(filterType)
                                .collection("userEmail")
                                .document(userEmail)
                                .collection("postings")  // Replace with your actual subcollection name
                                .get()
                                .addOnSuccessListener(postSnapshot -> {
                                    for (QueryDocumentSnapshot postDocument : postSnapshot) {
                                        allPost_data allPostData = postDocument.toObject(allPost_data.class);
                                        allPostData.setUserEmail(userEmail);
                                        allPostData.setPostId(postDocument.getId());
                                        allPostData.setUserType(filterType);
                                        currentList.add(allPostData);
                                    }
                                    updateAllPosts(currentList);
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("allPost_model", "Error fetching postings: " + e.getMessage());
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("allPost_model", "Error fetching user emails: " + e.getMessage());
                });
    }
}

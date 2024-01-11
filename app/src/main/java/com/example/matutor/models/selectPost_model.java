package com.example.matutor.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.matutor.data.selectPost_data;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class selectPost_model extends ViewModel {
    private String userType;
    private MutableLiveData<List<selectPost_data>> selectedPost = new MutableLiveData<>();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public LiveData<List<selectPost_data>> getSelectedPost() {
        return selectedPost;
    }

    public void loadCreatedPosts() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userEmail = currentUser.getEmail();

            firestore.collection("createdPosts")
                    .document("createdPost_" + userType)
                    .collection(userEmail)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot querySnapshots) {
                            List<selectPost_data> selectedPostList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : querySnapshots) {
                                selectPost_data selectedPostData = document.toObject(selectPost_data.class);
                                selectedPostList.add(selectedPostData);
                            }
                            selectedPost.setValue(selectedPostList);
                        }
                    });
        }
    }
}

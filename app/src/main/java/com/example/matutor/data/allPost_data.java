package com.example.matutor.data;

import java.util.List;

public class allPost_data {
    private String postTitle;
    private String postDescription;
    private List<String> postTags;
    private String userFirstname;
    private String userLastname;
    private String userEmail; // Add userEmail and postId fields
    private String postId;
    private String userType;

    public allPost_data() {
        // Empty constructor needed for Firebase
    }

    public allPost_data(String postTitle, String postDescription, List<String> postTags, String userFirstname, String userLastname) {
        this.postTitle = postTitle;
        this.postDescription = postDescription;
        this.postTags = postTags;
        this.userFirstname = userFirstname;
        this.userLastname = userLastname;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public List<String> getPostTags() {
        return postTags;
    }

    public String getUserFirstname() {
        return userFirstname;
    }

    public String getUserLastname() {
        return userLastname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }
}

package com.example.matutor.data;

import java.util.List;

public class selectPost_data {
    private String userFullname;
    private List<String> interestTags;
    private String postTitle;
    private String postDescription;

    public selectPost_data() {
        // Empty constructor needed for Firebase
    }

    public selectPost_data(String postTitle, String postDescription, List<String> interestTags, String userFullname) {
        this.postTitle = postTitle;
        this.postDescription = postDescription;
        this.interestTags = interestTags;
        this.userFullname = userFullname;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostDesc() {
        return postDescription;
    }

    public List<String> getInterestTags() {
        return interestTags;
    }
    public String getUserFullname() {
        return userFullname;
    }
}

package com.example.matutor.data;

import java.util.List;

public class allPost_data {
    private String postTitle;
    private String postDescription;
    private List<String> postTags;
    private String userFirstname;
    private String userLastname;

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
}


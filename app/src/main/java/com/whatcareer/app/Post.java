package com.whatcareer.app;


public class Post {

    String postTitle;
    String postContent;
    String postUrl;
    String postImage;
    String postDate;

    Post(String postTitle, String postContent, String postUrl, String postImage, String postDate) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postUrl = postUrl;
        this.postImage = postImage;
        this.postDate = postDate;
    }


    public String getPostTitle() {
        return postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public String getPostImage() {
        return postImage;
    }

    public String getPostDate() {
        return postDate;
    }
}

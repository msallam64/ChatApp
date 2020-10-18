package com.example.chatapp.Model;

public class User {
    private String id,username,imageUrl,status;

    public User() {
    }

    public User(String id, String username, String imageUrl, String status) {
        this.id = id;
        this.username = username;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageUrl;
    }

   
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

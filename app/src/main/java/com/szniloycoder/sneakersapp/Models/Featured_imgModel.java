package com.szniloycoder.sneakersapp.Models;

public class Featured_imgModel {

    String imageUrl;
    String key;
    String title;

    public Featured_imgModel( String imageUrl2, String key2, String title2) {
        this.imageUrl = imageUrl2;
        this.key = key2;
        this.title = title2;
    }

    public Featured_imgModel() {
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl2) {
        this.imageUrl = imageUrl2;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key2) {
        this.key = key2;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title2) {
        this.title = title2;
    }
}

package com.szniloycoder.sneakersapp.Models;

import java.util.ArrayList;

public class FavModel {

    String catId;
    String Category;
    String DeliveryTme;
    String Description;
    String Ingredients;
    String Name;
    String Price;
    ArrayList<String> imageUrls;
    String itemId;
    String key;
    String ratings;
    ArrayList<String> sizes;

    public FavModel(String catId, String category, String deliveryTme, String description, String ingredients, String name, String price, ArrayList<String> imageUrls, String itemId, String key, String ratings, ArrayList<String> sizes) {
        this.catId = catId;
        Category = category;
        DeliveryTme = deliveryTme;
        Description = description;
        Ingredients = ingredients;
        Name = name;
        Price = price;
        this.imageUrls = imageUrls;
        this.itemId = itemId;
        this.key = key;
        this.ratings = ratings;
        this.sizes = sizes;
    }

    public FavModel() {
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDeliveryTme() {
        return DeliveryTme;
    }

    public void setDeliveryTme(String deliveryTme) {
        DeliveryTme = deliveryTme;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getIngredients() {
        return Ingredients;
    }

    public void setIngredients(String ingredients) {
        Ingredients = ingredients;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public ArrayList<String> getSizes() {
        return sizes;
    }

    public void setSizes(ArrayList<String> sizes) {
        this.sizes = sizes;
    }
}
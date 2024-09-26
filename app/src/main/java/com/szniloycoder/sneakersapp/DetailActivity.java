package com.szniloycoder.sneakersapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.szniloycoder.sneakersapp.Adapters.DetailImgViewAdapter;
import com.szniloycoder.sneakersapp.Adapters.SizeAdapter;
import com.szniloycoder.sneakersapp.Models.FavModel;
import com.szniloycoder.sneakersapp.Models.SneakersModel;
import com.szniloycoder.sneakersapp.databinding.ActivityDetailBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    // Firebase and Database
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;

    // UI Components
    ActivityDetailBinding binding;
    SizeAdapter sizeAdapter;

    // Data Variables
    String catId;
    String foodCategory;
    String foodDeliveryTime;
    String foodDescription;
    String foodIngredients;
    String foodName;
    String foodPrice;
    ArrayList<String> imageUrls;
    String itemId;
    ArrayList<SneakersModel> list;
    String ratings;
    String selectedSize = "";
    ArrayList<String> sizeOptions = new ArrayList<>();
    ArrayList<String> sizes;
    double totalPrice = 0.0d;
    int totalQuantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // Initialize Firebase and UI components
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        list = new ArrayList<>();
        imageUrls = new ArrayList<>();

        // Get data from Intent
        retrieveIntentData();

        // Set UI Components
        setupUI();

        // Setup ViewPager and RecyclerView
        setupViewPager();
        setupSizeOptionsRecyclerView();

        // Setup Event Listeners
        setupEventListeners();

        // Fetch and Display Average Rating
     //   fetchAndDisplayAverageRating();
    }

    private void retrieveIntentData() {
        Intent intent = getIntent();
        foodName = intent.getStringExtra("foodName");
        ratings = intent.getStringExtra("foodRatings");
        sizes = intent.getStringArrayListExtra("foodSize");
        foodDeliveryTime = intent.getStringExtra("foodDeliveryTime");
        foodDescription = intent.getStringExtra("foodDescription");
        foodPrice = intent.getStringExtra("foodPrice");
        imageUrls = intent.getStringArrayListExtra("foodImage");
        foodIngredients = intent.getStringExtra("foodIngredient");
        itemId = intent.getStringExtra("ItemId");
        catId = intent.getStringExtra("catId");
        foodCategory = intent.getStringExtra("foodCatName");
    }

    private void setupUI() {
        binding.foodTitleTxt.setText(foodName);
        binding.descriptionTxt.setText(foodDescription);
        binding.foodPriceTxt.setText("$" + foodPrice);
        binding.ratingTxt.setText(ratings);
        binding.catNameTxt.setText(foodCategory);
    }

    private void setupEventListeners() {
        binding.btnBack.setOnClickListener(v -> finish());
        binding.btnFvtFood.setOnClickListener(v -> {
            binding.btnFvtFood.setImageResource(R.drawable.favorite_full);
        });
//        binding.ratingTxt.setOnClickListener(v -> {
//            Intent intent = new Intent(this, RatingsActivity.class);
//            intent.putExtra("itemId", itemId);
//            startActivity(intent);
//        });
        binding.addCartBtn.setOnClickListener(v ->{
            Toast.makeText(this, "Added to the cart", Toast.LENGTH_SHORT).show();
        });

        binding.plusBtn.setOnClickListener(v -> updateQuantity(true));
        binding.minusBtn.setOnClickListener(v -> updateQuantity(false));
    }

    private void setupViewPager() {
        binding.detailImgView.setAdapter(new DetailImgViewAdapter(this, imageUrls));
        binding.dotIndicator.setViewPager(binding.detailImgView);
    }

    private void setupSizeOptionsRecyclerView() {
        sizeOptions.addAll(sizes);
        sizeAdapter = new SizeAdapter(this, sizeOptions, size -> {
            selectedSize = size;
            sizeAdapter.setSelectedSize(size);
        });
        binding.recySizes.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        binding.recySizes.setAdapter(sizeAdapter);
    }

    @SuppressLint("StringFormatMatches")
    private void totalPriceAndQuantitySet() {
        double pricePerItem = Double.parseDouble(foodPrice.replaceAll("[^\\d.]", ""));
        totalPrice = pricePerItem;
        binding.totalFoodPriceTxt.setText(getString(R.string.total_price_format, totalPrice));
    }

    @SuppressLint("StringFormatMatches")
    private void updateQuantity(boolean increase) {
        double pricePerItem = Double.parseDouble(foodPrice.replaceAll("[^\\d.]", ""));
        if (increase) {
            totalQuantity++;
        } else if (totalQuantity > 1) {
            totalQuantity--;
        }
        binding.numTxt.setText(String.valueOf(totalQuantity));
        totalPrice = totalQuantity * pricePerItem;
        binding.totalFoodPriceTxt.setText(getString(R.string.total_price_format, totalPrice));
    }
    
    

}
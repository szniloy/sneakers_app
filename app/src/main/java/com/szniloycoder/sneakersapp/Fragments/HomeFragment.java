package com.szniloycoder.sneakersapp.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.szniloycoder.sneakersapp.Adapters.CategoryAdapter;
import com.szniloycoder.sneakersapp.Adapters.CategorySneakersAdapter;
import com.szniloycoder.sneakersapp.Adapters.FeaturedAdapter;
import com.szniloycoder.sneakersapp.Models.CategoryModel;
import com.szniloycoder.sneakersapp.Models.Featured_imgModel;
import com.szniloycoder.sneakersapp.Models.SneakersModel;
import com.szniloycoder.sneakersapp.R;
import com.szniloycoder.sneakersapp.databinding.FragmentHomeBinding;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    // Variable Declarations
    CategorySneakersAdapter adapter;
    FirebaseAuth auth;
    FragmentHomeBinding binding;
    CategoryAdapter catAdapter;
    ArrayList<CategoryModel> catList;
    Activity context;
    FirebaseDatabase database;
    FirebaseUser firebaseUser;
    ArrayList<SneakersModel> list;
    DatabaseReference reference;
    Handler sliderHandle = new Handler();

    // Slider runnable to auto-scroll featured items
    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            binding.featuredView.setCurrentItem(binding.featuredView.getCurrentItem() + 1);
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // Initialize Firebase and variables
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        list = new ArrayList<>();

        // Set up user data and features
        initFeatured();
        initCategory();
        initPopularFoods();
        //totalCartItemCount();

        // Button click listeners
      //  binding.btnCart.setOnClickListener(view13 -> startActivity(new Intent(context, CartActivity.class)));

    }

    // Set up user data from Firebase


    // Initialize featured items slider
    private void initFeatured() {
        DatabaseReference myRef = database.getReference("featured");
        binding.progressBarFeatured.setVisibility(View.VISIBLE);

        ArrayList<Featured_imgModel> items = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        items.add(snapshot1.getValue(Featured_imgModel.class));
                    }
                    featureds(items);
                    binding.progressBarFeatured.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void featureds(ArrayList<Featured_imgModel> items) {
        FeaturedAdapter featuredAdapter = new FeaturedAdapter(items, binding.featuredView);
        binding.featuredView.setAdapter(featuredAdapter);
        binding.featuredView.setClipToPadding(false);
        binding.featuredView.setClipChildren(false);
        binding.featuredView.setOffscreenPageLimit(3);
        binding.featuredView.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        binding.featuredView.setPageTransformer(transformer);

        binding.featuredView.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandle.removeCallbacks(sliderRunnable);
                sliderHandle.postDelayed(sliderRunnable, 2000);
            }
        });
    }


    // Initialize categories
    private void initCategory() {
        DatabaseReference myRef = database.getReference()
                .child("Category").child("Brands");
        binding.progressBarCategory.setVisibility(View.VISIBLE);
        catList = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    catList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        CategoryModel model = dataSnapshot.getValue(CategoryModel.class);
                        catList.add(model);
//                        if (model != null) {
//                          //  model.setKey(dataSnapshot.getKey());
//                            catList.add(model);
//                        }
                    }
                    catAdapter = new CategoryAdapter(context, catList);
                    binding.reyCategory.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                    binding.reyCategory.setAdapter(catAdapter);
                    binding.reyCategory.setNestedScrollingEnabled(true);
                    binding.progressBarCategory.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }


    // Initialize popular foods
    private void initPopularFoods() {
        binding.progressBarPopuFoods.setVisibility(View.VISIBLE);

        database.getReference().child("Sneakers").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        list.add(dataSnapshot.getValue(SneakersModel.class));
                    }
                    if (!list.isEmpty()) {
                        adapter = new CategorySneakersAdapter(context, list);
                        binding.reyPopuFoods.setLayoutManager(new GridLayoutManager(context, 2));
                        binding.reyPopuFoods.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                    binding.progressBarPopuFoods.setVisibility(View.GONE);
                } else {
                    Toast.makeText(context, "No Food Available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Count total cart items
//    private void totalCartItemCount() {
//        database.getReference().child("CartItems").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    binding.totalCartItmCount.setText(String.valueOf(snapshot.getChildrenCount()));
//                    binding.totalCartItmCount.setVisibility(View.VISIBLE);
//                } else {
//                    binding.totalCartItmCount.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {}
//        });
//    }


    @Override
    public void onPause() {
        super.onPause();
        sliderHandle.removeCallbacks(sliderRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        sliderHandle.postDelayed(sliderRunnable, 2000);
    }
}
package com.szniloycoder.sneakersapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseUser;
import com.szniloycoder.sneakersapp.Fragments.ExploreFragment;
import com.szniloycoder.sneakersapp.Fragments.FavouriteFragment;
import com.szniloycoder.sneakersapp.Fragments.HomeFragment;
import com.szniloycoder.sneakersapp.Fragments.ProfileFragment;
import com.szniloycoder.sneakersapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    HomeFragment homeFragment;
    ExploreFragment exploreFragment;
    FavouriteFragment favouriteFragment;
    ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(getResources().getColor(R.color.activityColor));

        // Initialize fragments
        homeFragment = new HomeFragment();
        favouriteFragment = new FavouriteFragment();
        exploreFragment = new ExploreFragment();
        profileFragment = new ProfileFragment();

        // Check if user is logged in
//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (firebaseUser == null) {
//            startActivity(new Intent(this, LoginActivity.class));
//            finish();
//            return;
//        }


        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.homeFragment) {
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, homeFragment).commit();
            }
            if (item.getItemId() == R.id.exploreFragment) {
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, exploreFragment).commit();
            }
            if (item.getItemId() == R.id.favouriteFragment) {
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, favouriteFragment).commit();
            }
            if (item.getItemId() != R.id.profileFragment) {
                return true;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, profileFragment).commit();
            return true;
        });

        // Set default fragment
        binding.bottomNavigation.setSelectedItemId(R.id.homeFragment);
    }
}
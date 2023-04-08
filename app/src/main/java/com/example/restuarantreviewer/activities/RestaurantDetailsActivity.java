package com.example.restuarantreviewer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restuarantreviewer.R;
import com.example.restuarantreviewer.Restaurant;

public class RestaurantDetailsActivity extends AppCompatActivity {
    TextView nameTextView, cuisineTextView, priceRangeTextView, addressTextView, ratingTextView, reviewTextView;
    ImageView restaurantImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        // Get the restaurant object from the intent
        Intent intent = getIntent();
        Restaurant restaurant = intent.getParcelableExtra("restaurant");

        // Initialize the views
        nameTextView = findViewById(R.id.restaurant_name);
        cuisineTextView = findViewById(R.id.restaurant_cuisine);
        priceRangeTextView = findViewById(R.id.restaurant_price_range);
        addressTextView = findViewById(R.id.restaurant_address);
        ratingTextView = findViewById(R.id.restaurant_rating);
        reviewTextView = findViewById(R.id.restaurant_review);
        restaurantImageView = findViewById(R.id.restaurant_image);

        // Set the restaurant details
        nameTextView.setText(restaurant.getName());
        cuisineTextView.setText(restaurant.getCuisine());
        priceRangeTextView.setText(restaurant.getPrice());
    }
}


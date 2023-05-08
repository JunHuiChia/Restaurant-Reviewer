package com.example.restuarantreviewer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restuarantreviewer.R;
import com.example.restuarantreviewer.database.entity.Restaurants;

import java.util.Arrays;

public class RestaurantDetailsActivity extends AppCompatActivity {
    EditText nameEditText, descriptionEditText;
    Spinner cuisineSpinner, priceRangeSpinner, ratingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        Intent intent = getIntent();
        Restaurants restaurant = (Restaurants) intent.getSerializableExtra("Restaurant");

        // Initialize the views
        nameEditText = findViewById(R.id.et_details_restaurant_name);
        priceRangeSpinner = findViewById(R.id.spinner_details_restaurant_price_range);
        ratingSpinner = findViewById(R.id.spinner_details_restaurant_rating);
        cuisineSpinner = findViewById(R.id.spinner_details_restaurant_cuisine);
        descriptionEditText = findViewById(R.id.et_details_restaurant_description);

        nameEditText.setText(restaurant.name);
        priceRangeSpinner.setSelection(itemIndex(R.array.spinner_price_range, restaurant.priceRange));
        ratingSpinner.setSelection(itemIndex(R.array.spinner_rating, MainActivity.formatRating(restaurant)));
        cuisineSpinner.setSelection(itemIndex(R.array.spinner_cuisine, restaurant.cuisine));
        descriptionEditText.setText(restaurant.description);
    }

    private int itemIndex(int array_id, String targetItem) {
        String[] itemArray = getResources().getStringArray(array_id);
        Arrays.sort(itemArray);
        int test = Arrays.binarySearch(itemArray, targetItem);
        return Arrays.binarySearch(itemArray, targetItem);
    }
}


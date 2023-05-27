package com.example.restuarantreviewer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restuarantreviewer.R;
import com.example.restuarantreviewer.database.entity.Restaurants;

import java.util.Arrays;

public class RestaurantDetailsActivity extends AppCompatActivity {
    EditText nameEditText, descriptionEditText;
    Spinner cuisineSpinner, priceRangeSpinner, ratingSpinner;
    Button saveButton, deleteButton;
    Restaurants newRestaurant = new Restaurants();
    Restaurants oldRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        Intent intent = getIntent();
        oldRestaurant = (Restaurants) intent.getSerializableExtra("Restaurant");

        // Initialize the views
        nameEditText = findViewById(R.id.et_details_restaurant_name);
        priceRangeSpinner = findViewById(R.id.spinner_details_restaurant_price_range);
        ratingSpinner = findViewById(R.id.spinner_details_restaurant_rating);
        cuisineSpinner = findViewById(R.id.spinner_details_restaurant_cuisine);
        descriptionEditText = findViewById(R.id.et_details_restaurant_description);

        saveButton = findViewById(R.id.btn_save_restaurant);
        deleteButton = findViewById(R.id.btn_delete_restaurant);

        nameEditText.setText(oldRestaurant.name);
        priceRangeSpinner.setSelection(itemIndex(R.array.spinner_price_range, oldRestaurant.priceRange));
        ratingSpinner.setSelection(itemIndex(R.array.spinner_rating, MainActivity.formatRating(oldRestaurant)));
        cuisineSpinner.setSelection(itemIndex(R.array.spinner_cuisine, oldRestaurant.cuisine));
        descriptionEditText.setText(oldRestaurant.description);

        cuisineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                newRestaurant.cuisine = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        priceRangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                newRestaurant.priceRange = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ratingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int rating = adapterView.getSelectedItemPosition();
                if (rating == 0) {
                    newRestaurant.rating = -1;
                    return;
                }
                newRestaurant.rating = rating;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        saveButton.setOnClickListener(view -> onSaveButtonClick());
        deleteButton.setOnClickListener(view -> onDeleteButtonClick());
    }

    private int itemIndex(int array_id, String targetItem) {
        String[] itemArray = getResources().getStringArray(array_id);
        Arrays.sort(itemArray);
        return Arrays.binarySearch(itemArray, targetItem);
    }

    private void onSaveButtonClick() {
        getUpdatedDetails();
        try {
            MainActivity.restaurantDao.updateRestaurants(newRestaurant);
        } catch (Exception e){
            return;
        }
        Intent intent = new Intent(RestaurantDetailsActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void onDeleteButtonClick() {
        MainActivity.restaurantDao.delete(oldRestaurant);
        Intent intent = new Intent(RestaurantDetailsActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void getUpdatedDetails() {
        newRestaurant.name = nameEditText.getText().toString();
        newRestaurant.description = descriptionEditText.getText().toString();
        newRestaurant.uid = oldRestaurant.uid;
    }
}


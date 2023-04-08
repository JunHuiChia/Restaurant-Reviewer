package com.example.restuarantreviewer.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restuarantreviewer.R;
import com.example.restuarantreviewer.database.entity.Restaurants;

public class AddRestaurantActivity extends AppCompatActivity {

    EditText nameEditText;
    Spinner cuisineSpinner, priceRangeSpinner, ratingSpinner;
    Button addButton;

    private Restaurants restaurant = new Restaurants();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        nameEditText = findViewById(R.id.et_restaurant_name);
        cuisineSpinner = findViewById(R.id.spinner_restaurant_cuisine);
        priceRangeSpinner = findViewById(R.id.spinner_restaurant_price_range);
        ratingSpinner = findViewById(R.id.spinner_restaurant_rating);
        addButton = findViewById(R.id.btn_add_new_restaurant);

        restaurant.name = nameEditText.getText().toString();
        cuisineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                restaurant.cuisine = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        priceRangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                restaurant.priceRange = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ratingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                restaurant.rating = adapterView.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        addButton.setOnClickListener(view -> saveRestaurant());
    }

    public void saveRestaurant() {
        if (restaurant.name.isEmpty()) {
            Toast.makeText(AddRestaurantActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        MainActivity.restaurantDao.insert(restaurant);
    }
}

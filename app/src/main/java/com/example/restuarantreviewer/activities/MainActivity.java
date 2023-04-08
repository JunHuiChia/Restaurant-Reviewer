package com.example.restuarantreviewer.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.restuarantreviewer.R;
import com.example.restuarantreviewer.Restaurant;
import com.example.restuarantreviewer.database.AppDatabase;
import com.example.restuarantreviewer.database.dao.RestuarantDao;
import com.example.restuarantreviewer.database.entity.Restuarants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName, editTextCuisine, editTextPrice;
    private Button addRestaurantBtn;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<Restaurant>> listDataChild;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
            AppDatabase.class, "app-db").allowMainThreadQueries().build();

        RestuarantDao restuarantDao = db.restuarantDao();
        List<Restuarants> restuarants = restuarantDao.getAll();

        // Initialize views
        addRestaurantBtn = findViewById(R.id.btn_add_restaurant);
        expandableListView = findViewById(R.id.expandableListView);

        // Initialize data
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        sharedPreferences = getSharedPreferences("RestaurantData", MODE_PRIVATE);
        loadData();

        // Initialize adapter
        expandableListAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expandableListView.setAdapter(expandableListAdapter);


        addRestaurantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddRestaurantActivity.class);
                startActivity(intent);
            }
        });
        // Set listener for Add button
//        buttonAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String name = editTextName.getText().toString();
//                String cuisine = editTextCuisine.getText().toString();
//                String price = editTextPrice.getText().toString();
//
//                // Check if any field is empty
//                if (name.isEmpty() || cuisine.isEmpty() || price.isEmpty()) {
//                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                // Add item to data
//                Restaurant restaurant = new Restaurant(name, cuisine, price);
//                if (listDataHeader.contains(cuisine)) {
//                    int groupPosition = listDataHeader.indexOf(cuisine);
//                    List<Restaurant> restaurants = listDataChild.get(listDataHeader.get(groupPosition));
//                    restaurants.add(restaurant);
//
//                } else {
//                    listDataHeader.add(cuisine);
//                    List<Restaurant> restaurants = new ArrayList<>();
//                    restaurants.add(restaurant);
//                    listDataChild.put(cuisine, restaurants);
//                }
//
//                // Sort data by cuisine
//                Collections.sort(listDataHeader);
//
//                // Notify adapter
//                expandableListAdapter.notifyDataSetChanged();
//
//                saveData();
//                loadData();
//                // Clear fields
//                editTextName.setText("");
//                editTextCuisine.setText("");
//                editTextPrice.setText("");
//            }
//        });
    }
    private void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String headerJson = gson.toJson(listDataHeader);
        String childJson = gson.toJson(listDataChild);
        editor.putString("header", headerJson);
        editor.putString("child", childJson);
        editor.apply();
    }

    private void loadData() {
        Gson gson = new Gson();
        String headerJson = sharedPreferences.getString("header", null);
        String childJson = sharedPreferences.getString("child", null);

        if (headerJson != null && childJson != null) {
            Type headerType = new TypeToken<List<String>>() {}.getType();
            Type childType = new TypeToken<HashMap<String, List<Restaurant>>>() {}.getType();

            listDataHeader = gson.fromJson(headerJson, headerType);
            listDataChild = gson.fromJson(childJson, childType);
        }
    }
    private class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Context context;
        private List<String> listDataHeader;
        private HashMap<String, List<Restaurant>> listDataChild;

        public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<Restaurant>> listDataChild) {
            this.context = context;
            this.listDataHeader = listDataHeader;
            this.listDataChild = listDataChild;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return this.listDataChild.get(this.listDataHeader.get(groupPosition)).get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_item_child, null);
            }

            Restaurant restaurant = (Restaurant) getChild(groupPosition, childPosition);

            TextView textViewName = convertView.findViewById(R.id.textViewName);
            TextView textViewPrice = convertView.findViewById(R.id.textViewPrice);

            textViewName.setText(restaurant.getName());
            textViewPrice.setText(restaurant.getPrice());

            textViewName.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listDataChild.get(restaurant.getCuisine()).remove(restaurant);
                    //deleteGroup();
                    saveData();
                    return true;
                }
            });

            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this.listDataChild.get(this.listDataHeader.get(groupPosition)).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this.listDataHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return this.listDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_item_header, null);
            }

            String cuisine = (String) getGroup(groupPosition);

            TextView textViewCuisine = convertView.findViewById(R.id.textViewCuisine);
            textViewCuisine.setText(cuisine);

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }

}





package com.example.restuarantreviewer.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.restuarantreviewer.R;
import com.example.restuarantreviewer.database.AppDatabase;
import com.example.restuarantreviewer.database.dao.RestaurantDao;
import com.example.restuarantreviewer.database.entity.Restaurants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<Restaurants>> listDataChild;
    public static RestaurantDao restaurantDao;
    public List<Restaurants> restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
            AppDatabase.class, "app-db").allowMainThreadQueries().build();

        restaurantDao = db.restaurantDao();

        // Initialize views
        Button addRestaurantBtn = findViewById(R.id.btn_add_restaurant);
        expandableListView = findViewById(R.id.expandableListView);
        Button expandAllBtn = findViewById(R.id.btn_expand_all);
        Button collapseAllBtn = findViewById(R.id.btn_collapse_all);

        // Initialize data
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        // Initialize adapter
        expandableListAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        addRestaurantBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddRestaurantActivity.class);
            startActivity(intent);
        });

        expandAllBtn.setOnClickListener(view -> expandAllGroups());
        collapseAllBtn.setOnClickListener(view -> collapseAllGroups());

    }

    @Override
    public void onResume() {
        super.onResume();

        loadData();
        expandableListView.setAdapter(expandableListAdapter);
    }

    private void loadData() {
        restaurants = restaurantDao.getAll();

        for (Restaurants restaurant: restaurants) {
            String restaurantCuisine = restaurant.cuisine;
            if (listDataHeader.contains(restaurantCuisine)) {
                boolean exists = false;
                List<Restaurants> restaurantsList = listDataChild.get(restaurantCuisine);
                for (Restaurants childRestaurant : restaurantsList) {
                    if (childRestaurant.uid == restaurant.uid) {
                        exists = true;
                    }
                }
                if (exists) continue;
                restaurantsList.add(restaurant);
            }
            else {
                listDataHeader.add(restaurantCuisine);
                List<Restaurants> restaurantsList = new ArrayList<>();
                restaurantsList.add(restaurant);
                listDataChild.put(restaurantCuisine, restaurantsList);
            }

        }
    }
    private class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Context context;
        private List<String> listDataHeader;
        private HashMap<String, List<Restaurants>> listDataChild;

        public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<Restaurants>> listDataChild) {
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
                convertView = inflater.inflate(R.layout.list_child, null);
            }

            Restaurants restaurant = (Restaurants) getChild(groupPosition, childPosition);

            TextView textViewName = convertView.findViewById(R.id.tv_list_child_name);
            TextView textViewPrice = convertView.findViewById(R.id.tv_list_child_price);
            TextView textViewRating = convertView.findViewById(R.id.tv_list_child_rating);

            textViewName.setText(restaurant.name);
            textViewPrice.setText(restaurant.priceRange);
            String rating = formatRating(restaurant);
            textViewRating.setText(rating);

            convertView.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this, RestaurantDetailsActivity.class);
                intent.putExtra("Restaurant", (Serializable) restaurant);
                startActivity(intent);
                finish();
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
                convertView = inflater.inflate(R.layout.list_header, null);
            }

            String cuisine = (String) getGroup(groupPosition);

            TextView textViewCuisine = convertView.findViewById(R.id.tv_cuisine);
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
    private void expandAllGroups() {
        int groupCount = expandableListAdapter.getGroupCount();
        for (int i = 0; i < groupCount; i++) {
            expandableListView.expandGroup(i);
        }
    }

    private void collapseAllGroups() {
        int groupCount = expandableListAdapter.getGroupCount();
        for (int i = 0; i < groupCount; i++) {
            expandableListView.collapseGroup(i);
        }
    }

    public static String formatRating(Restaurants restaurant) {
        String rating = String.valueOf(restaurant.rating);
        switch (restaurant.rating) {
            case -1:rating = "Unrated"; break;
            case 1: rating = "★★★★★"; break;
            case 2: rating = "★★★★☆"; break;
            case 3: rating = "★★★☆☆"; break;
            case 4: rating = "★★☆☆☆"; break;
            case 5: rating = "★☆☆☆☆"; break;
        }
        return rating;
    }
}





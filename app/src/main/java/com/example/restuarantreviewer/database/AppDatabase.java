package com.example.restuarantreviewer.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.restuarantreviewer.database.dao.RestaurantDao;
import com.example.restuarantreviewer.database.entity.Restaurants;

@Database(entities = {Restaurants.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RestaurantDao restaurantDao();
}

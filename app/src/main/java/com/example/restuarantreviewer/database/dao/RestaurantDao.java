package com.example.restuarantreviewer.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Update;

import com.example.restuarantreviewer.database.entity.Restaurants;

import java.util.List;

@Dao
public interface RestaurantDao {
    @Query("SELECT * FROM Restaurants")
    List<Restaurants> getAll();

    @Query("SELECT * FROM Restaurants WHERE cuisine LIKE :cuisine")
    List<Restaurants> getAllByCuisine(String cuisine);

    @Delete
    void delete(Restaurants restaurant);

    @Update
    void updateRestaurants(Restaurants... restaurants);
}

package com.example.restuarantreviewer.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Update;

import com.example.restuarantreviewer.database.entity.Restuarants;

import java.util.List;

@Dao
public interface RestuarantDao {
    @Query("SELECT * FROM restuarants")
    List<Restuarants> getAll();

    @Query("SELECT * FROM restuarants WHERE cuisine LIKE :cuisine")
    List<Restuarants> getAllByaCuisine(String cuisine);

    @Delete
    void delete(Restuarants restuarant);

    @Update
    void updateRestuarants(Restuarants... restuarants);
}

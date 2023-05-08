package com.example.restuarantreviewer.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Restaurants implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "restaurant_name")
    public String name;

    @ColumnInfo(name = "price_range")
    public String priceRange;

    @ColumnInfo(name = "rating")
    public int rating;

    @ColumnInfo(name = "cuisine")
    public String cuisine;

    @ColumnInfo(name = "description")
    public String description;
}


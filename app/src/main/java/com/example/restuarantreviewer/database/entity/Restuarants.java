package com.example.restuarantreviewer.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Restuarants {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "restuarant_name")
    public String name;

    @ColumnInfo(name = "price_range")
    public String priceRange;

    @ColumnInfo(name = "rating")
    public int rating;

    @ColumnInfo(name = "cuisine")
    public String cuisine;
}

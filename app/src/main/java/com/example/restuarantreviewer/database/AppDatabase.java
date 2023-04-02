package com.example.restuarantreviewer.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.restuarantreviewer.database.dao.RestuarantDao;
import com.example.restuarantreviewer.database.entity.Restuarants;

@Database(entities = {Restuarants.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RestuarantDao restuarantDao();
}

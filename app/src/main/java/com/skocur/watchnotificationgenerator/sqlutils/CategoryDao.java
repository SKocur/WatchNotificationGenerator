package com.skocur.watchnotificationgenerator.sqlutils;

import com.skocur.watchnotificationgenerator.models.Category;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface CategoryDao {

    @Query("SELECT * FROM category")
    List<Category> getAllCategories();

    @Insert
    void insertAll(Category... categories);

    @Delete
    void delete(Category category);
}

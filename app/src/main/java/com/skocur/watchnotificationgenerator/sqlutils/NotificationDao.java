package com.skocur.watchnotificationgenerator.sqlutils;

import com.skocur.watchnotificationgenerator.models.Notification;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface NotificationDao {

    @Query("SELECT * FROM notification")
    List<Notification> getAll();

    @Query("SELECT * FROM notification " +
            "WHERE notification.category LIKE :category")
    List<Notification> getAllFromCategory(String category);

    @Insert
    void insertAll(Notification... notifications);

    @Delete
    void delete(Notification notification);
}

package com.skocur.watchnotificationgenerator.sqlutils;

import com.skocur.watchnotificationgenerator.models.Category;
import com.skocur.watchnotificationgenerator.models.Notification;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Notification.class, Category.class}, version = 1)
public abstract class MainDatabase extends RoomDatabase {

    public abstract NotificationDao notificationDao();

    public abstract CategoryDao categoryDao();
}

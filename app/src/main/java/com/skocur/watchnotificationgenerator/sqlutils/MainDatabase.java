package com.skocur.watchnotificationgenerator.sqlutils;

import com.skocur.watchnotificationgenerator.models.Notification;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Notification.class}, version = 2)
public abstract class MainDatabase extends RoomDatabase {

    public abstract NotificationDao notificationDao();
}

package com.skocur.watchnotificationgenerator.sqlutils;

import androidx.room.RoomDatabase;

public abstract class MainDatabase extends RoomDatabase {

    public abstract NotificationDao notificationDao();
}

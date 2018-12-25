package com.skocur.watchnotificationgenerator.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Notification {

    @PrimaryKey
    public int uid;

    @ColumnInfo(name="notification_title")
    public String notificationTitle;

    @ColumnInfo(name = "notification_content")
    public String notificationContent;

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationContent() {
        return notificationContent;
    }

    public void setNotificationContent(String notificationContent) {
        this.notificationContent = notificationContent;
    }
}

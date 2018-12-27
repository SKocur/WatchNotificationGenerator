package com.skocur.watchnotificationgenerator.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Notification {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name="notification_title")
    public String notificationTitle;

    @ColumnInfo(name = "notification_content")
    public String notificationContent;

    @ColumnInfo(name = "category_uid")
    public int categoryUid;

    public int getCategoryUid() {
        return categoryUid;
    }

    public void setCategoryUid(int categoryUid) {
        this.categoryUid = categoryUid;
    }

    //@Embedded
    //public Category category;

    /*public Category getCategory() {
        return category;
    }*/

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

    /*public void setCategory(Category category) {
        this.category = category;
    }*/

    @NonNull
    @Override
    public String toString() {
        return getCategoryUid() + " "
                + getNotificationTitle() + " "
                + getNotificationContent();
    }
}

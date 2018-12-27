package com.skocur.watchnotificationgenerator.sqlutils;

import android.content.Context;
import android.os.AsyncTask;

import com.skocur.watchnotificationgenerator.models.Category;
import com.skocur.watchnotificationgenerator.models.Notification;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.room.Room;

public class DatabaseService {

    private MainDatabase db;

    public DatabaseService(Context context){
        db = Room.databaseBuilder(context,
                MainDatabase.class, "watch-notifications-db").build();
    }

    public void addNotification(Notification notification) {
        new NotificationInserterAsyncTask().execute(notification);
    }

    public List<Notification> getAllNotifications() throws InterruptedException, ExecutionException {
        return new NotificationDownloaderAsyncTask().execute().get();
    }

    public List<Notification> getAllNotificationsFromCategory(String category) throws InterruptedException, ExecutionException {
        return new NotificationCategoryDownloaderAsyncTask().execute(category).get();
    }

    public void addCategory(Category category) {
        new CategoryInserterAsyncTask().execute(category);
    }

    public List<Category> getAllCategories() throws InterruptedException, ExecutionException {
        return new CategoryDownloaderAsyncTask().execute().get();
    }

    private class NotificationInserterAsyncTask extends AsyncTask<Notification, Void, Void> {

        @Override
        protected Void doInBackground(Notification... notifications) {
            db.notificationDao().insertAll(notifications);
            return null;
        }
    }

    private class NotificationCategoryDownloaderAsyncTask extends AsyncTask<String, Void, List<Notification>> {

        @Override
        protected List<Notification> doInBackground(String... data) {
            return db.notificationDao().getAllFromCategory(data[0]);
        }
    }

    private class NotificationDownloaderAsyncTask extends AsyncTask<Void, Void, List<Notification>> {

        @Override
        protected List<Notification> doInBackground(Void... url) {
            return db.notificationDao().getAll();
        }
    }

    private class CategoryInserterAsyncTask extends AsyncTask<Category, Void, Void> {

        @Override
        protected Void doInBackground(Category... categories) {
            db.categoryDao().insertAll(categories);
            return null;
        }
    }

    private class CategoryDownloaderAsyncTask extends AsyncTask<Void, Void, List<Category>> {

        @Override
        protected List<Category> doInBackground(Void... url) {
            return db.categoryDao().getAllCategories();
        }
    }
}

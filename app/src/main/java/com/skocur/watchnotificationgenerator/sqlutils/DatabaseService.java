package com.skocur.watchnotificationgenerator.sqlutils;

import android.content.Context;
import android.os.AsyncTask;

import com.skocur.watchnotificationgenerator.models.Notification;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.room.Room;

public class DatabaseService {

    private MainDatabase db ;

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

    private class NotificationInserterAsyncTask extends AsyncTask<Notification, Void, Void> {

        @Override
        protected Void doInBackground(Notification... notifications) {
            db.notificationDao().insertAll(notifications);
            return null;
        }
    }


    private class NotificationDownloaderAsyncTask extends AsyncTask<Void, Void, List<Notification>> {

        @Override
        protected List<Notification> doInBackground(Void... url) {
            return db.notificationDao().getAll();
        }
    }
}

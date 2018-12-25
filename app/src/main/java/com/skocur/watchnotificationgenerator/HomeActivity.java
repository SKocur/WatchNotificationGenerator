package com.skocur.watchnotificationgenerator;

import android.os.Bundle;
import android.util.Log;

import com.skocur.watchnotificationgenerator.models.Notification;
import com.skocur.watchnotificationgenerator.sqlutils.MainDatabase;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class HomeActivity extends AppCompatActivity {

    public MainDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = Room.databaseBuilder(getApplicationContext(),
                MainDatabase.class, "watch-notifications-db").build();

        generateRandomDataAndInsertToDatabase();

        generateNotificationsFromEverything();

        db.close();
    }

    private void generateNotificationsFromEverything() {

        List<Notification> notifications = db.notificationDao().getAll();
        for (Notification notification : notifications) {
            Log.i("notification", notification.toString());
        }
    }

    private void generateRandomDataAndInsertToDatabase() {
        Notification notification = new Notification();

        for (int i = 0; i < 10; ++i) {
            notification.setCategory("general");
            notification.setNotificationTitle("General notification");
            notification.setNotificationContent("TEST " + (i * Math.random() * 100));

            db.notificationDao().insertAll(notification);
        }
    }
}

package com.skocur.watchnotificationgenerator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.skocur.watchnotificationgenerator.models.Notification;
import com.skocur.watchnotificationgenerator.sqlutils.MainDatabase;

import java.util.List;

import androidx.room.Room;

public class HomeActivity extends AppCompatActivity {

    public MainDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = Room.databaseBuilder(getApplicationContext(),
                MainDatabase.class, "watch-notifications-db").build();

        generateRandomDataAndInsertToDabase();

        db.close();
    }

    private void generateNotificationsFromEverything() {

        List<Notification> notifications = db.notificationDao().getAll();
    }

    private void generateRandomDataAndInsertToDabase() {
        Notification notification = new Notification();

        for (int i = 0; i < 10; ++i) {
            notification.setCategory("general");
            notification.setNotificationTitle("General notification");
            notification.setNotificationContent("TEST " + (i * Math.random() * 100));

            db.notificationDao().insertAll(notification);
        }
    }
}

package com.skocur.watchnotificationgenerator;

import android.os.Bundle;
import android.util.Log;

import com.skocur.watchnotificationgenerator.models.Notification;
import com.skocur.watchnotificationgenerator.sqlutils.DatabaseService;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private DatabaseService databaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        databaseService = new DatabaseService(getApplicationContext());

        generateRandomDataAndInsertToDatabase();
        //generateNotificationsFromEverything();
    }

    private void generateNotificationsFromEverything() {

        try {
            List<Notification> notifications = databaseService.getAllNotifications();
            for (Notification notification : notifications) {
                try {
                    Log.i("notification", notification.toString());
                } catch (NullPointerException e) {
                    Log.e("!", e.toString());
                }
            }
        } catch (InterruptedException e) {
            Log.e("!", e.toString());
        } catch (ExecutionException e) {
            Log.e("!", e.toString());
        }
    }

    private void generateRandomDataAndInsertToDatabase() {
        Notification notification = new Notification();

        for (int i = 0; i < 10; ++i) {
            notification.setCategory("general");
            notification.setNotificationTitle("General notification");
            notification.setNotificationContent("TEST " + (i * Math.random() * 100));

            databaseService.addNotification(notification);
        }
    }
}

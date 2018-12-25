package com.skocur.watchnotificationgenerator;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.skocur.watchnotificationgenerator.models.Notification;
import com.skocur.watchnotificationgenerator.sqlutils.DatabaseService;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class HomeActivity extends AppCompatActivity {

    private static final String NOTIFICATIONS_CHANNEL = "s0x";
    private static int NOTIFICATIONS_COUNTER = 0;
    private DatabaseService databaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();
        setContentView(R.layout.activity_home);

        databaseService = new DatabaseService(getApplicationContext());

        //generateRandomDataAndInsertToDatabase();
        generateNotificationsFromEverything();
    }

    private void generateNotificationsFromEverything() {
        try {
            List<Notification> notifications = databaseService.getAllNotifications();
            for (Notification notification : notifications) {
                //Thread.sleep(1000);
                displayNotification(notification);
                Log.e("notification", notification.toString());
            }
        } catch (InterruptedException e) {
            Log.e("!", e.toString());
        } catch (ExecutionException e) {
            Log.e("!", e.toString());
        } catch (NullPointerException e) {
            Log.e("!", e.toString());
        }
    }

    /**
     * IMPORTANT NOTE
     * User has to add this app to allowed applications on watch settings.
     *
     * @param notification
     */
    private void displayNotification(Notification notification) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, NOTIFICATIONS_CHANNEL)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(notification.getNotificationTitle())
                .setContentText(notification.notificationContent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .extend(new NotificationCompat.WearableExtender().setHintShowBackgroundOnly(true));

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATIONS_COUNTER++, mBuilder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = NOTIFICATIONS_CHANNEL;
            String description = "Notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NOTIFICATIONS_CHANNEL, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
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

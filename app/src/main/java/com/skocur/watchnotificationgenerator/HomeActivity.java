package com.skocur.watchnotificationgenerator;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.skocur.watchnotificationgenerator.models.Category;
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
        initiateViews();

        databaseService = new DatabaseService(getApplicationContext());

        //generateRandomDataAndInsertToDatabase();
        generateNotificationsFromEverything();
    }

    private void initiateViews() {
        findViewById(R.id.fab_add_category).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("Add category");

                final EditText input = new EditText(HomeActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);

                builder.setView(input);

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // TODO: Insert category to database
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.show();
            }
        });
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
     * User has to add this app to "allowed applications' on watch settings.
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
        Category category = new Category();

        for (int i = 0; i < 10; ++i) {
            category.setCategoryName("general");

            notification.setCategory(category);
            notification.setNotificationTitle("General notification");
            notification.setNotificationContent("TEST " + (i * Math.random() * 100));

            databaseService.addNotification(notification);
        }
    }
}

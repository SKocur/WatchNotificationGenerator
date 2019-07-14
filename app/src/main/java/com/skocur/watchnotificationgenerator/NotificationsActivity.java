package com.skocur.watchnotificationgenerator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.skocur.watchnotificationgenerator.models.Notification;
import com.skocur.watchnotificationgenerator.utils.CustomAddAlertDialog;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class NotificationsActivity extends AppCompatActivity {

    private static final String NOTIFICATIONS_CHANNEL = "s0x";
    private static int NOTIFICATIONS_COUNTER = 0;

    private String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        createNotificationChannel();

        Intent intent = getIntent();
        categoryName = intent.getStringExtra("category_name");

        initiateViews();

        try {
            NotificationsListAdapter notificationsListAdapter = new NotificationsListAdapter(
                    HomeActivity.databaseService.getAllNotificationsFromCategory(categoryName)
            );

            RecyclerView recyclerView = findViewById(R.id.notifications_recycler_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(notificationsListAdapter);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    class NotificationsListAdapter extends RecyclerView.Adapter<NotificationViewHolder> {

        List<Notification> mNotifications;

        NotificationsListAdapter(List<Notification> notifications) {
            this.mNotifications = notifications;
        }

        @NonNull
        @Override
        public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View layoutInflater = LayoutInflater.from(NotificationsActivity.this.getApplicationContext())
                    .inflate(R.layout.item_general, parent, false);
            return new NotificationViewHolder((ViewGroup) layoutInflater.findViewById(R.id.item_general_container));
        }

        @Override
        public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
            Notification notification = mNotifications.get(position);
            holder.setItemTitle(notification.getNotificationTitle());

            holder.setIconListener(notification);
        }

        @Override
        public int getItemCount() {
            return mNotifications.size();
        }
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder {

        ViewGroup mViewGroup;
        TextView mItemTitle;
        ImageView mNewNotification;

        public NotificationViewHolder(@NonNull ViewGroup container) {
            super(container);

            mViewGroup = container;
            mItemTitle = mViewGroup.findViewById(R.id.item_general_name);
            mNewNotification = mViewGroup.findViewById(R.id.item_general_create_notification);
            mNewNotification.setVisibility(View.VISIBLE);
        }

        void setIconListener(final Notification notification) {
            mNewNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    displayNotification(notification);
                }
            });
        }

        void setItemTitle(String title) {
            mItemTitle.setText(title);
        }

        /**
         * IMPORTANT NOTE
         * User has to add this app to "allowed applications' on watch settings.
         *
         * @param notification
         */
        private void displayNotification(Notification notification) {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(NotificationsActivity.this.getApplicationContext(), NOTIFICATIONS_CHANNEL)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle(notification.getNotificationTitle())
                    .setContentText(notification.notificationContent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .extend(new NotificationCompat.WearableExtender().setHintShowBackgroundOnly(true));

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(NotificationsActivity.this.getApplicationContext());
            notificationManager.notify(NOTIFICATIONS_COUNTER++, mBuilder.build());
        }
    }

    private void initiateViews() {
        findViewById(R.id.activity_notifications_fab_add_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomAddAlertDialog customAddAlertDialog = new CustomAddAlertDialog();
                customAddAlertDialog.alertFor(NotificationsActivity.this)
                        .setTitle("Add Notification")
                        .setPositiveButton(new CustomAddAlertDialog.InputReadyListener() {
                            @Override
                            public void onClick(EditText input) {
                                Intent intent = new Intent(NotificationsActivity.this, NewNotificationActivity.class);
                                intent.putExtra("notification_name", input.getText().toString());
                                intent.putExtra("category_name", categoryName);

                                startActivity(intent);
                            }
                        }).build();
            }
        });
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
            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}

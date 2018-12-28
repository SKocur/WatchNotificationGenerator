package com.skocur.watchnotificationgenerator.adapters;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.skocur.watchnotificationgenerator.R;
import com.skocur.watchnotificationgenerator.models.Notification;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationsAdapter extends ArrayAdapter<Notification> {

    private static final String NOTIFICATIONS_CHANNEL = "s0x";
    private static int NOTIFICATIONS_COUNTER = 0;

    public NotificationsAdapter(Context context, ArrayList<Notification> notifications) {
        super(context, 0, notifications);

        createNotificationChannel();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Notification notification = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_general, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.item_general_name);

        try {
            tvName.setText(notification.getNotificationTitle());
        } catch (NullPointerException e) {
            Log.e("!", e.toString());
        }

        convertView.findViewById(R.id.item_general_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Opening new activity with notification details
            }
        });

        ImageView ivCreateNotification = convertView.findViewById(R.id.item_general_create_notification);
        ivCreateNotification.setVisibility(View.VISIBLE);
        ivCreateNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayNotification(notification);
            }
        });

        return convertView;
    }

    /**
     * IMPORTANT NOTE
     * User has to add this app to "allowed applications' on watch settings.
     *
     * @param notification
     */
    private void displayNotification(Notification notification) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), NOTIFICATIONS_CHANNEL)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(notification.getNotificationTitle())
                .setContentText(notification.notificationContent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .extend(new NotificationCompat.WearableExtender().setHintShowBackgroundOnly(true));

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
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
            NotificationManager notificationManager = getContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}

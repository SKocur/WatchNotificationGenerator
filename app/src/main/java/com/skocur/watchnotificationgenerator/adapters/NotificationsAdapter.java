package com.skocur.watchnotificationgenerator.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.skocur.watchnotificationgenerator.R;
import com.skocur.watchnotificationgenerator.models.Notification;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NotificationsAdapter extends ArrayAdapter<Notification> {

    public NotificationsAdapter(Context context, ArrayList<Notification> notifications) {
        super(context, 0, notifications);
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

        return convertView;
    }
}

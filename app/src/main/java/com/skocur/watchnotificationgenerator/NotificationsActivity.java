package com.skocur.watchnotificationgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.skocur.watchnotificationgenerator.adapters.NotificationsAdapter;
import com.skocur.watchnotificationgenerator.models.Notification;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class NotificationsActivity extends AppCompatActivity {

    private String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        Intent intent = getIntent();
        categoryName = intent.getStringExtra("category_name");

        initiateViews();
    }

    private void initiateViews() {
        try {
            ArrayList<Notification> notifications = (ArrayList<Notification>) HomeActivity.databaseService.getAllNotificationsFromCategory(categoryName);
            NotificationsAdapter adapter = new NotificationsAdapter(this, notifications);

            ListView notificationsListView = findViewById(R.id.activity_notifications_list);
            notificationsListView.setAdapter(adapter);
        } catch (InterruptedException e) {
            Log.e("!", e.toString());
        } catch (ExecutionException e) {
            Log.e("!", e.toString());
        }

        findViewById(R.id.activity_notifications_fab_add_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}

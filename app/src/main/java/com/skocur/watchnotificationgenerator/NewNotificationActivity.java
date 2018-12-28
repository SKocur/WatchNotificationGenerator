package com.skocur.watchnotificationgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.skocur.watchnotificationgenerator.models.Category;
import com.skocur.watchnotificationgenerator.models.Notification;

import java.util.concurrent.ExecutionException;

public class NewNotificationActivity extends AppCompatActivity {

    private String notificationName;
    private String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_notification);

        notificationName = getIntent().getStringExtra("notification_name");
        categoryName = getIntent().getStringExtra("category_name");

        initiateViews();
    }

    private void initiateViews() {
        EditText etNotificationTitle = findViewById(R.id.activity_new_notification_edit_title);
        etNotificationTitle.setText(notificationName);

        final EditText etNotificationContent = findViewById(R.id.activity_new_notification_edit_content);

        findViewById(R.id.activity_new_notification_button_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notification notification = new Notification();
                notification.setNotificationTitle(notificationName);
                notification.setNotificationContent(etNotificationContent.getText().toString());

                try {
                    Category category = HomeActivity.databaseService.getCategoryForName(categoryName);
                    notification.setCategoryUid(category.getCategoryUid());

                    HomeActivity.databaseService.addNotification(notification);

                    finish();
                } catch (InterruptedException e) {
                    Log.e("!", e.toString());
                } catch (ExecutionException e) {
                    Log.e("!", e.toString());
                }
            }
        });
    }
}

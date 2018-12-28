package com.skocur.watchnotificationgenerator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.skocur.watchnotificationgenerator.adapters.CategoriesAdapter;
import com.skocur.watchnotificationgenerator.models.Category;
import com.skocur.watchnotificationgenerator.sqlutils.DatabaseService;
import com.skocur.watchnotificationgenerator.utils.CustomAddAlertDialog;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    public static DatabaseService databaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        databaseService = new DatabaseService(getApplicationContext());

        initiateViews();
    }

    private void initiateViews() {
        try {
            ArrayList<Category> categories = (ArrayList<Category>) databaseService.getAllCategories();
            CategoriesAdapter adapter = new CategoriesAdapter(this, categories);

            ListView categoriesListView = findViewById(R.id.activity_home_list_category);
            categoriesListView.setAdapter(adapter);
        } catch (InterruptedException e) {
            Log.e("!", e.toString());
        } catch (ExecutionException e2) {
            Log.e("!", e2.toString());
        }

        findViewById(R.id.activity_home_fab_add_category).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomAddAlertDialog customAddAlertDialog = new CustomAddAlertDialog();
                customAddAlertDialog.alertFor(HomeActivity.this)
                        .setTitle("Add category")
                        .setPositiveButton(new CustomAddAlertDialog.InputReadyListener() {
                    @Override
                    public void onClick(EditText input) {
                        Category category = new Category();
                        category.setCategoryName(input.getText().toString());

                        databaseService.addCategory(category);
                    }
                }).build();
            }
        });
    }
}

package com.skocur.watchnotificationgenerator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.skocur.watchnotificationgenerator.models.Category;
import com.skocur.watchnotificationgenerator.sqlutils.DatabaseService;
import com.skocur.watchnotificationgenerator.utils.CustomAddAlertDialog;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeActivity extends AppCompatActivity {

    public static DatabaseService databaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        databaseService = new DatabaseService(getApplicationContext());

        initiateViews();

        try {
            List<Category> categories = databaseService.getAllCategories();
            for (Category category : categories) {
                Log.i(">>>>>", category.getCategoryName());
            }
            CategoriesListAdapter categoriesListAdapter = new CategoriesListAdapter(
                    categories
            );

            RecyclerView recyclerView = findViewById(R.id.home_recycler_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(categoriesListAdapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    class CategoriesListAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

        private List<Category> mCategories;

        CategoriesListAdapter(List<Category> categories) {
            mCategories = categories;
        }

        @NonNull
        @Override
        public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(HomeActivity.this.getApplicationContext())
                    .inflate(R.layout.item_general, parent, false);
            return new CategoryViewHolder((ViewGroup) view.findViewById(R.id.item_general_container));
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
            Category category = mCategories.get(position);
            holder.setCategoryTitle(category.categoryName);
            holder.setCategoryListener(category);
        }

        @Override
        public int getItemCount() {
            return mCategories.size();
        }
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        private ViewGroup mContainer;
        private TextView mCategoryTitle;

        CategoryViewHolder(@NonNull ViewGroup itemView) {
            super(itemView);

            mContainer = itemView;
            mCategoryTitle = mContainer.findViewById(R.id.item_general_name);
        }

        void setCategoryListener(final Category category) {
            mContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = HomeActivity.this.getApplicationContext();
                    Intent intent = new Intent(context, NotificationsActivity.class);
                    intent.putExtra("category_name", category.getCategoryName());

                    context.startActivity(intent);
                }
            });
        }

        void setCategoryTitle(String title) {
            mCategoryTitle.setText(title);
        }
    }

    private void initiateViews() {
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

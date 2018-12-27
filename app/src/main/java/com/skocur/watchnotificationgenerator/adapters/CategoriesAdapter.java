package com.skocur.watchnotificationgenerator.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.skocur.watchnotificationgenerator.R;
import com.skocur.watchnotificationgenerator.models.Category;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CategoriesAdapter extends ArrayAdapter<Category> {

    public CategoriesAdapter(Context context, ArrayList<Category> categories){
        super(context, 0, categories);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Category category = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_category, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.item_category_name);

        try {
            tvName.setText(category.getCategoryName());
        } catch (NullPointerException e) {
            Log.e("!", e.toString());
        }

        return convertView;
    }
}

package com.skocur.watchnotificationgenerator.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.skocur.watchnotificationgenerator.HomeActivity;
import com.skocur.watchnotificationgenerator.models.Category;

public class CustomAddAlertDialog {

    private String alertTitle;
    private Context context;
    private DialogInterface.OnClickListener onClickListener;
    private InputReadyListener inputReadyListener;

    public CustomAddAlertDialog(Context context) {
        this.context = context;
    }

    public void setTitle(String alertTitle) {
        this.alertTitle = alertTitle;
    }

    public void setPositiveButton(DialogInterface.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setPositiveButton(InputReadyListener inputReadyListener) {
        this.inputReadyListener = inputReadyListener;
    }

    public void build() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(alertTitle);

        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(30, 5, 30, 5);

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        container.addView(input, params);
        builder.setView(container);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                inputReadyListener.onClick(input);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.show();
    }

    public interface InputReadyListener {

        void onClick(EditText input);
    }
}

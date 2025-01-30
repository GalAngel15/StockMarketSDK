package com.example.stockmarketsdk.utils;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class DialogHelper {

    public interface DialogCallback {
        void onConfirm(String input);
    }

    public static void showInputDialog(Context context, String title, String hint, DialogCallback callback) {
        EditText inputEditText = new EditText(context);
        inputEditText.setHint(hint);

        new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(inputEditText)
                .setPositiveButton("Add", (dialog, which) -> {
                    String input = inputEditText.getText().toString().trim();
                    if (input.isEmpty()) {
                        Toast.makeText(context, "No text entered", Toast.LENGTH_SHORT).show();
                    } else {
                        callback.onConfirm(input);
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    public static void showConfirmationDialog(Context context, String title, String message, DialogCallback callback) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes", (dialog, which) -> callback.onConfirm(null))
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

}
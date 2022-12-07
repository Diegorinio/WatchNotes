package com.example.projektfizyka;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.Timer;
import java.util.function.Function;

public class UserInteractions{
    public static void SendMessage(Context _context, String text){
        Toast.makeText(_context, text, Toast.LENGTH_SHORT).show();
    }


    public static AlertDialog.Builder AlertBuilder(Context _context, String title, String Msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(_context);
        builder.setTitle(title);
        builder.setMessage(Msg);
        return builder;
    }

    public static void hideKeyboard(View view, Activity _activity){
        InputMethodManager inputMethodManager = (InputMethodManager) _activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(_activity.getCurrentFocus().getWindowToken(),0);
        }
    }
}

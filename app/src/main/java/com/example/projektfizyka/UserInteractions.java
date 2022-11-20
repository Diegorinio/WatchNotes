package com.example.projektfizyka;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
}

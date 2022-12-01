package com.example.projektfizyka;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity{
    UserSettings settings;
    NotesFilesPreferences NoteFiles;
    Note Notes;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = new UserSettings(getApplicationContext());
        NoteFiles = new NotesFilesPreferences(getApplicationContext());
        Notes = new Note(MainActivity.this, this.settings, this.NoteFiles);


//        Button NewNoteActivity = (Button) findViewById(R.id.newNoteBtn);
        Button optionsBtn = (Button)findViewById(R.id.optionsBtn);
        Button fetchBtn = (Button)findViewById(R.id.goToFetchBtn);
        FloatingActionButton newFloatBtn = (FloatingActionButton)findViewById(R.id.newNote);

        Log.i("Connection", String.valueOf(isNetworkAvailable(MainActivity.this)));

//        Notification.SetUpNoteNotificationManager();

//
//        NewNoteActivity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                    if(settings.isSimulatedMode()){
//                        startActivity(new Intent(MainActivity.this, watch_simulation_mode.class));
//                    }
//                else{
//                    startActivity((new Intent(MainActivity.this, NotesActivity.class)));
//                }
//            }
//        });

        newFloatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(settings.isSimulatedMode()){
                    startActivity(new Intent(MainActivity.this, watch_simulation_mode.class));
                }
                else{
                    startActivity((new Intent(MainActivity.this, NotesActivity.class)));
                }
            }
        });

        fetchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkAvailable(MainActivity.this)){
                    startActivity(new Intent(MainActivity.this, Scrapper.class));
                }
                else{
                    UserInteractions.SendMessage(getApplicationContext(), "No internet connection");
                }
            }
        });

        optionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity((new Intent(MainActivity.this, options.class)));
            }
        });

//        GenerateNotes();
        Notes.GenerateNotes();

    }

    protected void onStart() {

        super.onStart();
        if(settings.CheckIfPreferencesExists()){
        }
        else{
            startActivity((new Intent(MainActivity.this, options.class)));
        }
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
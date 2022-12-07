package com.example.projektfizyka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity{
    UserSettings settings;
    NotesFilesPreferences NoteFiles;
    NotesGenerator notes;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = new UserSettings(getApplicationContext());
        NoteFiles = new NotesFilesPreferences(getApplicationContext());
        notes = new NotesGenerator(MainActivity.this, this.settings, this.NoteFiles);


        Button optionsBtn = (Button)findViewById(R.id.optionsBtn);
        Button fetchBtn = (Button)findViewById(R.id.goToFetchBtn);
        FloatingActionButton newNoteBtn = (FloatingActionButton)findViewById(R.id.newNote);
        Button manageNotesBtn = (Button)findViewById(R.id.ManageNotesBtn) ;

        Log.i("Connection", String.valueOf(isNetworkAvailable(MainActivity.this)));

//        Notification.SetUpNoteNotificationManager();

//
//        NewNoteActivity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                    if(settings.isSimulatedMode()){
//                        startActivity(new Intent(MainActivity.this, NoteInputSimulation.class));
//                    }
//                else{
//                    startActivity((new Intent(MainActivity.this, NoteInputNormal.class)));
//                }
//            }
//        });

        newNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(settings.isSimulatedMode()){
                    startActivity(new Intent(MainActivity.this, NoteInputSimulation.class));
                }
                else{
                    startActivity((new Intent(MainActivity.this, NoteInputNormal.class)));
                }
            }
        });



        manageNotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ManageNotes.class));
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
        notes.GenerateNotes();

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
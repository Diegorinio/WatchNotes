package com.example.projektfizyka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class ManageNotes extends AppCompatActivity {
    UserSettings settings;
    NotesFilesPreferences NoteFiles;
    NotesGenerator.NotesGeneratorGrid notes;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_notes);
        settings = new UserSettings(getApplicationContext());
        NoteFiles = new NotesFilesPreferences(getApplicationContext());
        notes = new NotesGenerator(ManageNotes.this, this.settings, this.NoteFiles).new NotesGeneratorGrid();

//        notes.GenerateNotes();


        Button backBtn = (Button) findViewById(R.id.backBtn);
        notes.GenerateNotes();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("state to load:", String.valueOf(settings.isSimulatedMode()));
                startActivity((new Intent(ManageNotes.this, MainActivity.class)));
            }

        });


    }
}
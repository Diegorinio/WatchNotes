package com.example.projektfizyka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ManageNotes extends AppCompatActivity {
    UserSettings settings;
    NotesFilesPreferences NoteFiles;
    Note Notes;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_notes);
        settings = new UserSettings(getApplicationContext());
        NoteFiles = new NotesFilesPreferences(getApplicationContext());
        Notes = new Note(ManageNotes.this, this.settings, this.NoteFiles);

        Notes.GenerateNotes();

        LinearLayout ListLayout = (LinearLayout) findViewById(R.id.FilesListContainer);
        String[] ListFiles = NoteFiles.GetFilesNamesArray();
        TextView NotePreview = (TextView) findViewById(R.id.noteContentFile);
        TextView NoteTitle = (TextView) findViewById(R.id.scrappedNoteTitle);
        Button backBtn = (Button)findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("state to load:", String.valueOf(settings.isSimulatedMode()));
                startActivity((new Intent(ManageNotes.this, MainActivity.class)));
            }

        });

    }

    protected void onStart() {
        super.onStart();
    }
}
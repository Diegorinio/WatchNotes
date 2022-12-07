package com.example.projektfizyka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NoteInputSimulation extends AppCompatActivity {
    public int maxCharactersSetting;
    public int maxLineLen;
    UserSettings settings;
    NoteNotification Notification;
    NotesFilesPreferences NoteFiles;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_simulation_mode);
        NoteFiles = new NotesFilesPreferences(getApplicationContext());
        settings = new UserSettings(getApplicationContext());
        Notification = new NoteNotification("MainNote", getApplicationContext());
        Button notifyButton;
        EditText noteInput;
        EditText watchSimulationNoteOutput = (EditText) findViewById(R.id.mainNoteWatchView);
        watchSimulationNoteOutput.setMovementMethod(new ScrollingMovementMethod());
        Button InputclearBtn;
        InputclearBtn = findViewById(R.id.clearInputBtn);
        noteInput = findViewById(R.id.noteInputTxt);
        noteInput.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(settings.getMaxPerLine())
        });
        notifyButton = findViewById((R.id.notifyBtn));
        Button addToNoteBtn = (Button)findViewById(R.id.addToNoteBtn);
        TextView inputCharsLen = (TextView)findViewById(R.id.inputCharsLen);
        TextView inputeLinesLen = (TextView)findViewById(R.id.inputLinesText);
        EditText noteTitle = (EditText)findViewById(R.id.noteName);
        Button saveBtn = (Button)findViewById(R.id.saveBtn);
        Button backBtn = (Button)findViewById(R.id.backBtn);



        Notification.SetUpNoteNotificationManager();


        addToNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(noteInput.getText().length() >0)
                {
//                    addToNoteBtn.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    watchSimulationNoteOutput.append("\n"+noteInput.getText());
//                    noteInput.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    noteInput.setText("");
                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(noteTitle.getText().length()<=0){
                    UserInteractions.SendMessage(getApplicationContext(), "Title must be longer");
                }
                else{
                    if(NoteFiles.AddValueToFileNamesPreferences(noteTitle.getText().toString(), watchSimulationNoteOutput.getText().toString())){
                        startActivity(new Intent(NoteInputSimulation.this, MainActivity.class));
                    }
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NoteInputSimulation.this, MainActivity.class));
            }
        });

        notifyButton.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View view) {
                //notka 646 znakow 39 linii amazfit bip u pro- 38 bo 39 jest ledwie widoczna
                noteInput.onEditorAction(EditorInfo.IME_ACTION_DONE);
                addToNoteBtn.onEditorAction((EditorInfo.IME_ACTION_DONE));
                //powiadomienie
                Notification.CreateNoteNotification("NotesGenerator", watchSimulationNoteOutput.getText().toString());
            }
        });

        //usun zawartosc notatki
        InputclearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteInput.setText("");
                noteInput.onEditorAction(EditorInfo.IME_ACTION_DONE);
                watchSimulationNoteOutput.setText("");
            }
        });
    }
}
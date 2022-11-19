package com.example.projektfizyka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.projektfizyka.UserSettings;
public class watch_simulation_mode extends AppCompatActivity {
    public int maxCharactersSetting;
    public int maxLineLen;
    UserSettings settings;
    NoteNotification Notification;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_simulation_mode);
        settings = new UserSettings(getApplicationContext());
        Notification = new NoteNotification("MainNote", getApplicationContext());

        String GROUP = "notatki";
        String NoteChannelID= "MainNote";
        Button notifyButton;
        Button optionsBtn;
        EditText noteInput;
        EditText watchSimulationNoteOutput = (EditText) findViewById(R.id.mainNoteWatchView);
        watchSimulationNoteOutput.setMovementMethod(new ScrollingMovementMethod());
        Button InputclearBtn;
        InputclearBtn = findViewById(R.id.clearInputBtn);
        noteInput = findViewById(R.id.noteText);
        noteInput.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(settings.getMaxPerLine())
        });
//        noteInput.setMovementMethod(new ScrollingMovementMethod());
        notifyButton = findViewById((R.id.notifyBtn));
        Button addToNoteBtn = (Button)findViewById(R.id.addToNoteBtn);
        TextView inputCharsLen = (TextView)findViewById(R.id.inputCharsLen);
        TextView inputeLinesLen = (TextView)findViewById(R.id.inputLinesText);
        //options btn
        optionsBtn = findViewById(R.id.optionsBtn);


        Notification.SetUpNoteNotificationManager();


        addToNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(noteInput.getText().length() >0)
                {
                    addToNoteBtn.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    watchSimulationNoteOutput.append("\n"+noteInput.getText());
                    noteInput.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    noteInput.setText("");
                }
            }
        });

        notifyButton.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View view) {
                //notka 646 znakow 39 linii amazfit bip u pro- 38 bo 39 jest ledwie widoczna
                //wyjdz z klawiatury gdy wcisniej done
                noteInput.onEditorAction(EditorInfo.IME_ACTION_DONE);
                addToNoteBtn.onEditorAction((EditorInfo.IME_ACTION_DONE));
                //powiadomienie
                Notification.CreateNoteNotification("Note", watchSimulationNoteOutput.getText().toString());
            }
        });

        //wejdz w activity opcje
        optionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(watch_simulation_mode.this, options.class));
            }
        });
        //usun zawartosc notatki
        InputclearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteInput.setText("");
                noteInput.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });
    }
}
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
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.projektfizyka.UserSettings;
import com.example.projektfizyka.NoteNotification;

public class MainActivity extends AppCompatActivity implements UserInteractions{
    //@Override
    public int maxCharactersSetting;
    UserSettings settings;
    NoteNotification Notification;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = new UserSettings(getApplicationContext());
        Notification = new NoteNotification("Note", getApplicationContext());
//        startActivity(new Intent(MainActivity.this, NotesActivity.class));
        if(settings.CheckIfPreferencesExists()){
            if(settings.isSimulatedMode()){
                startActivity(new Intent(MainActivity.this, watch_simulation_mode.class));
            }
        }
        else{
            startActivity((new Intent(MainActivity.this, options.class)));
        }

        Button notifyButton;
        Button optionsBtn;
        EditText noteInput;
        Button InputclearBtn;
        InputclearBtn = findViewById(R.id.clearInputBtn);

        noteInput = findViewById(R.id.noteText);
        noteInput.setMovementMethod(new ScrollingMovementMethod());
        notifyButton = findViewById((R.id.addToNoteBtn));
        TextView inputCharsLen = (TextView)findViewById(R.id.inputCharsLen);
        TextView inputeLinesLen = (TextView)findViewById(R.id.inputLinesText);
        Button FileMode = (Button)findViewById(R.id.FileMode);
        //options btn
        optionsBtn = findViewById(R.id.optionsBtn);


        TextWatcher textEditorWatcher = new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int len = noteInput.getText().length();
                int lines = noteInput.getLineCount();
                inputCharsLen.setText(String.valueOf(charSequence.length()));
                inputeLinesLen.setText((Integer.toString(lines)));
                if(len > settings.getMaxChars())
                {
                    inputCharsLen.setTextColor(Color.RED);
                }
                else
                {
                    inputCharsLen.setTextColor(Color.BLACK);
                }
                if(lines > settings.getMaxLines())
                {
                    inputeLinesLen.setTextColor(Color.RED);
                }
                else
                {
                    inputeLinesLen.setTextColor(Color.BLACK);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
        noteInput.addTextChangedListener(textEditorWatcher);


        Notification.SetUpNoteNotificationManager();

        notifyButton.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View view) {
                //notka 646 znakow 39 linii amazfit bip u pro- 38 bo 39 jest ledwie widoczna
                Notification.CreateNoteNotification("Notatka", noteInput.getText().toString());
            }
        });

        optionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, options.class));
            }
        });
        FileMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NotesActivity.class));
            }
        });
        InputclearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteInput.setText("");
                noteInput.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });
    }

    protected void onStart() {
        super.onStart();
    }

}
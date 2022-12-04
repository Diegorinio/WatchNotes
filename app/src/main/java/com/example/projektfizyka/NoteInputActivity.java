package com.example.projektfizyka;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NoteInputActivity extends AppCompatActivity {
    //@Override
    public int maxCharactersSetting;
    UserSettings settings;
    NoteNotification Notification;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = new UserSettings(getApplicationContext());
        Notification = new NoteNotification("NotesGenerator", getApplicationContext());
//        startActivity(new Intent(MainActivity.this, NoteInputNormal.class));
        if(settings.CheckIfPreferencesExists()){
            if(settings.isSimulatedMode()){
                startActivity(new Intent(NoteInputActivity.this, NoteInputSimulation.class));
            }
        }
        else{
            startActivity((new Intent(NoteInputActivity.this, options.class)));
        }

        Button notifyButton;
        Button optionsBtn;
        EditText noteInput;
        Button InputclearBtn;
        InputclearBtn = findViewById(R.id.clearInputBtn);

        noteInput = findViewById(R.id.noteInputTxt);
        noteInput.setMovementMethod(new ScrollingMovementMethod());
        notifyButton = findViewById((R.id.addToNoteBtn));
        TextView inputCharsLen = (TextView)findViewById(R.id.inputCharsLen);
        TextView inputeLinesLen = (TextView)findViewById(R.id.inputLinesText);
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
                Notification.CreateNoteNotification("Notatka", noteInput.getText().toString(),1);
            }
        });
        noteInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        optionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NoteInputActivity.this, options.class));
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
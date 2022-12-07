package com.example.projektfizyka;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NoteInputNormal extends AppCompatActivity {
    private NotesFilesPreferences NoteFiles;
    private TextWatcher editTextWatcher;
    UserSettings settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_normal);
        settings = new UserSettings(getApplicationContext());
        NoteFiles = new NotesFilesPreferences(getApplicationContext());
        Button saveToFileBtn = (Button)findViewById(R.id.saveToFile);
        EditText noteInputTxt = (EditText) findViewById(R.id.noteInputTxt);
        EditText noteTitle = (EditText) findViewById(R.id.noteName);
        Button backBtn = (Button)findViewById(R.id.backBtn);
        TextView linesCount = (TextView)findViewById(R.id.linesCount);
        TextView charactersCount = (TextView)findViewById(R.id.charactersCount);

        saveToFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(noteTitle.getText().length()<=0){
                    UserInteractions.SendMessage(getApplicationContext(), "Title must be longer");
                }
                else{
                    if(NoteFiles.AddValueToFileNamesPreferences(noteTitle.getText().toString(), noteInputTxt.getText().toString())){
                        startActivity(new Intent(NoteInputNormal.this, MainActivity.class));
                    }
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NoteInputNormal.this, MainActivity.class));
            }
        });

        editTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int len = noteInputTxt.getText().length();
                int lines = noteInputTxt.getLineCount();
                charactersCount.setText(String.valueOf(charSequence.length()));
                linesCount.setText((Integer.toString(lines)));
            if(len > settings.getMaxChars())
            {
                charactersCount.setTextColor(Color.RED);
            }
            else
            {
                charactersCount.setTextColor(Color.BLACK);
            }
            if(lines > settings.getMaxLines())
            {
                linesCount.setTextColor(Color.RED);
            }
            else
            {
                linesCount.setTextColor(Color.BLACK);
            }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        noteInputTxt.addTextChangedListener(editTextWatcher);
        noteInputTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        noteTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

    }
}
package com.example.projektfizyka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class NotesActivity extends AppCompatActivity implements UserInteractions {
    private NotesFilesPreferences NoteFiles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        NoteFiles = new NotesFilesPreferences(getApplicationContext());
        Button saveToFileBtn = (Button)findViewById(R.id.saveToFile);
        EditText noteInputTxt = (EditText) findViewById(R.id.noteInputTxt);
        EditText noteTitle = (EditText) findViewById(R.id.noteName);
        Button backBtn = (Button)findViewById(R.id.backBtn);


        saveToFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteFiles.AddValueToFileNamesPreferences(noteTitle.getText().toString());
                NoteFiles.SaveToFile(noteTitle.getText().toString(), noteInputTxt.getText().toString());
                startActivity(new Intent(NotesActivity.this, MainActivity.class));
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotesActivity.this, MainActivity.class));
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

//    private Button CreateElementButton(String BtnText){
//        Button NewBtn = new Button(this);
//        NewBtn.setText(BtnText);
//        NewBtn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
////        NewBtn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                NoteFiles.ReadFromFile(NotesActivity.this, BtnText);
////            }
////        });
//        return NewBtn;
//    }
}
package com.example.projektfizyka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import com.example.projektfizyka.NotesFilesPreferences;
import com.example.projektfizyka.NotesFilesManager;
public class NotesActivity extends AppCompatActivity implements UserInteractions {
    private NotesFilesPreferences NoteFiles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        NoteFiles = new NotesFilesPreferences(getApplicationContext());
        Button saveToFileBtn = (Button)findViewById(R.id.saveToFile);
        Button readFromFile = (Button)findViewById(R.id.ReadFromFile);
//        TextView ReadFileOut = (TextView)findViewById(R.id.FileContent);
        EditText NoteContent = (EditText)findViewById(R.id.NoteContent);
        EditText NoteName = (EditText)findViewById(R.id.FileName);
        LinearLayout ListLayout = (LinearLayout) findViewById(R.id.FilesList);
        EditText FileContent = (EditText) findViewById(R.id.TextContentView);
        for(String el : NoteFiles.GetFilesNamesArray()){
            Log.i("File"+ el, el);
            Button btn = CreateElementButton(el);
            ListLayout.addView(btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FileContent.setText(NoteFiles.ReadFromFile(NotesActivity.this, btn.getText().toString()));
                }
            });
        }
//        File file = getFileStreamPath("*.txt");
//        ReadFileOut.setText(file.toString());
        String note = "sdauifuidsanfdsafdsvfdsvfdsvfsdv\nvfdsnvifnsduivfnduisvbfdsisdbv";
        saveToFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String notka = NoteContent.getText().toString();
                NoteFiles.AddValueToFileNamesPreferences(NoteName.getText().toString());
                NoteFiles.SaveToFile(NoteName.getText().toString(), notka);
            }
        });
        readFromFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String note = readFromFile(getApplicationContext(), NoteName.getText().toString());
//                ReadFileOut.setText(note);
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    private Button CreateElementButton(String BtnText){
        Button NewBtn = new Button(this);
        NewBtn.setText(BtnText);
        NewBtn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        NewBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NoteFiles.ReadFromFile(NotesActivity.this, BtnText);
//            }
//        });
        return NewBtn;
    }
}
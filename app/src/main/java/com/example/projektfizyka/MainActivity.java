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
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.projektfizyka.UserSettings;
import com.example.projektfizyka.NoteNotification;
import com.example.projektfizyka.NotesFilesPreferences;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity{
    //@Override
    UserSettings settings;
    NoteNotification Notification;
    NotesFilesPreferences NoteFiles;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = new UserSettings(getApplicationContext());
        Notification = new NoteNotification("Note", getApplicationContext());
        NoteFiles = new NotesFilesPreferences(getApplicationContext());

        Button NewNoteActivity = (Button) findViewById(R.id.newNoteBtn);
        Button optionsBtn = (Button)findViewById(R.id.optionsBtn);

        Notification.SetUpNoteNotificationManager();


        NewNoteActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(settings.isSimulatedMode()){
                        startActivity(new Intent(MainActivity.this, watch_simulation_mode.class));
                    }
                else{
                    startActivity((new Intent(MainActivity.this, NotesActivity.class)));
                }
            }
        });

        optionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity((new Intent(MainActivity.this, options.class)));
            }
        });

    }

    protected void onStart() {

        super.onStart();
        LinearLayout ListLayout = (LinearLayout) findViewById(R.id.FilesListContainer);
        ScrollView listLayout = (ScrollView)findViewById(R.id.FilesListContainerScroll);
        String[] ListFiles = NoteFiles.GetFilesNamesArray();
//        for(String x: NoteFiles.GetFilesNamesArray()){
////            Log.i("Len ", x);
////        }
        TextView NotePreview = (TextView) findViewById(R.id.noteContentFile);
        TextView NoteTitle = (TextView) findViewById(R.id.noteTitle);
        Button deleteNoteBtn = (Button) findViewById(R.id.deleteBtn);
        Button sendNoteBtn = (Button) findViewById(R.id.sendNotifyBtn);
        if(ListFiles.length >0){
            for(String file: ListFiles){
                Button newBtn = CreateElementButton(file);
                ListLayout.addView(newBtn);
                newBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String content =NoteFiles.ReadContentFromFile(newBtn.getText().toString());
                        NotePreview.setText(content);
                        NoteTitle.setText(newBtn.getText().toString());
                        deleteNoteBtn.setEnabled(true);
                    }
                });
            }
        }
        else{

        }

                if(settings.CheckIfPreferencesExists()){
            if(settings.isSimulatedMode()){
                startActivity(new Intent(MainActivity.this, watch_simulation_mode.class));
            }
        }
        else{
            startActivity((new Intent(MainActivity.this, options.class)));
        }

        sendNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notification.CreateNoteNotification("Notatka", NotePreview.getText().toString());
            }
        });

        deleteNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteFiles.DeleteFile(NoteTitle.getText().toString());
                finish();
                startActivity(getIntent());
            }
        });



    }
    private Button CreateElementButton(String BtnText){
        Button NewBtn = new Button(this);
        NewBtn.setText(BtnText);
        NewBtn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return NewBtn;
    }
}
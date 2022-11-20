package com.example.projektfizyka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
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
        if(settings.CheckIfPreferencesExists()){
            GenerateNotes();
        }
        else{
            startActivity((new Intent(MainActivity.this, options.class)));
        }
    }
    private Button CreateElementButton(String BtnText){
        Button NewBtn = new Button(this);
        NewBtn.setText(BtnText);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,10,0,10);
        NewBtn.setLayoutParams(params);
        return NewBtn;
    }

    private void GenerateNotes(){
        LinearLayout ListLayout = (LinearLayout) findViewById(R.id.FilesListContainer);
        String[] ListFiles = NoteFiles.GetFilesNamesArray();
        TextView NotePreview = (TextView) findViewById(R.id.noteContentFile);
        TextView NoteTitle = (TextView) findViewById(R.id.noteTitle);
        Button deleteNoteBtn = (Button) findViewById(R.id.deleteBtn);
        Button sendNoteBtn = (Button) findViewById(R.id.sendNotifyBtn);
        if(ListFiles.length >0){
            for(String file: ListFiles){
                if(file == null || file == ""){
                    Log.i("file exc", file);
                }
                else{
                Button newBtn = CreateElementButton(file);
                ListLayout.addView(newBtn);
                newBtn.setBackgroundResource(R.color.lightred);
                newBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String content;
                        if(settings.isFormatModeOn()){
                            content =StringOperations.FormatStringOutputAuto(NoteFiles.ReadContentFromFile(newBtn.getText().toString()));
                        }
                        else{
                            content =NoteFiles.ReadContentFromFile(newBtn.getText().toString());
                        }
                        NotePreview.setText(content);
                        NoteTitle.setText(newBtn.getText().toString());
                        deleteNoteBtn.setEnabled(true);
                    }
                });
            }
            }
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
                //jebac nie chce mi sie pisac hanlerow na onclick
                AlertDialog.Builder alert = UserInteractions.AlertBuilder(MainActivity.this, "Confirm delete", "Do you want to delete file: "+NoteTitle.getText().toString());
                alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        NoteFiles.RemoveFileNameFromPreferences(NoteTitle.getText().toString());
                        RestartActivty();
                    }
                });
                alert.setNegativeButton("Nope", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.show();
            }
        });
    }

    private void RestartActivty(){
                finish();
                startActivity(getIntent());
    }
}
package com.example.projektfizyka;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{
    UserSettings settings;
    NoteNotification Notification;
    NotesFilesPreferences NoteFiles;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        startActivity(new Intent(MainActivity.this, Scrapper.class));
        settings = new UserSettings(getApplicationContext());
        Notification = new NoteNotification("Note", getApplicationContext());
        NoteFiles = new NotesFilesPreferences(getApplicationContext());


        Button NewNoteActivity = (Button) findViewById(R.id.newNoteBtn);
        Button optionsBtn = (Button)findViewById(R.id.optionsBtn);
        Button fetchBtn = (Button)findViewById(R.id.goToFetchBtn);

        Log.i("Connection", String.valueOf(isNetworkAvailable(MainActivity.this)));

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

        fetchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkAvailable(MainActivity.this)){
                    startActivity(new Intent(MainActivity.this, Scrapper.class));
                }
                else{
                    UserInteractions.SendMessage(getApplicationContext(), "No internet connection");
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
        TextView NoteTitle = (TextView) findViewById(R.id.scrappedNoteTitle);
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
                String[] NotesArray = StringOperations.SplitNotificationsToStringLength(NotePreview.getText().toString(), settings.getMaxChars());
                Log.i("Notes array", NotesArray.toString());
                String new_string = "";
                for(int x=0;x<=NotesArray.length-1;x++){
                    Log.i("Notes array", "id: "+ x +": "+NotesArray[x]);
                    int finalX = x;
                    Handler handler = new Handler();
                    int finalX1 = x;
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            Notification.CreateNoteNotification("Notatka", NotesArray[finalX1],finalX1);
                        }
                    }, 500);
                }
            }
        });

        deleteNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //jebac nie chce mi sie pisac handlerow na onclick
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

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
package com.example.projektfizyka;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import android.app.Fragment;

public class Note  extends NoteNotification {
    NotesFilesPreferences NoteFiles;
    UserSettings _settings;
    Activity _context;

    public Note(Activity activity, UserSettings settings, NotesFilesPreferences noteFiles) {
        super("Note", activity);
        _context = activity;
        _settings = settings;
        NoteFiles = noteFiles;
        SetUpNoteNotificationManager();
    }

    private Button CreateElementButton(String BtnText) {
        Button NewBtn = new Button(_context);
        NewBtn.setText(BtnText);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 10, 0, 10);
        NewBtn.setLayoutParams(params);
        return NewBtn;
    }

    public void GenerateNotes() {
        LinearLayout ListLayout = (LinearLayout) _context.findViewById(R.id.FilesListContainer);
        String[] ListFiles = NoteFiles.GetFilesNamesArray();
        TextView NotePreview = (TextView) _context.findViewById(R.id.noteContentFile);
        TextView NoteTitle = (TextView) _context.findViewById(R.id.scrappedNoteTitle);
        Button deleteNoteBtn = (Button) _context.findViewById(R.id.deleteBtn);
        Button sendNoteBtn = (Button) _context.findViewById(R.id.sendNotifyBtn);
        if (ListFiles.length > 0) {
            CreateNotes(ListFiles,ListLayout,NoteTitle,NotePreview);
        }
        sendNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] NotesArray = StringOperations.SplitNotificationsToStringLength(NotePreview.getText().toString(), _settings.getMaxChars());
                Log.i("Notes array", NotesArray.toString());
                String new_string = "";
                for(int x=0;x<=NotesArray.length-1;x++){
                    Log.i("Notes array", "id: "+ x +": "+NotesArray[x]);
                    Handler handler = new Handler();
                    int finalX1 = x;
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            CreateNoteNotification("Notatka", NotesArray[finalX1],finalX1);
                        }
                    }, 500);
                }
            }
        });

        deleteNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //jebac nie chce mi sie pisac handlerow na onclick
                _context.startActivity(new Intent(_context.getApplicationContext(), ManageNotes.class));
//                AlertDialog.Builder alert = UserInteractions.AlertBuilder(_context, "Confirm delete", "Do you want to delete file: "+NoteTitle.getText().toString());
//                alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        NoteFiles.RemoveFileNameFromPreferences(NoteTitle.getText().toString());
//                        RestartActivty();
//                    }
//                });
//                alert.setNegativeButton("Nope", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//                alert.show();
            }
        });
    }

    private void CreateNotes(String[] content,LinearLayout layout, TextView NoteTitle,TextView NoteContent){
        for (String file : content) {
            if (file == null || file == "") {
                Log.i("file exc", file);
            } else {
                Button newBtn = CreateElementButton(file);
                layout.addView(newBtn);
                newBtn.setBackgroundResource(R.color.lightred);
                newBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String content;
                        if (_settings.isFormatModeOn()) {
                            content = StringOperations.FormatStringOutputAuto(NoteFiles.ReadContentFromFile(newBtn.getText().toString()));
                        } else {
                            content = NoteFiles.ReadContentFromFile(newBtn.getText().toString());
                        }
                        NoteContent.setText(content);
                        NoteTitle.setText(newBtn.getText().toString());
                    }
                });
            }
        }
    }
    private void RestartActivty(){
        _context.finish();
        _context.startActivity(_context.getIntent());
    }
}
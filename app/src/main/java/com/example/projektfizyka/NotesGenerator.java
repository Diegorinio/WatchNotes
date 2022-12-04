package com.example.projektfizyka;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotesGenerator extends NoteNotification {
    NotesFilesPreferences NoteFiles;
    UserSettings _settings;
    Activity _context;

    public NotesGenerator(Activity activity, UserSettings settings, NotesFilesPreferences noteFiles) {
        super("Notes", activity);
        _context = activity;
        _settings = settings;
        NoteFiles = noteFiles;
        SetUpNoteNotificationManager();
    }

    private void CreateNotes(String[] content,LinearLayout layout, TextView NoteTitle,TextView NoteContent){
        for (String file : content) {
            if (file == null || file == "") {
                Log.i("file exc", file);
            } else {
                Button newBtn = CreateElementButton(file);
                layout.addView(newBtn);
                LinearLayout.LayoutParams params = setLinearLayout();
                newBtn.setLayoutParams(params);
                newBtn.setBackgroundResource(R.drawable.note);
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
        else{
            TextView LudzieTuNikogoNieMa = new TextView(_context);
            LudzieTuNikogoNieMa.setText("It's empty around here, add new note with button at right-bottom of the screen");
            ListLayout.addView(LudzieTuNikogoNieMa);
        }
        sendNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] NotesArray = StringOperations.SplitNotificationsToStringLength(NotePreview.getText().toString(), _settings.getMaxChars());
                Log.i("notes array", NotesArray.toString());
                String new_string = "";
                for(int x=0;x<=NotesArray.length-1;x++){
                    Log.i("notes array", "id: "+ x +": "+NotesArray[x]);
                    Handler handler = new Handler();
                    int finalX1 = x;
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            CreateNoteNotification(NoteTitle.getText().toString(), NotesArray[finalX1],finalX1);
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

    private Button CreateElementButton(String BtnText) {
        Button NewBtn = new Button(_context);
        NewBtn.setText(BtnText);
        return NewBtn;
    }


    private LinearLayout.LayoutParams setLinearLayout(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(50, 15, 50, 15);
        return params;
    }

    private void RestartActivty(){
        _context.finish();
        _context.startActivity(_context.getIntent());
    }

    public class NotesGeneratorGrid{
        public void GenerateNotes() {
            GridLayout ListLayout = (GridLayout) _context.findViewById(R.id.FilesListContainer);
            String[] ListFiles = NoteFiles.GetFilesNamesArray();
            TextView NotePreview = (TextView) _context.findViewById(R.id.noteContentFile);
            TextView NoteTitle = (TextView) _context.findViewById(R.id.scrappedNoteTitle);
            Button deleteNoteBtn = (Button) _context.findViewById(R.id.deleteBtn);
            Button sendNoteBtn = (Button) _context.findViewById(R.id.sendNotifyBtn);
            if (ListFiles.length > 0) {
                CreateNotes(ListFiles, ListLayout, NoteTitle, NotePreview);
            }
            sendNoteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String[] NotesArray = StringOperations.SplitNotificationsToStringLength(NotePreview.getText().toString(), _settings.getMaxChars());
                    Log.i("notes array", NotesArray.toString());
                    String new_string = "";
                    for (int x = 0; x <= NotesArray.length - 1; x++) {
                        Log.i("notes array", "id: " + x + ": " + NotesArray[x]);
                        Handler handler = new Handler();
                        int finalX1 = x;
                        handler.postDelayed(new Runnable() {
                            public void run() {
//                            CreateNoteNotification("Notatka", NotesArray[finalX1],finalX1);
                            }
                        }, 500);
                    }
                }
            });

        };

        private void CreateNotes (String[]content, GridLayout layout, TextView NoteTitle, TextView NoteContent){
            for (String file : content) {
                if (file == null || file == "") {
                    Log.i("file exc", file);
                } else {
                    Button newBtn = CreateElementButton(file);
                    Button deleteBtn = CreateElementButton("del");
                    Button editBtn = CreateElementButton("edit");
                    layout.addView(newBtn);
//                functionBtn.setBackgroundResource(R.drawable.ic_baseline_highlight_off_10);
                    deleteBtn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    layout.addView(editBtn);
                    layout.addView(deleteBtn);
                    newBtn.setBackgroundResource(R.drawable.note);
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
                    deleteBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder alert = UserInteractions.AlertBuilder(_context, "Confirm delete", "Do you want to delete file: "+file);
                            alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    NoteFiles.RemoveFile(file);
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
            }
        }

        private Button CreateElementButton(String BtnText) {
            Button NewBtn = new Button(_context);
            NewBtn.setText(BtnText);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
//        params.setGravity(Gravity.CENTER);
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.width = GridLayout.LayoutParams.WRAP_CONTENT;
            params.setMargins(5, 5, 5, 12);
            NewBtn.setLayoutParams(params);
            return NewBtn;
        }
    }
}
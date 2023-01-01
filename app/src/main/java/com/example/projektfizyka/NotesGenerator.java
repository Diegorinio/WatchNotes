package com.example.projektfizyka;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

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

    public void GenerateNotes() {
        LinearLayout ListLayout = (LinearLayout) _context.findViewById(R.id.FilesListContainer);
        String[] ListFiles = NoteFiles.GetFilesNamesArray();
        EditText NotePreview = (EditText) _context.findViewById(R.id.noteContentFile);
        TextView NoteTitle = (TextView) _context.findViewById(R.id.scrappedNoteTitle);
        Button sendNoteBtn = (Button) _context.findViewById(R.id.sendNotifyBtn);
        NotePreview.setMovementMethod(new ScrollingMovementMethod());
        Log.i("REs len:", Integer.toString(ListFiles.length));
        if (ListFiles[0] != "") {
            sendNoteBtn.setEnabled(true);
            CreateNotes(ListFiles,ListLayout,NoteTitle,NotePreview);
        }
        else{
            sendNoteBtn.setEnabled(false);
            NotePreview.setText("It's empty around here, just add new note with button at right bottom :)");
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
                newBtn.setTextColor(_context.getResources().getColorStateList(R.color.white));
                newBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String content;
                        if (_settings.isFormatModeOn()) {
                            content = StringOperations.FormatStringOutputAuto(NoteFiles.ReadContentFromFile(newBtn.getText().toString()));
                        } else {
                            content = StringOperations.FormatStringOutput(NoteFiles.ReadContentFromFile(newBtn.getText().toString()));
                        }
                        NoteContent.setText(content);
                        NoteTitle.setText(newBtn.getText().toString());
                    }
                });
            }
        }
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

// GRID
    public class NotesGeneratorGrid{
        public void GenerateNotes() {
            GridLayout ListLayout = (GridLayout) _context.findViewById(R.id.FilesListContainer);
            String[] ListFiles = NoteFiles.GetFilesNamesArray();
            EditText NotePreview = (EditText) _context.findViewById(R.id.noteContentFile);
            TextView NoteTitle = (TextView) _context.findViewById(R.id.scrappedNoteTitle);
//            Button sendNoteBtn = (Button) _context.findViewById(R.id.sendNotifyBtn);
            Button modifyNoteBtn = (Button) _context.findViewById(R.id.rewriteNoteBtn);
            if (ListFiles[0] != "") {
//                sendNoteBtn.setEnabled(true);
                CreateNotes(ListFiles, ListLayout, NoteTitle, NotePreview);
            }
            else{
                NotePreview.setText("It seems like you didn't add any note, just go back and create one with button at bottom right :)");
//                sendNoteBtn.setEnabled(false);
            }

            modifyNoteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String title = NoteTitle.getText().toString();
                    String content = NotePreview.getText().toString();
                    if(content.length()!=0){
                        NoteFiles.WriteToFile(title, content);
                        UserInteractions.SendMessage(_context, "Note modified");
                    }
                    else{
                        UserInteractions.SendMessage(_context, "Note it empty");
                    }
                }
            });
//            sendNoteBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    String[] NotesArray = StringOperations.SplitNotificationsToStringLength(NotePreview.getText().toString(), _settings.getMaxChars());
//                    Log.i("notes array", NotesArray.toString());
//                    String new_string = "";
//                    for (int x = 0; x <= NotesArray.length - 1; x++) {
//                        Log.i("notes array", "id: " + x + ": " + NotesArray[x]);
//                        Handler handler = new Handler();
//                        int finalX1 = x;
//                        handler.postDelayed(new Runnable() {
//                            public void run() {
//                            CreateNoteNotification("Notatka", NotesArray[finalX1],finalX1);
//                            }
//                        }, 500);
//                    }
//                }
//            });

        };

        private void CreateNotes (String[]content, GridLayout layout, TextView NoteTitle, TextView NoteContent){
            for (String file : content) {
                if (file == null || file == "") {
                    Log.i("file exc", file);
                } else {
                    Button noteBtn = CreateElementButton(file);
                    MaterialButton deleteBtn = CreateElementMaterialButton("del");
                    MaterialButton editBtn = CreateElementMaterialButton("edit");
                    deleteBtn.setBackgroundTintList(_context.getResources().getColorStateList(R.color.black));
                    deleteBtn.setIcon(ContextCompat.getDrawable(_context,R.drawable.ic_baseline_delete_forever_24));
//                    editBtn.setBackgroundTintList(_context.getResources().getColorStateList(R.color.black));
//                    editBtn.setIcon(ContextCompat.getDrawable(_context, R.drawable.ic_baseline_edit_note_24));
                    layout.addView(noteBtn);
                    deleteBtn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                    layout.addView(editBtn);
                    layout.addView(deleteBtn);
                    noteBtn.setBackgroundResource(R.drawable.note);
                    noteBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String content;
                            if (_settings.isFormatModeOn()) {
                                content = StringOperations.FormatStringOutputAuto(NoteFiles.ReadContentFromFile(noteBtn.getText().toString()));
                            } else {
                                content = StringOperations.FormatStringOutput(NoteFiles.ReadContentFromFile(noteBtn.getText().toString()));
                            }
                            NoteContent.setText(content);
                            NoteTitle.setText(file);
//                            String content = NoteFiles.ReadContentFromFile(noteBtn.getText().toString());
                            NoteContent.setText(content);
                            NoteTitle.setText(noteBtn.getText().toString());
                            NoteContent.setFocusable(true);
                            NoteContent.setFocusableInTouchMode(true);
                            _context.findViewById(R.id.rewriteNoteBtn).setVisibility(View.VISIBLE);
//                            _context.findViewById(R.id.rewriteNoteBtn).setEnabled(true);
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
//                    editBtn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            String content = NoteFiles.ReadContentFromFile(noteBtn.getText().toString());
//                            NoteContent.setText(content);
//                            NoteTitle.setText(noteBtn.getText().toString());
//                            NoteContent.setFocusable(true);
//                            NoteContent.setFocusableInTouchMode(true);
//                            _context.findViewById(R.id.rewriteNoteBtn).setVisibility(View.VISIBLE);
////                            _context.findViewById(R.id.rewriteNoteBtn).setEnabled(true);
//                        }
//
//                    });
                }
            }
        }

        private Button CreateElementButton(String BtnText) {
            Button NewBtn = new MaterialButton(_context);
            NewBtn.setText(BtnText);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
//        params.setGravity(Gravity.CENTER);
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.width = GridLayout.LayoutParams.WRAP_CONTENT;
            params.setMargins(5, 5, 5, 12);
            NewBtn.setLayoutParams(params);
            return NewBtn;
        }
        private MaterialButton CreateElementMaterialButton(String BtnText) {
            MaterialButton NewBtn = new MaterialButton(_context);
            NewBtn.setText(BtnText);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.width = GridLayout.LayoutParams.WRAP_CONTENT;
            params.setMargins(5, 5, 5, 12);
            NewBtn.setLayoutParams(params);
            return NewBtn;
        }
    }
}
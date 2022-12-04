package com.example.projektfizyka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class ManageNotes extends AppCompatActivity {
    UserSettings settings;
    NotesFilesPreferences NoteFiles;
    NotesGenerator.NotesGeneratorGrid notes;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_notes);
        settings = new UserSettings(getApplicationContext());
        NoteFiles = new NotesFilesPreferences(getApplicationContext());
        notes = new NotesGenerator(ManageNotes.this, this.settings, this.NoteFiles).new NotesGeneratorGrid();

//        notes.GenerateNotes();

        GridLayout ListLayout = (GridLayout) findViewById(R.id.FilesListContainer);
        String[] ListFiles = NoteFiles.GetFilesNamesArray();
        TextView NotePreview = (TextView) findViewById(R.id.noteContentFile);
        TextView NoteTitle = (TextView) findViewById(R.id.scrappedNoteTitle);
        Button backBtn = (Button) findViewById(R.id.backBtn);
        notes.GenerateNotes();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("state to load:", String.valueOf(settings.isSimulatedMode()));
                startActivity((new Intent(ManageNotes.this, MainActivity.class)));
            }

        });


    }

//    public void GenerateNotes() {
//        GridLayout ListLayout = (GridLayout) findViewById(R.id.FilesListContainer);
//        String[] ListFiles = NoteFiles.GetFilesNamesArray();
//        TextView NotePreview = (TextView) findViewById(R.id.noteContentFile);
//        TextView NoteTitle = (TextView) findViewById(R.id.scrappedNoteTitle);
//        Button deleteNoteBtn = (Button) findViewById(R.id.deleteBtn);
//        Button sendNoteBtn = (Button) findViewById(R.id.sendNotifyBtn);
//        if (ListFiles.length > 0) {
//            CreateNotes(ListFiles, ListLayout, NoteTitle, NotePreview);
//        }
//        sendNoteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String[] NotesArray = StringOperations.SplitNotificationsToStringLength(NotePreview.getText().toString(), settings.getMaxChars());
//                Log.i("notes array", NotesArray.toString());
//                String new_string = "";
//                for (int x = 0; x <= NotesArray.length - 1; x++) {
//                    Log.i("notes array", "id: " + x + ": " + NotesArray[x]);
//                    Handler handler = new Handler();
//                    int finalX1 = x;
//                    handler.postDelayed(new Runnable() {
//                        public void run() {
////                            CreateNoteNotification("Notatka", NotesArray[finalX1],finalX1);
//                        }
//                    }, 500);
//                }
//            }
//        });
//
//    };
//
//    private void CreateNotes (String[]content, GridLayout layout, TextView NoteTitle, TextView NoteContent){
//        for (String file : content) {
//            if (file == null || file == "") {
//                Log.i("file exc", file);
//            } else {
//                Button newBtn = CreateElementButton(file);
//                Button functionBtn = CreateElementButton("del");
//                Button functionBtn2 = CreateElementButton("edit");
//                layout.addView(newBtn);
////                functionBtn.setBackgroundResource(R.drawable.ic_baseline_highlight_off_10);
//                functionBtn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                layout.addView(functionBtn2);
//                layout.addView(functionBtn);
//                newBtn.setBackgroundResource(R.drawable.note);
//                newBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        String content;
//                        if (settings.isFormatModeOn()) {
//                            content = StringOperations.FormatStringOutputAuto(NoteFiles.ReadContentFromFile(newBtn.getText().toString()));
//                        } else {
//                            content = NoteFiles.ReadContentFromFile(newBtn.getText().toString());
//                        }
//                        NoteContent.setText(content);
//                        NoteTitle.setText(newBtn.getText().toString());
//                    }
//                });
//            }
//        }
//    }
//    private Button CreateElementButton(String BtnText) {
//        Button NewBtn = new Button(ManageNotes.this);
//        NewBtn.setText(BtnText);
//        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
////        params.setGravity(Gravity.CENTER);
//        params.height = GridLayout.LayoutParams.WRAP_CONTENT;
//        params.width = GridLayout.LayoutParams.WRAP_CONTENT;
//        params.setMargins(5, 5, 5, 12);
//        NewBtn.setLayoutParams(params);
//        return NewBtn;
//    }
}
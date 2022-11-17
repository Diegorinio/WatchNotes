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
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class watch_simulation_mode extends AppCompatActivity {
    static void TextSplitter(String inputText, EditText targetText)
    {
        int maxChars = 501;
        int maxLines = 39;//38 bo ucina 39

    }
    public int maxCharactersSetting;
    public int maxLineLen;
    //    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (getCurrentFocus() != null) {
//            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//        }
//        return super.dispatchTouchEvent(ev);
//    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_simulation_mode);

        String GROUP = "notatki";
        String NoteChannelID= "MainNote";
        Button notifyButton;
        Button optionsBtn;
        EditText noteInput;
        EditText watchSimulationNoteOutput = (EditText) findViewById(R.id.mainNoteWatchView);
        watchSimulationNoteOutput.setMovementMethod(new ScrollingMovementMethod());
        Button InputclearBtn;
        InputclearBtn = findViewById(R.id.clearInputBtn);
        noteInput = findViewById(R.id.noteText);
        noteInput.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(readSettings(2))
        });
//        noteInput.setMovementMethod(new ScrollingMovementMethod());
        notifyButton = findViewById((R.id.notifyBtn));
        Button addToNoteBtn = (Button)findViewById(R.id.addToNoteBtn);
        TextView inputCharsLen = (TextView)findViewById(R.id.inputCharsLen);
        TextView inputeLinesLen = (TextView)findViewById(R.id.inputLinesText);
        //options btn
        optionsBtn = findViewById(R.id.optionsBtn);


//        TextWatcher textEditorWatcher = new TextWatcher(){

//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                int len = noteInput.getText().length();
//                int lines = noteInput.getLineCount();
//                inputCharsLen.setText(String.valueOf(charSequence.length()));
//                inputeLinesLen.setText((Integer.toString(lines)));
//                if(len > readSettings(0))
//                {
//                    inputCharsLen.setTextColor(Color.RED);
//                }
//                else
//                {
//                    inputCharsLen.setTextColor(Color.BLACK);
//                }
//                if(lines > readSettings(1))
//                {
//                    inputeLinesLen.setTextColor(Color.RED);
//                }
//                else
//                {
//                    inputeLinesLen.setTextColor(Color.BLACK);
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        };

//        noteInput.addTextChangedListener(textEditorWatcher);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(NoteChannelID, NoteChannelID, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel((channel));
        }


        addToNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToNoteBtn.onEditorAction(EditorInfo.IME_ACTION_DONE);
                watchSimulationNoteOutput.append("\n"+noteInput.getText());
                noteInput.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });

//        watchViewClearBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                noteInput.onEditorAction(EditorInfo.IME_ACTION_DONE);
//                addToNoteBtn.onEditorAction((EditorInfo.IME_ACTION_DONE));
//                watchSimulationNoteOutput.setText("");
//            }
//        });

        notifyButton.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View view) {
                //notka 646 znakow 39 linii amazfit bip u pro- 38 bo 39 jest ledwie widoczna
//                int chars = Integer.parseInt(testChars.getText().toString());
//                String newChars = "";
//                for(int x=1;x<=chars;x++)
//                {
//                    newChars += Integer.toString(x)+"\n";
//                }

                //wyjdz z klawiatury gdy wcisniej done
                noteInput.onEditorAction(EditorInfo.IME_ACTION_DONE);
                addToNoteBtn.onEditorAction((EditorInfo.IME_ACTION_DONE));
                //powiadomienie
                NotificationCompat.Builder builder = new NotificationCompat.Builder(watch_simulation_mode.this, NoteChannelID);
                builder.setContentTitle("Test");
                builder.setContentTitle("Notatka");
                builder.setSmallIcon(R.drawable.ic_launcher_foreground);
                builder.setGroup(GROUP);
                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                builder.setStyle(new NotificationCompat.BigTextStyle().bigText(watchSimulationNoteOutput.getText().toString()));
                builder.setAutoCancel(false);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(watch_simulation_mode.this);
                managerCompat.notify(1, builder.build());
            }
        });

        //wejdz w activity opcje
        optionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(watch_simulation_mode.this, options.class));
            }
        });
        //usun zawartosc notatki
        InputclearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteInput.setText("");
                noteInput.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });
    }

    protected void onStart() {
        super.onStart();
        maxCharactersSetting = readSettings(0);
        maxLineLen = readSettings(2);

    }

    //wczytaj opcje
    int readSettings(int value){
        if(value == 0){//maks ilosc znakow
            SharedPreferences sharedprefs = this.getSharedPreferences("settings", Context.MODE_PRIVATE);
            Log.i("max chars", Integer.toString(sharedprefs.getInt("maxCharacters", 0)));
            return sharedprefs.getInt("maxCharacters", 0);
        }
        else if (value == 1) // max ilosc linii
        {
            SharedPreferences sharedprefs = this.getSharedPreferences("settings", Context.MODE_PRIVATE);
            Log.i("max Lines", Integer.toString(sharedprefs.getInt("maxLines", 0)));
            return sharedprefs.getInt("maxLines", 0);
        }
        else if (value ==2) // max ilosc charow w linii
        {
            SharedPreferences sharedprefs = this.getSharedPreferences("settings", Context.MODE_PRIVATE);
            Log.i("max Lines per ", Integer.toString(sharedprefs.getInt("maxPerLine", 0)));
            return sharedprefs.getInt("maxPerLine", 17);
        }
        else if(value == 3) // czy symuluj liniie, zmiana z main activity na line activity
        {
            SharedPreferences sharedprefs = this.getSharedPreferences("settings", Context.MODE_PRIVATE);
            Log.i("line simulation", Integer.toString(sharedprefs.getInt("watchSimulation", 0)));
            return sharedprefs.getInt("watchSimulation", 0);
        }
        else
        {
            return 0;
        }
    }

    public void WatchSimulation(EditText input)
    {

    }
}
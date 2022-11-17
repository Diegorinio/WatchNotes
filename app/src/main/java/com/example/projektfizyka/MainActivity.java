package com.example.projektfizyka;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    //@Override
    static void TextSplitter(String inputText, EditText targetText)
    {
        int maxChars = 501;
        int maxLines = 39;//38 bo ucina 39

    }
    public int maxCharactersSetting;
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (getCurrentFocus() != null) {
//            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//        }
//        return super.dispatchTouchEvent(ev);
//    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String GROUP = "notatki";
        String NoteChannelID= "MainNote";
        Button notifyButton;
        Button optionsBtn;
        EditText noteInput;
        Button InputclearBtn;
        InputclearBtn = findViewById(R.id.clearInputBtn);

        noteInput = findViewById(R.id.noteText);
        noteInput.setMovementMethod(new ScrollingMovementMethod());
        notifyButton = findViewById((R.id.notifyBtn));
        TextView inputCharsLen = (TextView)findViewById(R.id.inputCharsLen);
        TextView inputeLinesLen = (TextView)findViewById(R.id.inputLinesText);
        //options btn
        optionsBtn = findViewById(R.id.optionsBtn);


        TextWatcher textEditorWatcher = new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int len = noteInput.getText().length();
                int lines = noteInput.getLineCount();
                inputCharsLen.setText(String.valueOf(charSequence.length()));
                inputeLinesLen.setText((Integer.toString(lines)));
                if(len > readSettings(0))
                {
                    inputCharsLen.setTextColor(Color.RED);
                }
                else
                {
                    inputCharsLen.setTextColor(Color.BLACK);
                }
                if(lines > readSettings(1))
                {
                    inputeLinesLen.setTextColor(Color.RED);
                }
                else
                {
                    inputeLinesLen.setTextColor(Color.BLACK);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
        noteInput.addTextChangedListener(textEditorWatcher);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(NoteChannelID, NoteChannelID, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel((channel));
        }

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
                noteInput.onEditorAction(EditorInfo.IME_ACTION_DONE);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, NoteChannelID);
                builder.setContentTitle("Test");
                builder.setContentTitle("Notatka");
                builder.setSmallIcon(R.drawable.ic_launcher_foreground);
                builder.setGroup(GROUP);
                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                builder.setStyle(new NotificationCompat.BigTextStyle().bigText(noteInput.getText().toString()));
                builder.setAutoCancel(false);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
                managerCompat.notify(1, builder.build());
            }
        });

        optionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, options.class));
            }
        });
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
        maxCharactersSetting = readSettings(10);
    }

    int readSettings(int value){
        if(value == 0){
            SharedPreferences sharedprefs = this.getSharedPreferences("settings", Context.MODE_PRIVATE);
            Log.i("max chars", Integer.toString(sharedprefs.getInt("maxCharacters", 0)));
            return sharedprefs.getInt("maxCharacters", 0);
        }
        else if (value == 1)
        {
            SharedPreferences sharedprefs = this.getSharedPreferences("settings", Context.MODE_PRIVATE);
            Log.i("max Lines", Integer.toString(sharedprefs.getInt("maxLines", 0)));
            return sharedprefs.getInt("maxLines", 0);
        }
        else{
            return 0;
        }
    }

}
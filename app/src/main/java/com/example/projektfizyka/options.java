package com.example.projektfizyka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class options extends AppCompatActivity {
    String TestChannelID = "TESTCHANNELID";
    public Button linesTestBtn;
    public Button charactersTestBtn;
    public EditText charactersTestInput;
    public EditText linesTestInput;
    public Button characterPerLineBtn;
    public CheckBox watchSimulationCheckBox;
    @Override
    protected  void onStart() {
        super.onStart();
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Button backBtn;
        backBtn = findViewById(R.id.mainActivityBtn);
        linesTestBtn = findViewById(R.id.testLinesBtn);
        charactersTestBtn = findViewById(R.id.testCharactersBtn);
        charactersTestInput = findViewById(R.id.testCharactersInput);
        linesTestInput = findViewById(R.id.testLinesInput);
        characterPerLineBtn = findViewById(R.id.charactersPerLineTestBtn);
        CheckBox debugModeCheckBox = (CheckBox)findViewById(R.id.debugModeBox);

        Button saveSettingsBtn = (Button)findViewById(R.id.saveSettingsBtn);
        EditText maxCharacters = (EditText)findViewById(R.id.maxCharactersPreference);
        EditText maxLines = (EditText)findViewById(R.id.maxLinesPreference);
        EditText maxCharsPerLine = (EditText)findViewById(R.id.maxCharsPerLine);

        maxCharacters.setText(Integer.toString(readSettings(0)));
        maxLines.setText(Integer.toString(readSettings(1)));
        maxCharsPerLine.setText((Integer.toString(readSettings(2))));

        watchSimulationCheckBox = (CheckBox)findViewById(R.id.watchSimulationCheckBox);
        watchSimulationCheckBox.setChecked(checkWatchSimulation());

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(readSettings(3)== 1)
                    startActivity((new Intent(options.this, watch_simulation_mode.class)));
                else
                    startActivity(new Intent(options.this, MainActivity.class));
            }

        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(TestChannelID, TestChannelID, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel((channel));
        }


        debugModeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                if(ischecked)
                {
                    DebugOptions(true);
                }
                else
                {
                    DebugOptions(false);
                }
            }
        });

        watchSimulationCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });


        saveSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maxCharacters.onEditorAction(EditorInfo.IME_ACTION_DONE);
                SaveToPreferences(maxCharacters, maxLines, maxCharsPerLine);
            }
        });

        charactersTestBtn.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View view) {
                //notka 501 znakow 39 linii amazfit bip u pro- 38 bo 39 jest ledwie widoczna
                String newChars = "";
                for(int x=1;x<=Integer.parseInt(charactersTestInput.getText().toString());x++)
                {
                    if(x%2==0)
                        newChars += "a";
                    else
                        newChars += "b";
                }
                charactersTestBtn.onEditorAction(EditorInfo.IME_ACTION_DONE);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(options.this, TestChannelID);
                builder.setContentTitle("Test");
                builder.setSmallIcon(R.drawable.ic_launcher_foreground);
                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                builder.setStyle(new NotificationCompat.BigTextStyle().bigText(newChars));
                builder.setAutoCancel(false);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(options.this);
                managerCompat.notify(1, builder.build());
            }
        });

        linesTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newChars = "";
                for(int x=1;x<=Integer.parseInt(linesTestInput.getText().toString());x++)
                {
                    newChars += Integer.toString(x)+"\n";
                }
                charactersTestBtn.onEditorAction(EditorInfo.IME_ACTION_DONE);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(options.this, TestChannelID);
                builder.setContentTitle("Test");
                builder.setSmallIcon(R.drawable.ic_launcher_foreground);
                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                builder.setStyle(new NotificationCompat.BigTextStyle().bigText(newChars));
                builder.setAutoCancel(false);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(options.this);
                managerCompat.notify(1, builder.build());
            }
        });

        characterPerLineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newChars = "";
                int corr = 1;
                for(int x=1;x<=100;x++)
                {
                    if(corr>9)
                        corr = 1;
                    newChars += Integer.toString(corr);
                    corr++;
                }
                charactersTestBtn.onEditorAction(EditorInfo.IME_ACTION_DONE);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(options.this, TestChannelID);
                builder.setContentTitle("Test");
                builder.setSmallIcon(R.drawable.ic_launcher_foreground);
                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                builder.setStyle(new NotificationCompat.BigTextStyle().bigText(newChars));
                builder.setAutoCancel(false);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(options.this);
                managerCompat.notify(1, builder.build());

            }
        });

    }

    public void DebugOptions(boolean state)
    {
        if(state)
        {
            charactersTestBtn.setEnabled(true);
            charactersTestInput.setEnabled(true);
            linesTestBtn.setEnabled(true);
            linesTestInput.setEnabled(true);
            characterPerLineBtn.setEnabled(true);
        }
        else
        {
            charactersTestBtn.setEnabled(false);
            charactersTestInput.setEnabled(false);
            linesTestBtn.setEnabled(false);
            linesTestInput.setEnabled(false);
            characterPerLineBtn.setEnabled(false);
        }
    };

    public void SaveToPreferences(EditText maxChars, EditText maxLines, EditText maxCharsPerLine)
    {
        SharedPreferences sharedpref = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpref.edit();
        editor.putInt("maxCharacters", Integer.parseInt(maxChars.getText().toString()));
        Log.i("test", String.valueOf(Integer.parseInt(maxChars.getText().toString())));
        editor.putInt("maxLines", Integer.parseInt(String.valueOf(Integer.parseInt(maxLines.getText().toString()))));
        editor.putInt("maxPerLine", Integer.parseInt(maxCharsPerLine.getText().toString()));
        if(watchSimulationCheckBox.isChecked())
        {
            editor.putInt("watchSimulation", 1);
        }
        else
        {
            editor.putInt("watchSimulation",0);
        }
        editor.apply();
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
            return sharedprefs.getInt("maxPerLine", 0);
        }
        else if (value ==2)
        {
            SharedPreferences sharedprefs = this.getSharedPreferences("settings", Context.MODE_PRIVATE);
            Log.i("max Lines per ", Integer.toString(sharedprefs.getInt("maxPerLine", 0)));
            return sharedprefs.getInt("maxPerLine", 17);
        }
        else if(value == 3)
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

    boolean checkWatchSimulation()
    {
        if(readSettings(3) == 0)
            return false;
        else
            return true;
    }
}
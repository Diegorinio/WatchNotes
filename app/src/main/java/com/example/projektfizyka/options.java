package com.example.projektfizyka;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

public class options extends AppCompatActivity {
    public Button linesTestBtn;
    public Button charactersTestBtn;
    public EditText charactersTestInput;
    public EditText linesTestInput;
    public Button characterPerLineBtn;
    public CheckBox watchSimulationCheckBox;
    UserSettings settings;
    NoteNotification Notification;
    BluetoothStatus BluetoothStatus;
    @Override
    //Ja prdl, okazuje sie ze OnCreate jest na samym poczatku a potem OnStart
    //Pierdole nie chce mi sie juz robic z tego funkcji itd to i tak ustawia tylko sharedpreferences
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        settings = new UserSettings(getApplicationContext());
        settings.init_OptionsGetActivity(this);
        BluetoothStatus = new BluetoothStatus();
        Notification = new NoteNotification("TESTCHANNELID", getApplicationContext());

//        Button updateBtn = (Button)findViewById(R.id.updateBtn);
//        updateBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String url = "https://drive.google.com/drive/folders/1gvbnSvAPGD0pLqk0ynFhbAIvX1djSVxv?usp=share_link";
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
//            }
//        });

        ImageView BT_icon = (ImageView) findViewById(R.id.statusBT);
        BluetoothStatus.ShowBluetoothStatus(BT_icon);

        Button backBtn;
        backBtn = findViewById(R.id.backBtn);
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

        maxCharacters.setText(Integer.toString(settings.getMaxChars()));
        maxLines.setText(Integer.toString(settings.getMaxLines()));
        maxCharsPerLine.setText((Integer.toString(settings.getMaxPerLine())));

        watchSimulationCheckBox = (CheckBox)findViewById(R.id.watchSimulationCheckBox);
        watchSimulationCheckBox.setChecked(settings.isSimulatedMode());

        CheckBox autoFormatMode = (CheckBox)findViewById(R.id.autoFormatModeCheckBox);
        autoFormatMode.setChecked(settings.isFormatModeOn());

        //custom fetch
        CheckBox customFetchCheckBox = (CheckBox)findViewById(R.id.customFetchCheckbox);
        Button OpenUrlBtn = (Button)findViewById(R.id.goToUrlBtn);
        customFetchCheckBox.setChecked(settings.isCustomFetchUrlIsEnabled());
        EditText customFetchUrl = (EditText)findViewById(R.id.customFetchUrl);
        customFetchUrl.setText(settings.getCustomFetchUrl());

        if(customFetchCheckBox.isChecked()){
            customFetchUrl.setEnabled(true);
            OpenUrlBtn.setVisibility(View.VISIBLE);
        }

        customFetchCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(!customFetchCheckBox.isChecked()){
                    customFetchUrl.setEnabled(false);
                    OpenUrlBtn.setVisibility(View.GONE);
                }
                else{
                    AlertDialog.Builder alert = UserInteractions.AlertBuilder(options.this, "WARNING!", "This option is for advanced users");
                    alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            customFetchUrl.setEnabled(true);
                            customFetchCheckBox.setChecked(true);
                            OpenUrlBtn.setVisibility(View.VISIBLE);
                            UserInteractions.SendMessage(getApplicationContext(), "Custom fetch url enabled");
                        }
                    });
                    alert.setNegativeButton("Nope", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            customFetchCheckBox.setChecked(false);
                            OpenUrlBtn.setVisibility(View.GONE);
                        }
                    });
                    alert.show();
                }
            }
        });

        OpenUrlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = settings.getCustomFetchUrl().replace("/notes","");
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("state to load:", String.valueOf(settings.isSimulatedMode()));
                startActivity((new Intent(options.this, MainActivity.class)));
            }

        });
        Notification.SetUpNoteNotificationManager();


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


        saveSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maxCharacters.onEditorAction(EditorInfo.IME_ACTION_DONE);
                Log.i("simulation mode: ", String.valueOf(watchSimulationCheckBox.isChecked()));
                settings.SaveToPreferences(maxCharacters, maxLines, maxCharsPerLine, watchSimulationCheckBox, autoFormatMode, customFetchCheckBox, customFetchUrl);
            }
        });

        charactersTestBtn.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View view) {
                //notka 501 znakow 39 linii amazfit bip u pro- 38 bo 39 jest ledwie widoczna
                Notification.CreateNoteNotification("Test", MaxCharactersTest());
            }
        });

        linesTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notification.CreateNoteNotification("Test", MaxLinesTest());
            }
        });

        characterPerLineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Notification.CreateNoteNotification("Test", MaxCharactersPerLineTest());
            }
        });

    }

    private void DebugOptions(boolean state)
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

    private String MaxCharactersTest()
    {
        String newChars = "";
        for(int x=1;x<=Integer.parseInt(charactersTestInput.getText().toString());x++)
        {
            if(x%2==0)
                newChars += "a";
            else
                newChars += "b";
        }
        return newChars;
    }
    private String MaxLinesTest()
    {
        String newChars = "";
        for(int x=1;x<=Integer.parseInt(linesTestInput.getText().toString());x++)
        {
            newChars += Integer.toString(x)+"\n";
        }
        return newChars;
    }
    private String MaxCharactersPerLineTest()
    {
        String newChars = "";
        int corr = 1;
        for(int x=1;x<=100;x++)
        {
            if(corr>9)
                corr = 1;
            newChars += Integer.toString(corr);
            corr++;
        }
        return newChars;
    }
}
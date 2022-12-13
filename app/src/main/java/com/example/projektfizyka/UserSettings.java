package com.example.projektfizyka;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;

public class UserSettings {

    private Context context;
    private Activity m_Activity;
    private final String SharedPreferencesID = "settings";
    private int maxCharacters;
    private final String MaxCharactersID = "maxCharacters";
    private int maxLines;
    private final String MaxLinesID = "maxLines";
    private int maxPerLine;
    private final String MaxCharsPerLineID = "maxPerLine";
    private int simulationMode;
    private final String SimulationModeID = "watchSimulation";
    private int autoFormatMode;
    private final String AutoFormatModeID = "autoformatMode";
    private final String isCustomFetchUrlEnabledID = "isCustomFetchUrl";
    private boolean isCustomFetchUrl = false;
    private final String CustomFetchUrlID = "CustomFetchUrl";
    private String customFetchUrl;
    private final SharedPreferences sharedpref;
    public UserSettings(Context context)
    {
        this.context = context;
        sharedpref = context.getSharedPreferences(SharedPreferencesID, Context.MODE_PRIVATE);
        init_ReadSharedPreferenceValues();
        ShowMeWhatYouGot();
    }

    public boolean CheckIfPreferencesExists(){
        if(this.sharedpref.contains("maxCharacters")){
            return true;
        }
        else{
            return false;
        }
    }


    public void init_OptionsGetActivity(Activity Activity)
    {
        m_Activity = Activity;
    }


    public void SaveToPreferences( EditText maxChars, EditText maxLines, EditText maxCharsPerLine, CheckBox watchSimulationCheckbox, CheckBox autoFormatModeCheckBox, CheckBox customFetchUrlCheckbox,EditText CustomFetchUrl)
    {
        SharedPreferences.Editor editor = this.sharedpref.edit();
        editor.putInt(MaxCharactersID, Integer.parseInt(maxChars.getText().toString()));
        Log.i(MaxCharactersID, String.valueOf(Integer.parseInt(maxChars.getText().toString())));
        editor.putInt(MaxLinesID, Integer.parseInt(maxLines.getText().toString()));
        editor.putInt(MaxCharsPerLineID, Integer.parseInt(maxCharsPerLine.getText().toString()));
        editor.putBoolean(isCustomFetchUrlEnabledID, customFetchUrlCheckbox.isChecked());
        editor.putString(CustomFetchUrlID, CustomFetchUrl.getText().toString());
        if(watchSimulationCheckbox.isChecked())
        {
            editor.putInt(SimulationModeID, 1);
        }
        else
        {
            editor.putInt(SimulationModeID,0);
        }
        if(autoFormatModeCheckBox.isChecked()){
            editor.putInt(AutoFormatModeID, 1);
        }
        else{
            editor.putInt(AutoFormatModeID, 0);
        }
        editor.apply();
        ShowMeWhatYouGot();
        UserInteractions.SendMessage(context, "Settings saved");
    }

    private void init_ReadSharedPreferenceValues()
    {

        this.maxCharacters = this.sharedpref.getInt(MaxCharactersID, 646);
        this.maxLines = sharedpref.getInt(MaxLinesID, 38);
        this.maxPerLine = sharedpref.getInt(MaxCharsPerLineID, 17);
        this.simulationMode = sharedpref.getInt(SimulationModeID, 0);
        this.autoFormatMode = sharedpref.getInt(AutoFormatModeID, 0);
        this.customFetchUrl = sharedpref.getString(CustomFetchUrlID, "none");
        this.isCustomFetchUrl = sharedpref.getBoolean(isCustomFetchUrlEnabledID, false);
    }

    private void ShowMeWhatYouGot()
    {
        Log.i("shared prefs:", "Max Lines: " + Integer.toString(getMaxLines()) + " Max Characters: " + Integer.toString(getMaxChars())+ " Max Per Line: "+ Integer.toString(getMaxPerLine()) + " Autoformat: " + Boolean.toString(isFormatModeOn()) + " Input watch simulation: " + Boolean.toString(isSimulatedMode()) + " CustomFeth: " + Boolean.toString(isCustomFetchUrlIsEnabled())+ " url: "+ getCustomFetchUrl());
    }

    public int getMaxChars()
    {
        return this.maxCharacters;
    }
    public int getMaxLines()
    {
        return this.maxLines;
    }
    public int getMaxPerLine()
    {
        return this.maxPerLine;
    }
    public boolean isSimulatedMode() {
        if (simulationMode == 1)
            return true;
        else
            return false;
    }

    public boolean isFormatModeOn(){
        if(autoFormatMode==1){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isCustomFetchUrlIsEnabled(){
        return sharedpref.getBoolean(isCustomFetchUrlEnabledID, false);
    }
    public String getCustomFetchUrl(){
        return sharedpref.getString(CustomFetchUrlID, "https://diegorinio.github.io/");
    }

}

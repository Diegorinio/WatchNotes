package com.example.projektfizyka;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
public class NotesFilesPreferences extends NotesFilesManager{
    private Context _Context;
    private final SharedPreferences sharedpref;
    private final String SharedPreferencesID = "FilesList";
    public NotesFilesPreferences(Context context){
        super(context);
        this._Context = context;
        this.sharedpref = _Context.getSharedPreferences(SharedPreferencesID, Context.MODE_PRIVATE);
        if(!CheckIfFilesListExists()){
            SetDefaultPreferences();
        }
        ShowMeWhatYouGot();
    }

    public void AddValueToFileNamesPreferences(String title){
        SharedPreferences.Editor editor = this.sharedpref.edit();
        char split_mark = ';';
        String act_string = GetFilesNameList()+title+split_mark;
        editor.putString(SharedPreferencesID, act_string);
        Log.i("File title", act_string);
        editor.apply();
        ShowMeWhatYouGot();
    }

    public void SaveToFile(String Title, String content){
        WriteToFile(Title, content);
    }

    public String ReadContentFromFile(String Filename){
        return ReadFromFile(_Context, Filename);
    }

    public void ShowMeWhatYouGot(){
        Log.i("Files List", GetFilesNameList());
    }

    public String[] GetFilesNamesArray(){
        String list = this.sharedpref.getString(SharedPreferencesID,"");
        String[] FilesName = list.split(";");
        return FilesName;
    }
    private void SetDefaultPreferences(){
        SharedPreferences.Editor editor = this.sharedpref.edit();
        editor.putString(SharedPreferencesID, "");
        editor.apply();
    }

    private String GetFilesNameList(){
        String list = this.sharedpref.getString(SharedPreferencesID,"");
        return list;
    }


    private int GetFilesNameListLength(){
        String list = this.sharedpref.getString(SharedPreferencesID,"");
        return list.length();
    }

    private boolean CheckIfFilesListExists(){
        if(this.sharedpref.contains(SharedPreferencesID)){
            return true;
        }
        else{
            return false;
        }
    }
}

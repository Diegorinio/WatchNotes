package com.example.projektfizyka;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class NotesFilesPreferences extends NotesFilesManager{
    private Context _Context;
    private final SharedPreferences sharedpref;
    private final String SharedPreferencesID = "FilesList";
    private char split_mark = ':';
    public NotesFilesPreferences(Context context){
        super(context);
        this._Context = context;
        this.sharedpref = _Context.getSharedPreferences(SharedPreferencesID, Context.MODE_PRIVATE);
        if(!CheckIfFilesListExists()){
            SetDefaultPreferences();
        }
        ShowMeWhatYouGot();
    }

    public void AddValueToFileNamesPreferences(String title, String content){
        SharedPreferences.Editor editor = this.sharedpref.edit();
        String form_title = title.replaceAll(" ", "");
        String act_string = form_title+split_mark+GetFilesNameList();
        editor.putString(SharedPreferencesID, act_string);
        Log.i("File title", act_string);
        editor.apply();
        SaveToFile(form_title, content);
        UserInteractions.SendMessage(_Context, "File saved");
        ShowMeWhatYouGot();
    }

    public void RemoveFile(String filename){
        SharedPreferences.Editor editor = this.sharedpref.edit();
        String list = GetFilesNameList();
        String new_list = list.replace(filename+split_mark,"");
        editor.putString(SharedPreferencesID, new_list);
        editor.apply();
        DeleteFile(filename);
        ShowMeWhatYouGot();
        UserInteractions.SendMessage(_Context, "File removed");
    }

    private void SaveToFile(String Title, String content){
        WriteToFile(Title, content);
    }

    public String ReadContentFromFile(String Filename)
    {
        return ReadFromFile(_Context, Filename);
    }

    private void DeleteFile(String filename){
        DeleteFile(_Context, filename);
        Log.i("Remove file", filename+".txt");
    }

    private void ShowMeWhatYouGot(){
        Log.i("Files List", GetFilesNameList());
    }

    public String[] GetFilesNamesArray(){
        String list = this.sharedpref.getString(SharedPreferencesID,"");
        Log.i("No kurwa mac", list);
        String[] FilesName = list.split(":");
        List<String> result = new ArrayList<>();
        return FilesName;
    }
    private void SetDefaultPreferences(){
        SharedPreferences.Editor editor = this.sharedpref.edit();
        editor.putString(SharedPreferencesID, "");
        editor.apply();
    }

    public String GetFilesNameList(){
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


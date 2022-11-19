package com.example.projektfizyka;

import android.content.Context;
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

    public void AddValueToFileNamesPreferences(String title){
        SharedPreferences.Editor editor = this.sharedpref.edit();
        String act_string = title+split_mark+GetFilesNameList();
        editor.putString(SharedPreferencesID, act_string);
        Log.i("File title", act_string);
        editor.apply();
        ShowMeWhatYouGot();
    }

    private void RemoveFileNameFromPreferences(String title){
        SharedPreferences.Editor editor = this.sharedpref.edit();
        String list = GetFilesNameList();
        String new_list = list.replace(title+split_mark,"");
        editor.putString(SharedPreferencesID, new_list);
        editor.apply();
        ShowMeWhatYouGot();
    }

    public void SaveToFile(String Title, String content){
        WriteToFile(Title, content);
    }

    public String ReadContentFromFile(String Filename){
        return ReadFromFile(_Context, Filename);
    }

    public void DeleteFile(String filename){
        DeleteFile(_Context, filename);
        RemoveFileNameFromPreferences(filename);
        Log.i("Remove file", filename+".txt");
    }

    private void ShowMeWhatYouGot(){
        Log.i("Files List", GetFilesNameList());
    }

    public String[] GetFilesNamesArray(){
        String list = this.sharedpref.getString(SharedPreferencesID,"");
        Log.i("No kurwa mac", list);
        String[] FilesName = list.split(":");
//        String[] result = new String[0];
        List<String> result = new ArrayList<>();
//        for(int x=0; x<=FilesName.length-1;x++){
//            if(FilesName[x]!=null || FilesName[x].length()!=0){
//                result.set(x, FilesName[x]);
//            }
//        }
//        for(String i:FilesName){
//            Log.i("Araj:", i);
//            Log.i("araj len", Integer.toString(FilesName.length));
//        }
//        for(Object x: result.toArray()){
//            Log.i("araj2", x.toString());
//        }
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

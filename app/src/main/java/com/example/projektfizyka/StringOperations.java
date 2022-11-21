package com.example.projektfizyka;

import android.util.Log;

import org.json.*;

public class StringOperations {
    public static String FormatStringOutputAuto(String Input){
        String input = Input;
        String[] inputArray;
        String result = "";
        //zrob jako array
        inputArray = input.replaceAll(" ", ";").replaceAll("\n", "").replaceAll("\r", "").split(";");
        for(int x=0;x<=inputArray.length-1;x++){
            String new_string = inputArray[x].substring(0,1).toUpperCase()+inputArray[x].substring(1);
            inputArray[x] = new_string;
            result+=inputArray[x];
        }
        return result;
    }

    public static JSONArray ReadFromJsonString(String inputJsonString) throws JSONException {
        String data = null;
        String[] readElements = null;
        JSONObject reader = new JSONObject(inputJsonString);
//        JSONArray araj = new JSONArray(inputJsonString);
        JSONArray result = reader.getJSONArray("notes");
        Log.i("dzejson", result.getJSONObject(1).getString("subject").toString());
        for(int j=0;j<=result.length()-1;j++){
            Log.i("Elements", String.valueOf(result.getString(j)));
        }
        return result;
    }

}

package com.example.projektfizyka;

import android.util.Log;

import org.json.*;

public class StringOperations {
    public static String FormatStringOutputAuto(String Input){
        String input = Input;
        String[] inputArray;
        String result = "";
        //zrob jako array
        inputArray = input.replaceAll(" ", "///").replaceAll("\n", "").replaceAll("\r", "").split("///");
        for(String i: inputArray) {
            Log.i("String array:", i);
        }
        Log.i("Len:", Integer.toString(inputArray.length));
        for(int x=0;x<=inputArray.length-1;x++){
            if(inputArray[x].length()>0) {
                String new_string = inputArray[x].substring(0, 1).toUpperCase()+inputArray[x].substring(1);
                inputArray[x] = new_string;
                result += inputArray[x];
                result.replaceAll("\n", "");
            }
        }
        Log.i("result:", result);
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

    public static String[] SplitNotificationsToStringLength(String Input, int MaxCharacters){
        if(Input.length()>MaxCharacters){
            int split_count = Input.length()/MaxCharacters;
            split_count+=1;
            Log.i("Split array: ", Integer.toString(split_count));
            int rest = (Input.length()-MaxCharacters)/2;
            String[] result = new String[split_count];
            Log.i("araj:", Integer.toString(result.length));
            int turns = split_count;
            int max = Input.length();
            for(int x=1;x<=turns;x++){
                String id = Integer.toString(x)+"#";
                int id_len = id.length();
                if(x==1){
                    result[x-1] = Input.substring(0, MaxCharacters-id_len);
                }
                else if(x==turns){
                    result[x-1] = Input.substring(MaxCharacters*(x-1)-id_len, max);
                }
                else{
                    result[x-1] = Input.substring(MaxCharacters*(x-1)-id_len,MaxCharacters*x-id_len);
                }
                result[x-1] = id+result[x-1];
            }
            return result;
        }else
        {
            String[] result = new String[1];
            result[0] = Input;
            return result;
        }
    }

}

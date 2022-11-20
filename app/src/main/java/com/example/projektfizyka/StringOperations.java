package com.example.projektfizyka;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import org.w3c.dom.Text;

public class StringOperations {
    private String lorem= "Potezna wichura lamiac duze drzewa trzcina zaledwie tylko kolysze. Uwazaj uwazaj, kutang panie to nie ma sansu. Ty kurwa Efbijaj";
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

}

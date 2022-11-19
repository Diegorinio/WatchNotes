package com.example.projektfizyka;

import android.content.Context;
import android.widget.Toast;

public interface UserInteractions {
    default void SendResponseToEvent(Context context, String response){
        Toast.makeText(context, response, Toast.LENGTH_LONG).show();
    }
}

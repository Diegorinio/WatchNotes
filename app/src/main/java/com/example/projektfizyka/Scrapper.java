package com.example.projektfizyka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Scrapper extends AppCompatActivity {
    TextView Essa;
    TextView noteContent;
    JSONArray dzejson;
    NotesFilesPreferences NoteFiles;
    UserSettings settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrapper);
        settings = new UserSettings(getApplicationContext());
        NoteFiles = new NotesFilesPreferences(getApplicationContext());
        Button backBtn = (Button)findViewById(R.id.backBtn);
        Button saveBtn = (Button)findViewById(R.id.saveBtn);
        noteContent = findViewById(R.id.scrappedNoteContent);
        Essa = findViewById(R.id.scrappedNoteTitle);
        Scrapping scrap = new Scrapping();
        scrap.execute();


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Scrapper.this, MainActivity.class));
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = Essa.getText().toString();
                String content = noteContent.getText().toString();
                if(title.length()<=0 && content.length()<=0){
                    UserInteractions.SendMessage(getApplicationContext(), "No title or content");
                }
                else {
                    NoteFiles.AddValueToFileNamesPreferences(title, content);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
//        try {
//            GenerateNotes(dzejson);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    private class Scrapping extends  AsyncTask<Void, Void, Void >{

        @Override
        protected Void doInBackground(Void... voids) {
            Document doc;
            String url = "https://magicznykasztan.github.io/";
            try{
                if(settings.isCustomFetchUrlIsEnabled()){
                    url = settings.getCustomFetchUrl();
                }
                doc = Jsoup.connect(url).get();
                String title = doc.title();
//                Essa.setText(title);
                Elements element = doc.select("body");
                Log.i("Title",element.text());
                JSONArray result = StringOperations.ReadFromJsonString(element.text());
//                noteContent.setText(result.getJSONObject(0).getString("note_content"));
//                Essa.setText(result.getJSONObject(0).getString("note_title"));
                dzejson = result;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            try {
                if(dzejson !=null){
                    GenerateNotes(dzejson);
                }else{
                    UserInteractions.SendMessage(getApplicationContext(), "Failed to receive data");
                    startActivity(new Intent(Scrapper.this, MainActivity.class));
                }
            } catch (JSONException e) {
                UserInteractions.SendMessage(getApplicationContext(), "Failed to receive data");
                startActivity(new Intent(Scrapper.this, MainActivity.class));
//                e.printStackTrace();
            }
        }
    }

    private void GenerateNotes(JSONArray Notes) throws JSONException {
        LinearLayout list =(LinearLayout) findViewById(R.id.Linear);
        Essa = findViewById(R.id.scrappedNoteTitle);
        noteContent = findViewById(R.id.scrappedNoteContent);
        for(int x = 0; x<=Notes.length()-1; x++){
            String title = Notes.getJSONObject(x).getString("note_title");
            String content = Notes.getJSONObject(x).getString("note_content");
            Button newBtn = CreateElementButton(title);
            newBtn.setBackgroundResource(R.drawable.note);
            list.addView(newBtn);
//            TextView newView = CreateTextView(content);
//            list.addView(newView);

            newBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    noteContent.setText(content);
                    Essa.setText(title);
                }
            });
        }
    }
    private Button CreateElementButton(String BtnText){
        Button NewBtn = new Button(this);
        NewBtn.setText(BtnText);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,20,0,10);
        NewBtn.setLayoutParams(params);
        return NewBtn;
    }

    private TextView CreateTextView(String TextViewContent){
        TextView newView = new TextView(this);
        newView.setText(TextViewContent);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        newView.setLayoutParams(params);
        return newView;
    }

}
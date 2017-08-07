package com.example.arnarfreyr.dicegame2000;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Scores extends AppCompatActivity {

    ListView lstScores;
    ArrayList<UserData> scores;
    List<String> names = new ArrayList<>();
    List<Integer> score = new ArrayList<>();
    ListAdapter adapter;

    SQLstatements sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        sql = new SQLstatements(this);
        scores = new ArrayList<UserData>();
        getScores();

        adapter = new ListAdapter(Scores.this, names, score);
        lstScores = (ListView)findViewById(R.id.lstScores);
        lstScores.setAdapter(adapter);
    }

    @Override
    public void onBackPressed(){
        Intent main = new Intent( this, MainActivity.class );
        startActivity(main);
    }

    private void getScores() {
        scores = sql.getAllHighScores();

        for(int i = 0; i < scores.size(); i++) {
            names.add(scores.get(i).getName());
            score.add(scores.get(i).getScore());
        }
    }
}

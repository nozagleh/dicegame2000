package com.example.arnarfreyr.dicegame2000;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Start the main gaim activity.
     * @param view
     */
    public void startGame(View view) {
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }

    /**
     * Start the highscore activity.
     * @param view
     */
    public void viewHighscore(View view) {
        Intent intent = new Intent(this, Scores.class);
        startActivity(intent);
    }
}

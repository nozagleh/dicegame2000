package com.example.arnarfreyr.dicegame2000;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.arnarfreyr.dicegame2000.R.id.lstScores;

public class RegisterScore extends AppCompatActivity {

    TextView txtCongratz;
    TextView txtScore;
    EditText etName;
    ListView lstRounds;
    Button btnSubmit;

    ArrayList<Integer> totalScores;
    Integer totalScore;

    List<String> rounds;

    SQLstatements sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_score);

        txtCongratz = (TextView)findViewById(R.id.txtCongratz);
        txtScore = (TextView)findViewById(R.id.txtScore);
        etName = (EditText)findViewById(R.id.etName);
        lstRounds = (ListView)findViewById(R.id.lstRounds);
        btnSubmit = (Button)findViewById(R.id.btnSubmit);

        Intent intent = getIntent();
        ListAdapter adapter;

        rounds = new ArrayList<>();
        totalScores = intent.getIntegerArrayListExtra("score_list");
        Log.d("ASD --> ", String.valueOf(totalScores.size()));
        for (int i = 0; i < totalScores.size(); i++) {
            rounds.add(i, "Round " + (i+1));
        }
        adapter = new ListAdapter(RegisterScore.this,rounds,totalScores);
        lstRounds.setAdapter(adapter);

        txtCongratz.setText(R.string.congratulations);

        totalScore = intent.getIntExtra("total_score",0);
        txtScore.setText(totalScore.toString());

        sql = new SQLstatements(this);


        /*ListAdapter adapter;
        adapter = new ListAdapter(RegisterScore.this, names, score);
        lstRounds = (ListView)findViewById(R.id.lstRounds);
        lstRounds.setAdapter(adapter);*/


    }

    public void submitScore( View view ) {
        if ( !(etName.length() > 0) ) {
            return;
        }

        UserData user = new UserData();

        user.setName(etName.getText().toString());
        user.setScore(Integer.parseInt(txtScore.getText().toString()));
        Date date = new Date();
        user.setDate(date.toString());

        sql.addHighScore(user);

        Intent highscore = new Intent(this, Scores.class);
        startActivity(highscore);
    }
}

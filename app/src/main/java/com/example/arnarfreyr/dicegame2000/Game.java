package com.example.arnarfreyr.dicegame2000;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.annotation.BoolRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game extends AppCompatActivity {

    SQLstatements sql;

    private SparseArray<ImageView> dicesImgs;

    private SparseIntArray imgFiles = new SparseIntArray();

    Spinner lstBets;

    Button btnSubmit;

    TextView txtRound;

    ProgressBar pbRounds;

    DiceGroup dg;

    GamePlay gp;

    ArrayList<String> bets;

    ArrayAdapter<String> betsAdapter;

    Integer rollNr;

    ArrayList<Integer> prevDices;

    Boolean isGameFinished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        txtRound = (TextView)findViewById(R.id.txtRound);
        pbRounds = (ProgressBar)findViewById(R.id.pbRounds);

        sql = new SQLstatements(this);

        loadAssets();

        startGame();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("PAUSED --> ", "yes");
        Integer id  = sql.getGameId(1);
        Log.d("GAME ID --> ", id.toString());
        sql.addGame(id, 0);
        savePreferences();

    }

    @Override
    protected  void onResume() {
        super.onResume();

        Log.d("RESUMED --> ", "yes");

    }

    private void savePreferences() {
        int rollNr = this.gp.getRoll();
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("rollNr", rollNr);
        editor.putBoolean("isGameFinished", false);
        int count = 0;
        for( Dice die : this.dg.getDices() ) {
            editor.putInt("dice" + count, die.getDice());
            count++;
        }

        editor.apply();
    }

    private void loadPreferences() {
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        int count = 0;
        for( Dice die : this.dg.getDices()) {
            die.setDice(sp.getInt("dice" + count, 1));
            count++;
        }
        for(int i = 0; i < sp.getInt("rollNr", 0); i++) {
            gp.addRoll();
        }
        this.isGameFinished = sp.getBoolean("isGameFinished", true);
        Log.d("FINISHED -----> ", String.valueOf(sp.getBoolean("isGameFinished",true)));
    }

    /**
     *
     */
    private void loadAssets(){
        // Init the ImageViev array to hold the views
        this.dicesImgs = new SparseArray<>();


        // Init the views themselves
        ImageView diceImg0 = (ImageView)findViewById(R.id.diceImg0);
        ImageView diceImg1 = (ImageView)findViewById(R.id.diceImg1);
        ImageView diceImg2 = (ImageView)findViewById(R.id.diceImg2);
        ImageView diceImg3 = (ImageView)findViewById(R.id.diceImg3);
        ImageView diceImg4 = (ImageView)findViewById(R.id.diceImg4);
        ImageView diceImg5 = (ImageView)findViewById(R.id.diceImg5);

        // Add all the ImageView's to the array
        dicesImgs.put(0, diceImg0);
        dicesImgs.put(1, diceImg1);
        dicesImgs.put(2, diceImg2);
        dicesImgs.put(3, diceImg3);
        dicesImgs.put(4, diceImg4);
        dicesImgs.put(5, diceImg5);

        // Add red dices
        imgFiles.put(0, R.drawable.red1);
        imgFiles.put(1, R.drawable.red2);
        imgFiles.put(2, R.drawable.red3);
        imgFiles.put(3, R.drawable.red4);
        imgFiles.put(4, R.drawable.red5);
        imgFiles.put(5, R.drawable.red6);
        // Add gray dices
        imgFiles.put(6, R.drawable.grey1);
        imgFiles.put(7, R.drawable.grey2);
        imgFiles.put(8, R.drawable.grey3);
        imgFiles.put(9, R.drawable.grey4);
        imgFiles.put(10, R.drawable.grey5);
        imgFiles.put(11, R.drawable.grey6);

        // Bind the images to the dices
        Dice dice0 = new Dice();
        dice0.bindImage(diceImg0);
        Dice dice1 = new Dice();
        dice1.bindImage(diceImg1);
        Dice dice2 = new Dice();
        dice2.bindImage(diceImg2);
        Dice dice3 = new Dice();
        dice3.bindImage(diceImg3);
        Dice dice4 = new Dice();
        dice4.bindImage(diceImg4);
        Dice dice5 = new Dice();
        dice5.bindImage(diceImg5);

        this.dg = new DiceGroup();

        dg.setDice(dice0);
        dg.setDice(dice1);
        dg.setDice(dice2);
        dg.setDice(dice3);
        dg.setDice(dice4);
        dg.setDice(dice5);

        // Init the spinner(dropdown) for the bet types
        lstBets = (Spinner)findViewById(R.id.lstBets);

        // Create an adapter for the spinner,
        // set the layout and string array
        String[] arrBets = getResources().getStringArray(R.array.bets_array);
        this.bets = new ArrayList<>();
        for (int i = 0; i < arrBets.length; i++) {
            Log.d("ITEM -->", arrBets[i]);
            this.bets.add(arrBets[i]);
        }
        //Collections.addAll(this.bets, arrBets);

        betsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, this.bets);
        lstBets.setAdapter(betsAdapter);

        // Init a new instance of the click listener for the spinner
        ListBetsActivity lba = new ListBetsActivity(this.gp);
        // Tie the spinner and listener together
        lstBets.setOnItemSelectedListener(lba);
    }

    private void startGame(){
        gp = new GamePlay();
        loadPreferences();
        gp.setStarted(isGameFinished);
        Log.d("GAME FINISHED --> ", isGameFinished.toString());
        if(!gp.getStarted()) {
            Integer gameId = sql.getGameId(1);
            ArrayList<Integer> roundsBets = sql.getRoundBets(gameId);
            ArrayList<Integer> roundsScore = sql.getRoundScore(gameId);

            for (Integer round: roundsScore) {
                gp.addPoints(round);
                gp.saveDices(dg);
                gp.addRoundScore(round);
                gp.addRound();
            }

            int count = 0;
            for (Integer bet : roundsBets) {
                gp.addDoneBet(bet);
                count++;
            }

        }else {
            gp.addRound();
            sql.addGame(1);
        }

        Log.d("ROUND NR --> ", gp.getRoundNr().toString());
        updateButton();
    }

    public void rollDices() {
        for(Integer i = 0; i < this.dg.length(); i++) {
            Dice die = this.dg.getDice(i);
            if( !die.getChosen() ) {
                die.rollDice();
                die.updateImage(this.imgFiles.get(die.getDice() - 1));
            }
        }
        updateButton();
    }

    public void clickRoll( View view ) {
        // Check if rounds are over
        Log.d("Round nr --> ", gp.getRoundNr().toString());
        if( this.gp.roundsFinished() ) {
            endGame();
        }else {
            if (!this.gp.getStarted()) {
                this.gp.setStarted(true);
            }
            gp.setBetType(lstBets.getSelectedItemPosition());
            gp.addRoll();

            if ( gp.rollAllowed() ) {
                rollDices();
            }else {
                startNewRound();
            }

            if ( gp.isLastRoll() && !gp.roundsFinished() ) {
                btnSubmit.setText(R.string.btn_new_round);
            }else if( gp.roundsFinished() ) {
                btnSubmit.setText(R.string.btn_go_to_score);
            }
        }
    }

    public void clickDie( View view ) {
        if ( gp.getStarted() ) {
            for (int i = 0; i < this.dicesImgs.size(); i++) {
                if (this.dicesImgs.get(i).getId() == view.getId()) {
                    Dice die = this.dg.getDice(i);
                    if ( die.getChosen() ) {
                        die.updateImage(this.imgFiles.get(die.getDice() - 1));
                        die.setChosen(false);
                    }else {
                        die.updateImage(this.imgFiles.get(die.getDice() - 1 + 6));
                        die.setChosen(true);
                    }
                }
            }
        }
    }

    public void updateButton() {
        if ( !gp.isLastRoll() && !gp.roundsFinished() ) {
            Integer rl = gp.getRollsLeft();
            String s = getResources().getString(R.string.btn_roll);
            s += " (";
            s += rl.toString();
            s += ")";

            String roundText = "Round " + gp.getRoundNr().toString() + "/" + gp.getMaxRounds().toString();
            txtRound.setText(roundText);

            pbRounds.setProgress(gp.getRoundNr());

            btnSubmit.setText(s);

        } else if ( gp.isLastRoll() && gp.roundsFinished() ) {
            btnSubmit.setText(R.string.btn_go_to_score);
        }
    }

    public void resetImages() {
        for (Dice die: this.dg.getDices() ) {
            die.updateImage(this.imgFiles.get(die.getDice() - 1));
            die.setChosen(false);
        }
    }

    public void startNewRound() {
        // TODO start new round
        // Init a new instance of the click listener for the spinner
        ListBetsActivity lba = new ListBetsActivity(this.gp);
        // Tie the spinner and listener together
        lstBets.setOnItemSelectedListener(lba);
        if( gp.isBetDone(lstBets.getSelectedItemPosition()) ) {
            Toast toast = Toast.makeText(this, "Bet already chosen, choosen another!", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        gp.addDoneBet(lstBets.getSelectedItemPosition());
        gp.saveDices(dg);
        gp.saveRoundScore();

        int[] scores = gp.getRoundScores();
        Integer gameNr = sql.getGameId(1);
        Log.d("SSS --> ", gp.getRoundNr().toString());
        sql.addRound(lstBets.getSelectedItemPosition(),scores[gp.getRoundNr() -1 ], gameNr);

        gp.addRound();


        gp.resetRolls();
        gp.resetScore();
        updateButton();
        resetImages();

    }

    public void endGame() {
        Intent intent = new Intent(this, RegisterScore.class);
        intent.putExtra("total_score", this.gp.getTotalScore());
        intent.putIntegerArrayListExtra("score_list", this.gp.getScores());
        Integer id  = sql.getGameId(1);

        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isGameFinished", true);
        editor.apply();

        Log.d("END GAME ID ---> ", id.toString());
        sql.addGame(id, 1);
        startActivity(intent);
    }


    /*public void submitScore(View view) {
        UserData user = new UserData();

        user.setName(txtName.getText().toString());
        user.setScore(Integer.parseInt(txtScore.getText().toString()));
        Date date = new Date();
        user.setDate(date.toString());

        sql.putHighScore(user);
    }*/
}

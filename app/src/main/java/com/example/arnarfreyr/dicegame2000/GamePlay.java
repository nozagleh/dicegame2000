package com.example.arnarfreyr.dicegame2000;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnarfreyr on 1.7.2017.
 */

public class GamePlay {

    private static Integer MAX_THROWS = 3;

    private static Integer MAX_ROUNDS = 10;

    private Integer betType;

    private List<DiceGroup> diceGroups;
    private ArrayList<Integer> roundScores;
    private ArrayList<Integer> chosenBets;

    private Boolean started;

    private Integer throwNr = 0;
    private Integer roundNr = 0;
    private Integer points = 0;

    public GamePlay(){
        this.betType = 0;
        this.diceGroups = new ArrayList<>();
        this.roundScores = new ArrayList<>();
        this.chosenBets = new ArrayList<>();
        this.started = false;
    }

    public void saveDices( DiceGroup dg ) {
        this.diceGroups.add( dg );
    }

    public void removeDices( DiceGroup dg ) {
        this.diceGroups.remove( dg );
    }

    public List<DiceGroup> getDices() {
        return this.diceGroups;
    }

    public void setBetType( Integer betType ) {
        this.betType = betType;
    }

    public Integer getBetType() {
        return this.betType;
    }

    public Boolean getStarted() {
        return this.started;
    }

    public void setStarted( Boolean started ) {
        this.started = started;
    }

    public void addRoll() {
        this.throwNr++;
    }

    public Integer getRoll() {
        return this.throwNr;
    }

    public Boolean rollAllowed() {
        Log.d("ROLL NR --> ", this.throwNr.toString());
        if( this.throwNr <= MAX_THROWS ) {
            return true;
        }
        return false;
    }

    public Boolean isLastRoll() {
        if ( this.throwNr.equals(MAX_THROWS) ) {
            return true;
        }
        return false;
    }

    public Integer getRollsLeft() {
        return MAX_THROWS - this.throwNr;
    }

    public void addRound() {
        this.roundNr++;
    }

    public Boolean roundsFinished() {
        if ( this.roundNr > MAX_ROUNDS ) {
            return true;
        }
        return false;
    }

    public Integer getRoundNr() {
        return this.roundNr;
    }

    public Integer getMaxRounds() {
        return MAX_ROUNDS;
    }

    public Integer getRoundsLeft() {
        return MAX_ROUNDS - this.roundNr;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public Integer getPoints() {
        return this.points;
    }

    public void saveRoundScore() {
        DiceGroup roundGroup = this.diceGroups.get(getRoundNr() - 1);
        List<Dice> dg = roundGroup.getDices();
        Log.d("Bet type --> ",getBetType().toString());
        if ( getBetType() < 1 ) {
            for (Dice dice : dg) {
                if ( dice.getDice() <= 3 ) {
                    addPoints(1);
                }
            }
        }else {
            int betValue = getBetType() + 3;
            DiceGroup usedDices = new DiceGroup();
            for(Integer i = 0; i < dg.size(); i++){
                Dice dice1 = dg.get(i);
                Log.d("DICE1 ---> ", dice1.getDice().toString());
                if (dice1.getDice() == betValue
                        && !usedDices.hasDice(dice1)) {
                    Log.d("D1# ->", i.toString());
                    Log.d("D1 MATCH--->", dice1.getDice().toString());
                    addPoints(1);
                    usedDices.setDice(dice1);
                    continue;
                }
                for(Integer x = i+1; x < dg.size(); x++){
                    Dice dice2 = dg.get(x);
                    Log.d("DICE2 ---> ", dice2.getDice().toString());
                    if (dice1.getDice() + dice2.getDice() == betValue
                            && !usedDices.hasDice(dice1) && !usedDices.hasDice(dice2)) {
                        Log.d("D1# ->", i.toString());
                        Log.d("D2# ->", x.toString());
                        Log.d("D1+D2 MATCH --->", dice2.getDice().toString());
                        usedDices.setDice(dice1);
                        usedDices.setDice(dice2);
                        addPoints(1);
                        continue;
                    }
                    for(Integer y = x+1; y < dg.size(); y++){
                        Dice dice3 = dg.get(y);
                        //Log.d("DICE3 ---> ", dice3.getDice().toString());
                        if (dice1.getDice() + dice2.getDice() + dice3.getDice() == betValue
                                && !usedDices.hasDice(dice1) && !usedDices.hasDice(dice2) && !usedDices.hasDice(dice3)) {
                            Log.d("D1# ->", i.toString());
                            Log.d("D2# ->", x.toString());
                            Log.d("D3# ->", y.toString());
                            Log.d("D1+D2+D3 --->", dice3.getDice().toString());
                            usedDices.setDice(dice1);
                            usedDices.setDice(dice2);
                            usedDices.setDice(dice3);
                            addPoints(1);
                            continue;
                        }
                        for(Integer z = y+1; z < dg.size(); z++){
                            Dice dice4 = dg.get(z);
                            //Log.d("DICE4 ---> ", dice4.getDice().toString());
                            if (dice1.getDice() + dice2.getDice() + dice3.getDice() + dice4.getDice() == betValue
                                    && !usedDices.hasDice(dice1) && !usedDices.hasDice(dice2)
                                    && !usedDices.hasDice(dice3) && !usedDices.hasDice(dice4)) {
                                Log.d("D1# ->", i.toString());
                                Log.d("D2# ->", x.toString());
                                Log.d("D3# ->", y.toString());
                                Log.d("D4# ->", z.toString());
                                Log.d("D1+D2+D3+D4 --->", dice4.getDice().toString());
                                usedDices.setDice(dice1);
                                usedDices.setDice(dice2);
                                usedDices.setDice(dice3);
                                usedDices.setDice(dice4);
                                addPoints(1);
                                continue;
                            }
                            for(Integer a = z+1; a < dg.size(); a++){
                                Dice dice5 = dg.get(a);
                                //Log.d("DICE5 ---> ", dice5.getDice().toString());
                                if (dice1.getDice() + dice2.getDice()
                                        + dice3.getDice() + dice4.getDice()
                                        + dice5.getDice() == betValue
                                        && !usedDices.hasDice(dice1) && !usedDices.hasDice(dice2)
                                        && !usedDices.hasDice(dice3) && !usedDices.hasDice(dice4)
                                        && !usedDices.hasDice(dice5)) {
                                    Log.d("D1# ->", i.toString());
                                    Log.d("D2# ->", x.toString());
                                    Log.d("D3# ->", y.toString());
                                    Log.d("D4# ->", z.toString());
                                    Log.d("D5# ->", a.toString());
                                    Log.d("D1+D2+D3+D4+D5 --->", dice5.getDice().toString());
                                    usedDices.setDice(dice1);
                                    usedDices.setDice(dice2);
                                    usedDices.setDice(dice3);
                                    usedDices.setDice(dice4);
                                    usedDices.setDice(dice5);
                                    addPoints(1);
                                    continue;
                                }
                                for(Integer b = a+1; b < dg.size(); b++){
                                    Dice dice6 = dg.get(b);
                                    //Log.d("DICE6 ---> ", dice6.getDice().toString());
                                    if (dice1.getDice() + dice2.getDice()
                                            + dice3.getDice() + dice4.getDice()
                                            + dice5.getDice() + dice6.getDice() == betValue
                                            && !usedDices.hasDice(dice1) && !usedDices.hasDice(dice2)
                                            && !usedDices.hasDice(dice3) && !usedDices.hasDice(dice4)
                                            && !usedDices.hasDice(dice5) && !usedDices.hasDice(dice6)) {
                                        Log.d("D1# ->", i.toString());
                                        Log.d("D2# ->", x.toString());
                                        Log.d("D3# ->", y.toString());
                                        Log.d("D4# ->", z.toString());
                                        Log.d("D5# ->", a.toString());
                                        Log.d("D6# ->", b.toString());
                                        Log.d("D1+D2+D3+D4+D5+D6 --->", dice6.getDice().toString());
                                        usedDices.setDice(dice1);
                                        usedDices.setDice(dice2);
                                        usedDices.setDice(dice3);
                                        usedDices.setDice(dice4);
                                        usedDices.setDice(dice5);
                                        usedDices.setDice(dice6);
                                        addPoints(1);
                                    }
                                }
                            }
                        }
                    }
                }
                //Log.d("FIRST ----> ", "LOOP");
            }
        }
        this.roundScores.add(getPoints());
        Log.d("CHOSEN BET ---> ", getBetType().toString());
        Log.d("TOTAL SCORE -->", getPoints().toString());
    }

    public Integer getTotalScore() {
        Integer total = 0;
        for (Integer score : this.roundScores ) {
            total += score;
        }
        return total;
    }

    public ArrayList<Integer> getScores() {
        return this.roundScores;
    }

    public void resetRolls() {
        this.throwNr = 0;
    }

    public void resetScore() {
        this.points = 0;
    }

    public int[] getRoundScores() {
        int[] roundScores = new int[this.roundScores.size()];
        for (int i = 0; i < roundScores.length; i++) {
            roundScores[i] = this.roundScores.get(i);
        }

        return roundScores;
    }

    public void addRoundScore( int score ) {
        this.roundScores.add(score);
    }

    public void addDoneBet( Integer betNr ) {
        this.chosenBets.add(betNr);
    }

    public boolean isBetDone( int betNr ) {
        if ( this.chosenBets.contains(betNr) ) {
            return true;
        }
        return false;
    }
}

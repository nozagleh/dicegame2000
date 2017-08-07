package com.example.arnarfreyr.dicegame2000;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnarfreyr on 28.6.2017.
 */

public class DiceGroup {

    private List<Dice> dices;

    public DiceGroup() {
        this.dices = new ArrayList<Dice>();
    }

    public void setDice( Dice dice ){
        if (this.dices.contains(dice)) {
            this.removeDice(dice);
        }
        this.dices.add(dice);
    }

    public void removeDice( Dice dice ) {
        this.dices.remove(dice);
    }

    public void removeDiceNr( int nr ) {
        this.dices.remove(nr);
    }

    public Dice getDice(int nr) {
        return this.dices.get(nr);
    }

    public List<Dice> getDices() {
        return this.dices;
    }

    public Integer getDicesValue( List<Dice> dices ) {
        int dicesValue = 0;
        for (Dice dice:dices) {
            dicesValue += dice.getDice();
        }

        return dicesValue;
    }

    public void fillDiceList(){
        if ( !this.dices.isEmpty() ) {
            for (Dice dice: this.dices) {
                dice.rollDice();
            }
            return;
        }

        Dice dice0 = new Dice();
        dice0.rollDice();
        this.setDice(dice0);
        Dice dice1 = new Dice();
        dice1.rollDice();
        this.setDice(dice1);
        Dice dice2 = new Dice();
        dice2.rollDice();
        this.setDice(dice2);
        Dice dice3 = new Dice();
        dice3.rollDice();
        this.setDice(dice3);
        Dice dice4 = new Dice();
        dice4.rollDice();
        this.setDice(dice4);
        Dice dice5 = new Dice();
        dice5.rollDice();
        this.setDice(dice5);
    }

    public void removeAllDices() {
        this.dices.clear();
    }

    public Integer length() {
        return this.dices.size();
    }

    public Boolean hasDice( Dice dice ) {
        for( Integer i = 0; i < this.dices.size(); i++ ) {
            if ( getDice(i) == dice ) {
                Log.d("DICE USED -->", i.toString());
                return true;
            }
        }
        return false;
    }
}

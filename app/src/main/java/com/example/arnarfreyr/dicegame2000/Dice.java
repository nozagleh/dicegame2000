package com.example.arnarfreyr.dicegame2000;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by arnarfreyr on 28.6.2017.
 */

public class Dice implements Serializable {

    private Integer value;

    private Boolean chosen = false;

    ImageView img;

    public Dice() {
        this.resetDice();
    }

    public void setDice(int value) {
        this.value = value;
    }

    public Integer getDice() {
        return this.value;
    }

    public void rollDice() {
        this.setDice((int)(Math.random() * 6 + 1));
    }

    public void resetDice() {
        this.setDice(0);
    }

    public void setChosen(Boolean chosen) {
        this.chosen = chosen;
    }

    public Boolean getChosen() {
        return this.chosen;
    }

    public void bindImage(ImageView img) {
        this.img = img;
    }

    public ImageView getImage() {
        return this.img;
    }

    public void updateImage(int res) {
        this.img.setImageResource(res);
    }

    public void updateBackground(int res) {
        this.img.setBackgroundResource(res);
    }
}

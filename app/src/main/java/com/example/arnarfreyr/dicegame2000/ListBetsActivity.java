package com.example.arnarfreyr.dicegame2000;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

/**
 * Created by arnarfreyr on 1.7.2017.
 */

public class ListBetsActivity extends Activity implements AdapterView.OnItemSelectedListener {

    GamePlay gp;
    Button btn;

    public ListBetsActivity( GamePlay gp ) {
        gp = new GamePlay();
        this.gp = gp;
        this.btn = btn;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Integer a = parent.getSelectedItemPosition();
        Log.d("SPINNER CLICKED -----> ", a.toString());
        if( this.gp.isBetDone(parent.getSelectedItemPosition()) ) {
            parent.getSelectedView().setEnabled(false);
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }
}

package com.example.arnarfreyr.dicegame2000;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by arnarfreyr on 2.7.2017.
 */

public class SpinnerAdapter extends ArrayAdapter {

    private Context context;
    private List<String> itemList;
    public SpinnerAdapter(Context context, int textViewResourceId,List<String> itemList) {

        super(context, textViewResourceId);
        this.context=context;
        this.itemList=itemList;
    }

    @Override
    public boolean isEnabled(int pos) {
       return false;
    }
}

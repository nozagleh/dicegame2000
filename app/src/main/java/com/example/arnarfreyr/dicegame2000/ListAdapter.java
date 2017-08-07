package com.example.arnarfreyr.dicegame2000;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnarfreyr on 28.6.2017.
 */

public class ListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> users;
    private final List<Integer> scores;

    public ListAdapter(Activity context, List<String> users, List<Integer> scores) {
        super(context, R.layout.listscores, users);

        this.context = context;
        this.users = users;
        this.scores = scores;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listscores, null, true);
        TextView txtName = (TextView) rowView.findViewById(R.id.txtName);
        TextView txtScore = (TextView) rowView.findViewById(R.id.txtScore);

        txtName.setText(users.get(position));
        txtScore.setText(scores.get(position).toString());

        return rowView;
    }
}

package com.example.android.softlab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by pk on 10/31/2017.
 */

public class StudentAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] values;

    public StudentAdapter(Context context, String[] values){
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.studentinfo_layout, parent, false);




        return rowView;
    }

}

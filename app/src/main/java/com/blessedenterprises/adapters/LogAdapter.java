package com.blessedenterprises.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.blessedenterprises.R;

import java.util.List;

/**
 * Created by stephnoutsa on 9/16/16.
 */
public class LogAdapter extends ArrayAdapter<String[]> {

    Context context;

    public LogAdapter(Context context, List<String[]> logs) {
        super(context, R.layout.log_row, logs);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.log_row, parent, false);

        String[] log = getItem(position);
        String sn = log[0];
        String date = log[1];

        TextView numRow = (TextView) customView.findViewById(R.id.numRow);
        numRow.setText(sn);

        TextView dateRow = (TextView) customView.findViewById(R.id.dateRow);
        dateRow.setText(date);

        return customView;
    }
}

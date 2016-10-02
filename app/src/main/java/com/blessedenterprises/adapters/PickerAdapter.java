package com.blessedenterprises.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;

import com.blessedenterprises.DatePicker;
import com.blessedenterprises.R;

import java.util.List;

/**
 * Created by stephnoutsa on 10/1/16.
 */
public class PickerAdapter extends ArrayAdapter<String[]> {

    Context context;
    int selected = 0;

    public PickerAdapter(Context context, List<String[]> dates) {
        super(context, R.layout.picker_row, dates);
        this.context = context;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.picker_row, parent, false);

        String[] date = getItem(position);
        final String value = date[0];
        String month = date[1];

        RadioButton radioButton = (RadioButton) customView.findViewById(R.id.radioButton);
        radioButton.setText(month);
        if (position == selected) {
            ((DatePicker) context).setMonth(value);
        }
        radioButton.setChecked(position == selected);
        radioButton.setTag(position);
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = (Integer) v.getTag();
                notifyDataSetChanged();

                if (context instanceof DatePicker) {
                    ((DatePicker) context).setMonth(value);
                }
            }
        });

        return customView;
    }
}

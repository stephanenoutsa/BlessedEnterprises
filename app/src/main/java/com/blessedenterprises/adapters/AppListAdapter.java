package com.blessedenterprises.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blessedenterprises.R;
import com.blessedenterprises.models.AppDetails;

import java.util.List;

/**
 * Created by stephnoutsa on 9/12/16.
 */
public class AppListAdapter extends ArrayAdapter<AppDetails> {

    Context context;

    public AppListAdapter(Context context, List<AppDetails> apps) {
        super(context, R.layout.app_row, apps);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.app_row, parent, false);

        AppDetails app = getItem(position);
        CharSequence label = app.label;
        //String labelString = trimLabel(label);
        Drawable icon = app.icon;

        ImageView appIcon = (ImageView) customView.findViewById(R.id.appIcon);
        appIcon.setImageDrawable(icon);

        TextView appLabel = (TextView) customView.findViewById(R.id.appLabel);
        appLabel.setText(label.toString());

        return customView;
    }

    private String trimLabel(CharSequence text) {
        String str = text.toString();
        String trimmed = "";
        int requiredNum = 6;
        int currentNum = str.length();

        if(requiredNum >= currentNum) {
            trimmed = str;
        }
        else {
            for(int i = 0; i < requiredNum; i++) {
                trimmed += str.charAt(i);
            }
            trimmed += "..";
        }

        return trimmed;
    }
}

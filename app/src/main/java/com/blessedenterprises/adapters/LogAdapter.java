package com.blessedenterprises.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.blessedenterprises.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

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
        String name = log[1];
        String date = log[2];
        String login = log[3];
        String logout = log[4];

        Date loginTime, logoutTime;
        SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss a", Locale.ENGLISH);
        long diff = 0;

        TextView numRow = (TextView) customView.findViewById(R.id.numRow);
        numRow.setText(sn);

        TextView nameRow = (TextView) customView.findViewById(R.id.nameRow);
        nameRow.setText(name);

        TextView dateRow = (TextView) customView.findViewById(R.id.dateRow);
        dateRow.setText(date);

        TextView loginRow = (TextView) customView.findViewById(R.id.loginRow);
        loginRow.setText(login);

        TextView logoutRow = (TextView) customView.findViewById(R.id.logoutRow);
        logoutRow.setText(logout);

        TextView sessionRow = (TextView) customView.findViewById(R.id.sessionRow);

        if (logout.equals("Still active")) {
            try {
                Date now = new Date();
                String nowTime = df.format(now);
                Date time = df.parse(nowTime);
                loginTime = df.parse(login);
                long diffInMs = time.getTime() - loginTime.getTime();
                //long diffMin = diffInMs / (1000 * 60);
                long diffMin = TimeUnit.MILLISECONDS.toMinutes(diffInMs);
                //long diffHr = diffMin / 60;
                long diffHr = TimeUnit.MILLISECONDS.toHours(diffInMs);

                if (diffMin < 1) {
                    sessionRow.setText("Less than a minute");
                } else if (diffMin >= 1 && diffHr < 1) {
                    if (diffMin < 2) {
                        sessionRow.setText(String.valueOf(diffMin));
                        sessionRow.append(" min");
                    } else {
                        sessionRow.setText(String.valueOf(diffMin));
                        sessionRow.append(" mins");
                    }
                } else if (diffHr >= 1) {
                    if (diffHr < 2) {
                        sessionRow.setText(String.valueOf(diffHr));
                        sessionRow.append(" hr");
                    } else {
                        sessionRow.setText(String.valueOf(diffHr));
                        sessionRow.append(" hrs");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                loginTime = df.parse(login);
                logoutTime = df.parse(logout);
                long diffInMs = logoutTime.getTime() - loginTime.getTime();
                long diffMin = diffInMs / (1000 * 60);
                long diffHr = diffMin / 60;

                if (diffMin < 1) {
                    sessionRow.setText("Less than a minute");
                } else if (diffMin >= 1 && diffHr < 1) {
                    if (diffMin < 2) {
                        sessionRow.setText(String.valueOf(diffMin));
                        sessionRow.append(" min");
                    } else {
                        sessionRow.setText(String.valueOf(diffMin));
                        sessionRow.append(" mins");
                    }
                } else if (diffHr >= 1) {
                    if (diffHr < 2) {
                        sessionRow.setText(String.valueOf(diffHr));
                        sessionRow.append(" hr");
                    } else {
                        sessionRow.setText(String.valueOf(diffHr));
                        sessionRow.append(" hrs");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return customView;
    }
}

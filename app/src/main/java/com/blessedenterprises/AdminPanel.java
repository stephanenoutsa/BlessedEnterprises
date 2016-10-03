package com.blessedenterprises;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toast;

import com.blessedenterprises.adapters.LogAdapter;
import com.blessedenterprises.dbhandlers.MyDBHandler;
import com.blessedenterprises.models.User;
import com.blessedenterprises.utils.WriteExcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
//import android.support.v7.widget.Toolbar;

public class AdminPanel extends AppCompatActivity {
    Context context = this;
    MyDBHandler dbHandler;
    List<User> users;
    List<String[]> list;
    ListView listView;
    ListAdapter listAdapter;
    TextView countLog, clearLog;
    Spinner spinner;
    String previous = "", month = "", year = "";
    int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        try {
            previous = getIntent().getExtras().getString("previous");
        } catch (NullPointerException npe) {
            previous = "";
        }

        try {
            month = getIntent().getExtras().getString("month");
            year = getIntent().getExtras().getString("year");
        } catch (NullPointerException npe) {
            month = "";
            year = "";
        }

        if (previous.equals("")) {
            dbHandler = new MyDBHandler(this, null, null, 1);
            users = new ArrayList<>();
            users = dbHandler.getAllUsers();
            users.remove(0);
            Collections.reverse(users);

            if (users.isEmpty()) {
                Toast.makeText(context, getString(R.string.no_users), Toast.LENGTH_SHORT).show();
            }

            list = new ArrayList<>();

            for(int i = 0; i < users.size(); i++) {
                String name = users.get(i).getName();
                String date = users.get(i).getDate();
                String login = users.get(i).getLoginTime();
                String logout = users.get(i).getLogoutTime();
                int sn = i + 1;
                String snStr = String.valueOf(sn) + ")";
                String[] details = {snStr, name, date, login, logout};
                list.add(details);
            }

            listView = (ListView) findViewById(R.id.listView);
            listAdapter = new LogAdapter(this, list);
            listView.setAdapter(listAdapter);

            countLog = (TextView) findViewById(R.id.countLog);
            String size = String.valueOf(list.size());
            countLog.setText(size);

            clearLog = (TextView) findViewById(R.id.clearLog);
            clearLog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(context).
                            setTitle(getString(R.string.clear_log)).
                            setMessage(getString(R.string.clear_log_warning)).
                            setPositiveButton(getString(R.string.logout_yes), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(context, AdminCheck.class);
                                    i.putExtra("previous", "clear");
                                    startActivity(i);
                                }
                            }).
                            setNegativeButton(getString(R.string.logout_no), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
            });

            spinner = (Spinner) findViewById(R.id.spinner);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    pos = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                    R.array.filter_options, android.R.layout.simple_spinner_item);

            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Apply the adapter to the spinner
            spinner.setAdapter(adapter);
            spinner.setSelection(2);
        } else if (previous.equals("picker")) {
            dbHandler = new MyDBHandler(this, null, null, 1);
            users = new ArrayList<>();
            users = dbHandler.getAllUsers();
            List<User> userGroup = new ArrayList<>();
            for (User user : users) {
                String date = user.getDate();
                String mth;
                String yr;
                try {
                    String[] details = date.split("/");
                    mth = details[1];
                    yr = details[2];
                } catch (Exception e) {
                    mth = "";
                    yr = "";
                }
                if (mth.equals(month) && yr.equals(year)) {
                    userGroup.add(user);
                }
            }

            if (userGroup.isEmpty()) {
                Toast.makeText(context, getString(R.string.no_users), Toast.LENGTH_SHORT).show();
            }

            list = new ArrayList<>();

            for(int i = 0; i < userGroup.size(); i++) {
                String name = userGroup.get(i).getName();
                String date = userGroup.get(i).getDate();
                String login = userGroup.get(i).getLoginTime();
                String logout = userGroup.get(i).getLogoutTime();
                int sn = i + 1;
                String snStr = String.valueOf(sn) + ")";
                String[] details = {snStr, name, date, login, logout};
                list.add(details);
            }

            listView = (ListView) findViewById(R.id.listView);
            listAdapter = new LogAdapter(this, list);
            listView.setAdapter(listAdapter);

            countLog = (TextView) findViewById(R.id.countLog);
            String size = String.valueOf(list.size());
            countLog.setText(size);

            clearLog = (TextView) findViewById(R.id.clearLog);
            clearLog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(context).
                            setTitle(getString(R.string.clear_log)).
                            setMessage(getString(R.string.clear_log_warning)).
                            setPositiveButton(getString(R.string.logout_yes), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(context, AdminCheck.class);
                                    i.putExtra("previous", "clear");
                                    startActivity(i);
                                }
                            }).
                            setNegativeButton(getString(R.string.logout_no), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
            });

            spinner = (Spinner) findViewById(R.id.spinner);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    pos = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                    R.array.filter_options, android.R.layout.simple_spinner_item);

            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Apply the adapter to the spinner
            spinner.setAdapter(adapter);
            spinner.setSelection(1);
        } else if (previous.equals("last_seven")) {
            // Set max date
            Calendar maxDate = Calendar.getInstance();
            maxDate.add(Calendar.DAY_OF_MONTH, 1);

            // Set min date
            Calendar minDate = Calendar.getInstance();
            minDate.add(Calendar.DAY_OF_MONTH, -7);

            dbHandler = new MyDBHandler(this, null, null, 1);
            users = new ArrayList<>();
            users = dbHandler.getAllUsers();
            List<User> userGroup = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            sdf.setLenient(false);

            for (User user: users) {
                try {
                    String strDate = user.getDate();
                    Date date = sdf.parse(strDate);

                    if (date.before(maxDate.getTime()) && date.after(minDate.getTime())) {
                        userGroup.add(user);
                    }
                } catch (ParseException pe) {
                    pe.printStackTrace();
                }
            }

            if (userGroup.isEmpty()) {
                Toast.makeText(context, getString(R.string.no_users), Toast.LENGTH_SHORT).show();
            }

            list = new ArrayList<>();

            for(int i = 0; i < userGroup.size(); i++) {
                String name = userGroup.get(i).getName();
                String date = userGroup.get(i).getDate();
                String login = userGroup.get(i).getLoginTime();
                String logout = userGroup.get(i).getLogoutTime();
                int sn = i + 1;
                String snStr = String.valueOf(sn) + ")";
                String[] details = {snStr, name, date, login, logout};
                list.add(details);
            }

            listView = (ListView) findViewById(R.id.listView);
            listAdapter = new LogAdapter(this, list);
            listView.setAdapter(listAdapter);

            countLog = (TextView) findViewById(R.id.countLog);
            String size = String.valueOf(list.size());
            countLog.setText(size);

            clearLog = (TextView) findViewById(R.id.clearLog);
            clearLog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(context).
                            setTitle(getString(R.string.clear_log)).
                            setMessage(getString(R.string.clear_log_warning)).
                            setPositiveButton(getString(R.string.logout_yes), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(context, AdminCheck.class);
                                    i.putExtra("previous", "clear");
                                    startActivity(i);
                                }
                            }).
                            setNegativeButton(getString(R.string.logout_no), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
            });

            spinner = (Spinner) findViewById(R.id.spinner);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    pos = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                    R.array.filter_options, android.R.layout.simple_spinner_item);

            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Apply the adapter to the spinner
            spinner.setAdapter(adapter);
            spinner.setSelection(0);
        }
    }

    public void onClickFilter(View view) {
        switch (pos) {
            case 0:
                Intent t = new Intent(context, AdminPanel.class);
                t.putExtra("previous", "last_seven");
                finish();
                overridePendingTransition(0, 0);
                startActivity(t);
                overridePendingTransition(0, 0);
                break;

            case 1:
                Intent i = new Intent(context, DatePicker.class);
                startActivity(i);
                break;

            case 2:
                Intent j = new Intent(context, AdminPanel.class);
                finish();
                overridePendingTransition(0, 0);
                startActivity(j);
                overridePendingTransition(0, 0);
        }
    }

    public void onClickGenerate(View view) {
        WriteExcel writeExcel = new WriteExcel();
        writeExcel.setOutputFile(context);
        try {
            writeExcel.write();
        } catch (Exception e) {
            Toast.makeText(context, getString(R.string.generate_failed), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, LockScreen.class);
        startActivity(i);
    }
}

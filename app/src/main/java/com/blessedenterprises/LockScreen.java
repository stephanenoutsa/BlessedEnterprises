package com.blessedenterprises;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
//import android.view.Menu;
//import android.view.MenuItem;

import com.blessedenterprises.dbhandlers.MyDBHandler;
import com.blessedenterprises.utils.Alarm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LockScreen extends AppCompatActivity {

    MyDBHandler dbHandler;
    Context context = this;
    Date date;
    RelativeLayout row4;
    TextView logout, adminPanel, statusIndicator, sessionStatus;
    EditText userName, nameField;
    Button unlock;
    Spinner spinner;
    int pos = 0;
    String status = "";
    String input = "";
    String result = "";
    String time = "";
    String loginTime = "";
    String host = "";
    String line = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);

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

        dbHandler = new MyDBHandler(this, null, null, 1);

        userName = (EditText) findViewById(R.id.userName);
        nameField = (EditText) findViewById(R.id.nameField);
        logout = (TextView) findViewById(R.id.reset);
        adminPanel = (TextView) findViewById(R.id.adminPanel);
        statusIndicator = (TextView) findViewById(R.id.statusIndicator);
        sessionStatus = (TextView) findViewById(R.id.sessionStatus);
        row4 = (RelativeLayout) findViewById(R.id.row4);

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
                R.array.line_options, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        setView();

        unlock = (Button) findViewById(R.id.unlock);

        unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dbHandler.getSession().equals("active")) {
                    Intent i = new Intent(context, AppsLauncher.class);
                    startActivity(i);
                } else {
                    host = dbHandler.getHost().getName();
                    line = dbHandler.getHost().getLine();
                    if (host.equals("null") || line.equals("null")) {
                        Toast.makeText(context, getString(R.string.host_error), Toast.LENGTH_LONG).show();
                    } else {
                        result = capitalize(userName.getText().toString().trim());
                        if (validate(result, "user")) {
                            date = new Date();
                            DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                            DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.ENGLISH);
                            time = df.format(date.getTime());
                            loginTime = df1.format(date.getTime());
                            dbHandler.updateSession("active");
                            dbHandler.addUser(result, time, loginTime, "Still active", host, line);
                            int count = dbHandler.getCount();
                            int newCount = count + 1;
                            dbHandler.updateCount(newCount);

                            // Start alarm for ads
                            Alarm alarm = new Alarm();
                            alarm.startAlarm(context);

                            Intent i = new Intent(context, AppsLauncher.class);
                            startActivity(i);
                        }
                    }
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = dbHandler.getSession();
                switch (status) {
                    case "inactive":
                        Toast.makeText(context, getString(R.string.not_logged_in), Toast.LENGTH_SHORT).show();
                        break;

                    case "active":
                        new AlertDialog.Builder(context).
                                setTitle(getString(R.string.logout_text)).
                                setMessage(getString(R.string.logout_warning)).
                                setPositiveButton(getString(R.string.logout_yes), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i = new Intent(context, AdminCheck.class);
                                        i.putExtra("previous", "logout");
                                        startActivity(i);
                                    }
                                }).
                                setNegativeButton(getString(R.string.logout_no), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
                        break;
                }
            }
        });

        adminPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AdminCheck.class);
                i.putExtra("previous", "admin");
                startActivity(i);
            }
        });

        setStatus();
    }

    public void setStatus() {
        sessionStatus.setText(dbHandler.getSession());
        switch (dbHandler.getSession()) {
            case "active":
                sessionStatus.setTextColor(ContextCompat.getColor(this, R.color.colorActive));
                break;
            case "inactive":
                sessionStatus.setTextColor(ContextCompat.getColor(this, R.color.colorInactive));
        }
    }

    public void setView() {
        host = dbHandler.getHost().getName();
        line = dbHandler.getHost().getLine();
        if (! host.equals("null") && ! line.equals("null")) {
            row4.setVisibility(View.GONE);
        }
    }

    public String capitalize(String name) {
        if (!name.equals("")) {
            String[] words = name.split(" ");
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < words.length; i++) {
                stringBuilder.append(Character.toUpperCase(words[i].charAt(0)));
                if (words[i].length() > 3) {
                    for (int j = 0; j < words[i].substring(1).length(); j++) {
                        stringBuilder.append(Character.toLowerCase(words[i].substring(1).charAt(j)));
                    }
                    if (i < words.length) {
                        stringBuilder.append(" ");
                    }
                } else {
                    stringBuilder.append(words[i].substring(1));
                }
            }

            return stringBuilder.toString().trim();
        } else {
            return name;
        }
    }

    public boolean validate(String name, String field) {
        boolean check = true;

        if (name.equals("")) {
            check = false;
            if (field.equals("user")) {
                userName.setError(getString(R.string.no_name));
            } else {
                nameField.setError(getString(R.string.no_name));
            }
        } else {
            String[] words = name.split(" ");
            int num = words.length;

            if (num < 2) {
                check = false;
                if (field.equals("user")) {
                    userName.setError(getString(R.string.short_name));
                } else {
                    nameField.setError(getString(R.string.short_name));
                }
            }

            if (num > 3) {
                check = false;
                if (field.equals("user")) {
                    userName.setError(getString(R.string.long_name));
                } else {
                    nameField.setError(getString(R.string.long_name));
                }
            }

            for (String word : words) {
                Pattern pattern = Pattern.compile("(?i)[a-zçèéêîôœû]+");
                Matcher matcher = pattern.matcher(word);
                if (!matcher.matches()) {
                    check = false;
                    if (field.equals("user")) {
                        userName.setError(getString(R.string.invalid_name));
                    } else {
                        nameField.setError(getString(R.string.invalid_name));
                    }
                }
                if (word.length() < 2) {
                    check = false;
                    if (field.equals("user")) {
                        userName.setError(getString(R.string.invalid_name));
                    } else {
                        nameField.setError(getString(R.string.invalid_name));
                    }
                }
            }
        }

        return check;
    }

    public void onClickSubmit(View view) {
        String[] lines = getResources().getStringArray(R.array.line_options);
        String lineName = lines[pos];

        String hostName = capitalize(nameField.getText().toString().trim());

        if (validate(hostName, "host")) {
            Intent i = new Intent(context, AdminCheck.class);
            i.putExtra("previous", "host");
            i.putExtra("host", hostName);
            i.putExtra("line", lineName);
            startActivity(i);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.moveTaskToFront(getTaskId(), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setStatus();
        setView();
        input = "";
        userName.setText(input);
        host = "";
        line = "";
    }

    @Override
    public void onBackPressed() {
        // Do nothing
    }
}

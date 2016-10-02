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
import android.widget.Button;
import android.widget.EditText;
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
    TextView logout, adminPanel, statusIndicator, sessionStatus;
    EditText userName;
    Button unlock;
    String status = "";
    String input = "";
    String result = "";
    String time = "";
    String loginTime = "";

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
        logout = (TextView) findViewById(R.id.reset);
        adminPanel = (TextView) findViewById(R.id.adminPanel);
        statusIndicator = (TextView) findViewById(R.id.statusIndicator);
        sessionStatus = (TextView) findViewById(R.id.sessionStatus);

        unlock = (Button) findViewById(R.id.unlock);

        unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dbHandler.getSession().equals("active")) {
                    Intent i = new Intent(context, AppsLauncher.class);
                    startActivity(i);
                } else {
                    result = capitalize(userName.getText().toString().trim());
                    if (validate(result)) {
                        date = new Date();
                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                        DateFormat df1 = new SimpleDateFormat("hh:mm:ss a", Locale.ENGLISH);
                        time = df.format(date.getTime());
                        loginTime = df1.format(date.getTime());
                        dbHandler.updateSession("active");
                        dbHandler.addUser(result, time, loginTime, "Still active");
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

    public String capitalize(String name) {
        if (!name.equals("")) {
            String[] words = name.split(" ");
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < words.length; i++) {
                stringBuilder.append(Character.toUpperCase(words[i].charAt(0)));
                stringBuilder.append(words[i].substring(1));
                if (i < words.length) {
                    stringBuilder.append(" ");
                }
            }

            return stringBuilder.toString().trim();
        } else {
            return name;
        }
    }

    public boolean validate(String name) {
        boolean check = true;

        if (name.equals("")) {
            check = false;
            userName.setError(getString(R.string.no_name));
        } else {
            String[] words = name.split(" ");
            int num = words.length;

            if (num < 2) {
                userName.setError(getString(R.string.short_name));
                check = false;
            }

            if (num > 3) {
                userName.setError(getString(R.string.long_name));
                check = false;
            }

            for (String word : words) {
                Pattern pattern = Pattern.compile("[a-zA-Z]+");
                Matcher matcher = pattern.matcher(word);
                if (!matcher.matches()) {
                    check = false;
                    userName.setError(getString(R.string.invalid_name));
                }
                if (word.length() < 2) {
                    check = false;
                    userName.setError(getString(R.string.invalid_name));
                }
            }
        }

        return check;
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
        input = "";
        userName.setText(input);
    }

    @Override
    public void onBackPressed() {
        // Do nothing
    }
}

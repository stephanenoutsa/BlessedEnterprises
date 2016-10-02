package com.blessedenterprises;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.blessedenterprises.dbhandlers.MyDBHandler;
import com.blessedenterprises.utils.Alarm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AdminCheck extends AppCompatActivity {

    MyDBHandler dbHandler;
    Context context = this;
    Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button0, buttonx, buttony, verify;
    String input = "";
    String previous = "";
    String code = "0000";
    String superCode = "2580";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check);

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

        // What button was pressed?
        previous = getIntent().getExtras().getString("previous");

        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        buttonx = (Button) findViewById(R.id.buttonx);
        buttony = (Button) findViewById(R.id.buttony);
        verify = (Button) findViewById(R.id.verify);

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input += "0";
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input += "1";
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input += "2";
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input += "3";
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input += "4";
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input += "5";
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input += "6";
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input += "7";
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input += "8";
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input += "9";
            }
        });

        buttonx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input = "";
            }
        });

        buttony.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codeStr = input;
                if (!codeStr.equals("")) {
                    input = codeStr.substring(0, codeStr.length() - 1);
                }
            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (previous) {
                    case "logout":
                        if (input.equals(code) || input.equals(superCode)) {
                            Date date = new Date();
                            DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.ENGLISH);
                            String logoutTime = df.format(date.getTime());
                            dbHandler.updateUser(logoutTime);
                            dbHandler.updateSession("inactive");

                            // Stop the ads alarm
                            Alarm alarm = new Alarm();
                            alarm.stopAlarm(context);

                            finish();
                        } else {
                            Toast.makeText(context, getString(R.string.wrong_code), Toast.LENGTH_SHORT).show();
                            input = "";
                        }
                        break;

                    case "admin":
                        if (input.equals(superCode)) {
                            Intent i = new Intent(context, AdminPanel.class);
                            startActivity(i);
                        } else if (input.equals(code)) {
                            Toast.makeText(context, getString(R.string.no_privilege), Toast.LENGTH_SHORT).show();
                            input = "";
                        } else {
                            Toast.makeText(context, getString(R.string.wrong_code), Toast.LENGTH_SHORT).show();
                            input = "";
                        }
                        break;

                    case "clear":
                        if (input.equals(superCode)) {
                            // Stop the ads alarm
                            Alarm alarm = new Alarm();
                            alarm.stopAlarm(context);

                            dbHandler.deleteAllUsers();
                            dbHandler.updateSession("inactive");
                            Intent intent = new Intent(context, AdminPanel.class);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                        } else if (input.equals(code)) {
                            Toast.makeText(context, getString(R.string.no_privilege), Toast.LENGTH_SHORT).show();
                            input = "";
                        } else {
                            Toast.makeText(context, getString(R.string.wrong_code), Toast.LENGTH_SHORT).show();
                            input = "";
                        }
                        break;
                }
            }
        });
    }

}

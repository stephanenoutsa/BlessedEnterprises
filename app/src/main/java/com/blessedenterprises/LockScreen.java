package com.blessedenterprises;

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
import android.widget.TextView;
import android.widget.Toast;
//import android.view.Menu;
//import android.view.MenuItem;

import com.blessedenterprises.dbhandlers.MyDBHandler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LockScreen extends AppCompatActivity {

    MyDBHandler dbHandler;
    Context context = this;
    Date date;
    TextView code, logout, adminPanel, statusIndicator, sessionStatus;
    Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button0, buttonx, buttony, unlock;
    String status = "";
    String input = "";
    String result = "";
    String time = "";

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

        code = (TextView) findViewById(R.id.code);
        logout = (TextView) findViewById(R.id.reset);
        adminPanel = (TextView) findViewById(R.id.adminPanel);
        statusIndicator = (TextView) findViewById(R.id.statusIndicator);
        sessionStatus = (TextView) findViewById(R.id.sessionStatus);

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
        unlock = (Button) findViewById(R.id.unlock);

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.length() < 4) {
                    input += "0";
                    code.setText(input);
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.length() < 4) {
                    input += "1";
                    code.setText(input);
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.length() < 4) {
                    input += "2";
                    code.setText(input);
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.length() < 4) {
                    input += "3";
                    code.setText(input);
                }
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.length() < 4) {
                    input += "4";
                    code.setText(input);
                }
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.length() < 4) {
                    input += "5";
                    code.setText(input);
                }
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.length() < 4) {
                    input += "6";
                    code.setText(input);
                }
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.length() < 4) {
                    input += "7";
                    code.setText(input);
                }
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.length() < 4) {
                    input += "8";
                    code.setText(input);
                }
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.length() < 4) {
                    input += "9";
                    code.setText(input);
                }
            }
        });

        buttonx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input = "";
                code.setText(input);
            }
        });

        buttony.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codeStr = code.getText().toString();
                if (!codeStr.equals("")) {
                    input = codeStr.substring(0, codeStr.length() - 1);
                    code.setText(input);
                }
            }
        });

        unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = code.getText().toString();
                if (result.equals("1234")) {
                    status = dbHandler.getSession();
                    if (status.equals("inactive")) {
                        date = new Date();
                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
                        time = df.format(date.getTime());
                        dbHandler.updateSession("active");
                        dbHandler.addCode(result, time);
                        int count = dbHandler.getCount();
                        int newCount = count + 1;
                        dbHandler.updateCount(newCount);
                    }
                    Intent i = new Intent(context, AppsLauncher.class);
                    startActivity(i);
                } else {
                    Toast.makeText(context, getString(R.string.wrong_code), Toast.LENGTH_SHORT).show();
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
        sessionStatus.setText(" ");
        sessionStatus.append(dbHandler.getSession());
        switch (dbHandler.getSession()) {
            case "active":
                sessionStatus.setTextColor(ContextCompat.getColor(this, R.color.colorActive));
                break;
            case "inactive":
                sessionStatus.setTextColor(ContextCompat.getColor(this, R.color.colorInactive));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setStatus();
    }

    @Override
    public void onBackPressed() {
        // Do nothing
    }
}

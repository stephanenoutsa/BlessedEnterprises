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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.blessedenterprises.adapters.LogAdapter;
import com.blessedenterprises.dbhandlers.MyDBHandler;
import com.blessedenterprises.models.Code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//import android.support.v7.widget.Toolbar;

public class AdminPanel extends AppCompatActivity {
    Context context = this;
    MyDBHandler dbHandler;
    List<Code> codes;
    List<String[]> list;
    ListView listView;
    ListAdapter listAdapter;
    TextView countLog, clearLog;

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

        dbHandler = new MyDBHandler(this, null, null, 1);
        codes = new ArrayList<>();
        codes = dbHandler.getAllCodes();
        codes.remove(0);
        Collections.reverse(codes);
        list = new ArrayList<>();

        for(int i = 0; i < codes.size(); i++) {
            String date = codes.get(i).getDate();
            int sn = i + 1;
            String snStr = String.valueOf(sn);
            String[] code = {snStr, date};
            list.add(code);
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, LockScreen.class);
        startActivity(i);
    }
}

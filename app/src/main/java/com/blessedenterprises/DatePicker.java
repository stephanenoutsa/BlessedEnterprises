package com.blessedenterprises;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.blessedenterprises.adapters.PickerAdapter;
import com.blessedenterprises.dbhandlers.MyDBHandler;
import com.blessedenterprises.models.User;

import java.util.ArrayList;
import java.util.List;

public class DatePicker extends AppCompatActivity {

    Context context = this;
    String month = "", year = "";
    MyDBHandler dbHandler;
    List<User> users = new ArrayList<>();
    List<String[]> months = new ArrayList<>();
    List<String> years = new ArrayList<>();
    ListView listView;
    ListAdapter listAdapter;
    TextView yearText;
    Spinner yearFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);

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

        // Populate months list
        String[] january = {"01", "January"};
        months.add(january);
        String[] february = {"02", "February"};
        months.add(february);
        String[] march = {"03", "March"};
        months.add(march);
        String[] april = {"04", "April"};
        months.add(april);
        String[] may = {"05", "May"};
        months.add(may);
        String[] june = {"06", "June"};
        months.add(june);
        String[] july = {"07", "July"};
        months.add(july);
        String[] august = {"08", "August"};
        months.add(august);
        String[] september = {"09", "September"};
        months.add(september);
        String[] october = {"10", "October"};
        months.add(october);
        String[] november = {"11", "November"};
        months.add(november);
        String[] december = {"12", "December"};
        months.add(december);

        dbHandler = new MyDBHandler(context, null, null, 1);
        users = dbHandler.getAllUsers();
        users.remove(0);
        for (User user : users) {
            String date = user.getDate();
            String[] details = date.split("/");
            String yr = details[2];
            if (!years.contains(yr)) {
                years.add(yr);
            }
        }

        listView = (ListView) findViewById(R.id.listView);
        listAdapter = new PickerAdapter(context, months);
        listView.setAdapter(listAdapter);

        yearText = (TextView) findViewById(R.id.yearText);

        yearFilter = (Spinner) findViewById(R.id.yearFilter);
        yearFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Create an ArrayAdapter using the years list and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, years);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        yearFilter.setAdapter(adapter);
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void onClickNext(View view) {
        Intent i = new Intent(context, AdminPanel.class);
        i.putExtra("previous", "picker");
        i.putExtra("month", month);
        i.putExtra("year", year);
        startActivity(i);
        overridePendingTransition(0, 0);
    }

}

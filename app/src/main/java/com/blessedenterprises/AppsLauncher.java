package com.blessedenterprises;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.blessedenterprises.adapters.AppListAdapter;
import com.blessedenterprises.models.AppDetails;

import java.util.ArrayList;
import java.util.List;

public class AppsLauncher extends AppCompatActivity {

    private PackageManager packageManager;
    private List<AppDetails> apps;
    private GridView gridView;
    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps_launcher);

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

        packageManager = getPackageManager();
        apps = new ArrayList<>();

        gridView = (GridView) findViewById(R.id.gridView);

        loadApps();
        loadAppList();
    }

    private void loadApps() {
        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = packageManager.queryIntentActivities(i, 0);

        for (ResolveInfo ri : availableActivities) {
            AppDetails app = new AppDetails();
            app.label = ri.loadLabel(packageManager);
            app.name = ri.activityInfo.packageName;
            app.icon = ri.activityInfo.loadIcon(packageManager);

            apps.add(app);
        }
    }

    private void loadAppList() {
        listAdapter = new AppListAdapter(this, apps);
        gridView.setAdapter(listAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppDetails app = (AppDetails) parent.getItemAtPosition(position);
                CharSequence name = app.name;
                Intent i = packageManager.getLaunchIntentForPackage(name.toString());
                startActivity(i);
            }
        });
    }

}

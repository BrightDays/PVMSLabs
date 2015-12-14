package com.example.brightdays.lab6;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Date;

public class MyActivity extends FragmentActivity {

    protected static DBHelper dbHelper;
    private String[] menuItemsTitle;
    private Handler mHandler;
    protected static DrawerLayout drawerLayout;
    protected static ListView drawerListView;
    protected final static String ARG_KEY_CATEGORY = "key_category";

    private class DrawerListViewClickListener implements ListView.OnItemClickListener {

        private ViewPagerFragment vpf;

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
            final ProgressDialog progress = new ProgressDialog(MyActivity.this);
            progress.show();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    navigateTo(i);
                    progress.dismiss();
                }
            }, 250);
            drawerListView.setItemChecked(i, true);
            drawerLayout.closeDrawer(drawerListView);
        }


        private void navigateTo(int position) {
            vpf = new ViewPagerFragment();
            Bundle args = new Bundle();
            switch (position) {
                case 1: { // income
                    args.putString(ARG_KEY_CATEGORY, DBHelper.TAG_INCOME);
                    vpf.setArguments(args);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, vpf, ViewPagerFragment.TAG).commit();
                    break;
                }
                case 2: { // expences
                    args.putString(ARG_KEY_CATEGORY, DBHelper.TAG_EXPENCES);
                    vpf.setArguments(args);
                    //getSupportFragmentManager().beginTransaction().replace(vpf, R.id.content_frame, ViewPagerFragment.TAG).commit();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, vpf, ViewPagerFragment.TAG).commit();
                    break;
                }
                case 3: { // statistic
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, new StatisticFragment(), null).commit();
                }
                case 4: { // categories
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, new CategoryFragment(), null).commit();

                }
            }
        }
    }

    public void clickAddValues(View view) {
        Intent intent = new Intent(this, AddValuesToDatabase.class);
        if (intent != null) {
            this.startActivity(intent);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        mHandler = new Handler(); // for lag in the Navigation Drawer
       /* DialogChangePeriod dialog = new DialogChangePeriod();
        dialog.show(getSupportFragmentManager(), "HELLO!");
        DialogAddCategory dg = new DialogAddCategory();
        dg.show(getSupportFragmentManager(), "Hello!");*/
        String currentDateTime = DateFormat.format("yyyy.MM.dd kk:mm:ss", new Date()).toString();
        Finance[] finance = new Finance[20];
        finance[0] = new Finance("test", "income", "2014.05.14 12:56:41", 1000);
        finance[1] = new Finance("test", "income", "2015.05.10 12:56:41", 1000);
        finance[2] = new Finance("test", "income", "2015.05.11 12:56:41", 1000);
        finance[3] = new Finance("test", "income", "2015.05.12 12:56:41", 1000);
        finance[4] = new Finance("test", "income", "2015.05.13 12:56:41", 1000);
        finance[5] = new Finance("test", "income", "2015.05.14 12:56:41", 1000);
        finance[6] = new Finance("test", "income", "2015.05.15 12:56:41", 1000);
        finance[7] = new Finance("test", "income", "2015.05.16 12:56:41", 1000);
        try {
             //for (int i = 0; i < 8; i++)
             // new AsyncTaskDB.AsyncInsertData(finance[i], this).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        menuItemsTitle = getResources().getStringArray(R.array.menu_items);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerListView = (ListView) findViewById(R.id.left_menu);
        View nav_header = getLayoutInflater().inflate(R.layout.navigation_header, null);
        drawerListView.addHeaderView(nav_header);
        drawerListView.setAdapter(new DrawerAdapter(this, R.layout.left_menu_item_row, menuItemsTitle));
        drawerListView.setOnItemClickListener(new DrawerListViewClickListener());
    }
}


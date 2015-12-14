package com.example.brightdays.lab6;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by User-PC on 03.04.2015.
 */
public class AsyncTaskDB {

    public static class AsyncInsertData extends AsyncTask<Void, Void, Void> {
        DBHelper dbHelper;
        Finance finance;
        Context context;
        ProgressDialog progress;

        protected AsyncInsertData(Finance finance, Context context) {
            this.finance = finance;
            this.dbHelper = new DBHelper(context);

        }



        @Override
        protected Void doInBackground(Void... voids) {
            try {
                dbHelper.addFinance(finance);
                progress.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


    }


    public static class AsyncGetCategories extends AsyncTask<Void, Void, ArrayList<String>> {
        DBHelper dbHelper;
        ProgressDialog progress;
        ArrayList <String> categories;

        public AsyncGetCategories(Context context) {
            this.dbHelper = new DBHelper(context);
            this.categories = new ArrayList<String>();
            progress = new ProgressDialog(context);
            progress.show();
        }


        @Override
        public ArrayList<String> doInBackground(Void... voids) {
            try {
                categories = dbHelper.getCategories();
                progress.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return categories;
        }

        @Override
        public void onPostExecute(final ArrayList<String> result) {

        }
    }

    public static class AsyncGetPageName extends AsyncTask<Void, Void, ArrayList<String>> {
        DBHelper dbHelper;
        ArrayList<String> month;
        String criteria;
        ProgressDialog progress;

        protected AsyncGetPageName(Context context, int period, String criteria) {
            this.dbHelper = new DBHelper(context);
            this.criteria = criteria;
            progress = new ProgressDialog(context);
            progress.show();
        }


        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            try {
                month = dbHelper.parsePageName(criteria);
                progress.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return month;
        }

        @Override
        protected void onPostExecute(final ArrayList<String> result) {
            //  ViewPagerFragment.SectionsPagerAdapter.tableNames = result;
        }
    }

    public static class AsyncGetAllData extends AsyncTask<Void, Void, ArrayList<Finance>> {
        DBHelper dbHelper;
        String pageData;
        String criteria;
        int period;
        Context context;
        ArrayList<Finance> finances;
        ProgressDialog progress;

        protected AsyncGetAllData(Context context, String pageData, String criteria, int period) {
            this.pageData = pageData;
            this.criteria = criteria;
            this.period = period;
            this.dbHelper = new DBHelper(context);
            progress = new ProgressDialog(context);
            progress.show();
        }

        @Override
        protected ArrayList<Finance> doInBackground(Void... voids) {
            try {
                finances = dbHelper.getAllDatabaseValues(pageData, criteria, period);
                progress.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return finances;
        }
    }
}

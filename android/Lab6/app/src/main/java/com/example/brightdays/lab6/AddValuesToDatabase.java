package com.example.brightdays.lab6;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by User-PC on 02.04.2015.
 */
public class AddValuesToDatabase extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_values_to_db);
        category_spinner = (Spinner) findViewById(R.id.category_spinner);
        try {
            data = new AsyncTaskDB.AsyncGetCategories(this).execute().get();
            category_spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    TextView value;
    Spinner category_spinner;
    ArrayList <String> data;
    String type;

    public void butClick(View view) {
        value = (TextView) findViewById(R.id.values);


        String currentDateTime = DateFormat.format("yyyy.MM.dd kk:mm:ss", new Date()).toString();
        this.type = getIntent().getExtras().getString(AddValuesOnClickListener.ARG_TYPE);
        Finance finance = new Finance(category_spinner.getSelectedItem().toString(), ViewPagerFragment.getCriteria(type),
                currentDateTime, Integer.parseInt(value.getText().toString()));
        new AsyncTaskDB.AsyncInsertData(finance, this).execute();
    }
}
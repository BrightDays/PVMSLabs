package com.example.brightdays.lab6;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

/**
 * Created by User-PC on 01.04.2015.
 */
public class AddValuesOnClickListener implements Button.OnClickListener {

    private Context context;
    private int type;
    public final static String ARG_TYPE = "ARG_TYPE";

    public AddValuesOnClickListener(Context context, int type) {
        this.context = context;
        this.type = type;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context, AddValuesToDatabase.class);
        intent.putExtra(ARG_TYPE, type);
        if (intent != null) {
            context.startActivity(intent);
        }
    }
}

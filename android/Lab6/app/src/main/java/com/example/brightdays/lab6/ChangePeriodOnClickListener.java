package com.example.brightdays.lab6;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import com.example.brightdays.lab6.Dialogs.DialogChangePeriod;
import com.example.brightdays.lab6.Events.ChangePeriodEvent;

/**
 * Created by User-PC on 04.04.2015.
 */
public class ChangePeriodOnClickListener implements Button.OnClickListener {

    private Context context;
    private ChangePeriodEvent initiator;
    private DialogChangePeriod dialog;

    public ChangePeriodOnClickListener(Context context, ChangePeriodEvent initiator) {
        this.context = context;
        this.initiator = initiator;
    }


    @Override
    public void onClick(View view) {
        dialog = new DialogChangePeriod();
        dialog.setChangePeriodEvent(initiator);
        dialog.show(((FragmentActivity) context).getSupportFragmentManager(), "TAG");

    }
}

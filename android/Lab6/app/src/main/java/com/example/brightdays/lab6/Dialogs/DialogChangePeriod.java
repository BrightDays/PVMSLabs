package com.example.brightdays.lab6.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.brightdays.lab6.Events.ChangePeriodEvent;
import com.example.brightdays.lab6.R;
import com.example.brightdays.lab6.ViewPagerFragment;

/**
 * Created by User-PC on 04.04.2015.
 */
public class DialogChangePeriod extends DialogFragment {

    private int period;

    private void setPeriod(int period) {
        ViewPagerFragment.setPeriod(period);
    }

    private int getPeriod()
    {
        return ViewPagerFragment.getPeriod();
    }


    ChangePeriodEvent changePeriodEvent;

    public void setChangePeriodEvent(ChangePeriodEvent changePeriodEvent) {
        this.changePeriodEvent = changePeriodEvent;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Выберите период!")
                .setItems(R.array.criteria, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int position) {
                        period = getPeriod();
                        setPeriod(position);
                        if (getPeriod() != period && changePeriodEvent != null)
                            changePeriodEvent.changePeriod();
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        return builder.create();
    }
}

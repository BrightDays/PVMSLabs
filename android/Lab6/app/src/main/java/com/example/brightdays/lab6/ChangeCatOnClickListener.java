package com.example.brightdays.lab6;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.example.brightdays.lab6.Dialogs.DialogChangeCategory;
import com.example.brightdays.lab6.Events.ChangeCategoryEvent;

/**
 * Created by User-PC on 19.04.2015.
 */
public class ChangeCatOnClickListener implements Button.OnClickListener {

    private Context context;
    private DialogChangeCategory dialog;
    private ChangeCategoryEvent initiator;

    public ChangeCatOnClickListener(Context context, ChangeCategoryEvent initiator) {
        this.context = context;
        this.initiator = initiator;
    }


    @Override
    public void onClick(View view) {
        dialog = new DialogChangeCategory();
        dialog.setChangeCategoryEvent(initiator);
        dialog.show(((FragmentActivity) context).getSupportFragmentManager(), "TAG");
    }

}


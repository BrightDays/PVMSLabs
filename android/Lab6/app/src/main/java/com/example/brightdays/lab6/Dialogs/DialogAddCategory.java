package com.example.brightdays.lab6.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.widget.EditText;
import com.example.brightdays.lab6.DBHelper;
import com.example.brightdays.lab6.Events.AddCategoryEvent;

/**
 * Created by User-PC on 16.04.2015.
 */
public class DialogAddCategory extends DialogFragment {

    AddCategoryEvent initiator;

    public void setAddCategoryEvent(AddCategoryEvent initiator)
    {
        this.initiator = initiator;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setTitle("Введите имя новой категории");
        builder.setPositiveButton("Ок", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                DBHelper dbHelper = new DBHelper(getActivity());
                dbHelper.addCategory(input.getText().toString());
                initiator.addCategory();

            }
        });
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        return builder.create();
    }
}

package com.example.brightdays.lab6.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.brightdays.lab6.AsyncTaskDB;
import com.example.brightdays.lab6.Events.ChangeCategoryEvent;
import com.example.brightdays.lab6.ViewPagerFragment;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by User-PC on 19.04.2015.
 */
public class DialogChangeCategory extends DialogFragment {

    ChangeCategoryEvent changeCategoryEvent;

    public void setChangeCategoryEvent(ChangeCategoryEvent changeCategoryEvent) {
        this.changeCategoryEvent = changeCategoryEvent;
    }

    private static boolean[] isChecked;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        try {
            final ArrayList<String> categories = new AsyncTaskDB.AsyncGetCategories(getActivity()).execute().get();

            if (isChecked == null || isChecked.length != categories.size()) {
                isChecked = new boolean[categories.size()];
                Arrays.fill(isChecked, true);
                ViewPagerFragment.setSelectedCategories(categories);
            }
            final ArrayList<String> selectedCategories = ViewPagerFragment.getSelectedCategories();
            builder.setMultiChoiceItems(categories.toArray(new CharSequence[categories.size()]),
                    isChecked, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int indexSelected,
                                            boolean itemChecked) {
                            if (itemChecked) {
                                isChecked[indexSelected] = true;
                                selectedCategories.add(categories.get(indexSelected));
                                ViewPagerFragment.setSelectedCategories(selectedCategories);
                            } else {
                                isChecked[indexSelected] = false;
                                selectedCategories.remove(categories.get(indexSelected));
                                ViewPagerFragment.setSelectedCategories(selectedCategories);
                            }
                        }
                    });
            builder.setTitle("Выберите категории!");
            builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            builder.setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (changeCategoryEvent != null)
                        changeCategoryEvent.changeCategory();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.create();

    }
}

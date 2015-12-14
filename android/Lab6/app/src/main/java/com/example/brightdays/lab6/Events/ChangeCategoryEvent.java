package com.example.brightdays.lab6.Events;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User-PC on 20.04.2015.
 */
public class ChangeCategoryEvent {

    List<ChangeCategoryInterface> listeners = new ArrayList<ChangeCategoryInterface>();

    public void addListener(ChangeCategoryInterface toAdd) {
        listeners.add(toAdd);
    }

    public void changeCategory() {
        for (ChangeCategoryInterface hl : listeners)
            hl.categoryChanged();
    }
}

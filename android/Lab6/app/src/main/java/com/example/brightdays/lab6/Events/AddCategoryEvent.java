package com.example.brightdays.lab6.Events;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User-PC on 19.04.2015.
 */
public class AddCategoryEvent {

    List<AddCategoryInterface> listeners = new ArrayList<AddCategoryInterface>();

    public void addListener(AddCategoryInterface toAdd) {
        listeners.add(toAdd);
    }

    public void addCategory() {
        for (AddCategoryInterface hl : listeners)
            hl.categoryAdded();
    }
}

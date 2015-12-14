package com.example.brightdays.lab6.Events;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User-PC on 05.04.2015.
 */
public class ChangePeriodEvent {

    List<ChangePeriodInterface> listeners = new ArrayList<ChangePeriodInterface>();

    public void addListener(ChangePeriodInterface toAdd) {
        listeners.add(toAdd);
    }

    public void changePeriod() {
        for (ChangePeriodInterface hl : listeners)
            hl.periodChanged();
    }
}

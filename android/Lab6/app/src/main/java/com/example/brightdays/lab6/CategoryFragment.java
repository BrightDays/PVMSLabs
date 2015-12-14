package com.example.brightdays.lab6;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.example.brightdays.lab6.Dialogs.DialogAddCategory;
import com.example.brightdays.lab6.Events.AddCategoryEvent;
import com.example.brightdays.lab6.Events.AddCategoryInterface;

import java.util.ArrayList;

/**
 * Created by User-PC on 15.04.2015.
 */


public class CategoryFragment extends Fragment {

    private void updateList()
    {
        try {
            items = new AsyncTaskDB.AsyncGetCategories(getActivity()).execute().get();
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, items);
            listView.setAdapter(arrayAdapter);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    ArrayList<String> items = null;
    ListView listView;

    class AddCategoryResponder implements AddCategoryInterface {

        @Override
        public void categoryAdded() {

            try {
                updateList();
            } catch (Exception e) {
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.category_list);
        Button button = (Button) view.findViewById(R.id.add_category);
        final AddCategoryEvent initiator = new AddCategoryEvent();
        AddCategoryResponder responder = new AddCategoryResponder();
        initiator.addListener(responder);
        updateList();
        button.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View view)
            {
                DialogAddCategory dialog = new DialogAddCategory();
                dialog.setAddCategoryEvent(initiator);
                dialog.show(getChildFragmentManager(), "tag");
            }
        });
        return view;
    }

}

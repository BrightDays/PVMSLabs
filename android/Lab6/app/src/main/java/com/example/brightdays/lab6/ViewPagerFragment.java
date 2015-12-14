package com.example.brightdays.lab6;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.view.ViewGroup;
import com.example.brightdays.lab6.Events.ChangeCategoryEvent;
import com.example.brightdays.lab6.Events.ChangeCategoryInterface;
import com.example.brightdays.lab6.Events.ChangePeriodEvent;
import com.example.brightdays.lab6.Events.ChangePeriodInterface;

import java.util.ArrayList;

public class ViewPagerFragment extends Fragment {
    public static final String TAG = ViewPagerFragment.class.getSimpleName();
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    String str;
    Button addValues = null;
    Button changePeriod = null;
    int type;
    ChangePeriodEvent periodInitiater;
    ChangeCategoryEvent categoryInitiater;

    private static int period = DBHelper.PERIOD_MONTH;
    private static ArrayList<String> selectedCategories;

    public static int getPeriod() {
        return period;
    }

    public static void setPeriod(int period) {
        ViewPagerFragment.period = period;
    }

    public static void setSelectedCategories(ArrayList<String> selectedCategories)
    {
        ViewPagerFragment.selectedCategories = selectedCategories;
    }

    public static ArrayList<String> getSelectedCategories()
    {
        return selectedCategories;
    }

    public static String getCriteria(String type) {
        if (type.equals(DBHelper.TAG_INCOME))
                return DBHelper.TAG_INCOME;
        if (type.equals(DBHelper.TAG_EXPENCES))
            return DBHelper.TAG_EXPENCES;
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt(MyActivity.ARG_KEY_CATEGORY);
        periodInitiater = new ChangePeriodEvent();
        categoryInitiater = new ChangeCategoryEvent();
        ChangePeriodResponder periodResponder = new ChangePeriodResponder();
        ChangeCategoryResponder categoryResponder = new ChangeCategoryResponder();
        periodInitiater.addListener(periodResponder);
        categoryInitiater.addListener(categoryResponder);
        try {
            selectedCategories = new AsyncTaskDB.AsyncGetCategories(getActivity()).execute().get();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tabbed, container, false);
        try {
            mViewPager = (ViewPager) v.findViewById(R.id.pager);
            updateViewPager();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    public void updateViewPager()
    {
        try {
            mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
            mViewPager.setAdapter(mSectionsPagerAdapter);
            mViewPager.setCurrentItem(mSectionsPagerAdapter.getCount() - 1);
        } catch (Exception e) {

        }
    }

    class ChangeCategoryResponder implements ChangeCategoryInterface {

        @Override
        public void categoryChanged() {
            updateViewPager();
        }
    }

    class ChangePeriodResponder implements ChangePeriodInterface {

        @Override
        public void periodChanged() {
            updateViewPager();
        }
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button addValue = (Button) getActivity().findViewById(R.id.addValues);
        addValue.setOnClickListener(new AddValuesOnClickListener(getActivity(), type));

        Button changePeriod = (Button) getActivity().findViewById(R.id.changePeriod);
        changePeriod.setOnClickListener(new ChangePeriodOnClickListener(getActivity(), periodInitiater));

        Button changeCategory = (Button) getActivity().findViewById(R.id.changeCat);
        changeCategory.setOnClickListener(new ChangeCatOnClickListener(getActivity(), categoryInitiater));
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public ArrayList<String> tableNames;
        private int count = -1;
        private DBHelper dbHelper;
        private String type;
        FragmentManager fm;

        public SectionsPagerAdapter(FragmentManager fm) throws Exception {
            super(fm);
            this.fm = fm;
            dbHelper = new DBHelper(getActivity());
            type = getArguments().getString(MyActivity.ARG_KEY_CATEGORY);
            tableNames = dbHelper.parsePageName(getCriteria(type));
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new TabbedContentFragment();
            Bundle args = new Bundle();
            args.putString(MyActivity.ARG_KEY_CATEGORY, type);
            args.putString(TabbedContentFragment.ARG_SECTION_NUMBER, tableNames.get(position));
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            if (count == -1) {
                count = tableNames.size();
            }
            return count;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tableNames.get(position);
        }
    }

    public static class TabbedContentFragment extends Fragment {

        public static final String ARG_SECTION_NUMBER = "section_number";
        private String pageDate, criteria, curDate;
        private String type;
        private View rootView;
        private ArrayList<Finance> al;
        private ListView lv = null;
        private DBHelper dbHelper;

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);
            if (isVisibleToUser && al == null) {
                pageDate = getArguments().getString(ARG_SECTION_NUMBER);
                type = getArguments().getString(MyActivity.ARG_KEY_CATEGORY);
                criteria = getCriteria(type);
                try {
                    al = new AsyncTaskDB.AsyncGetAllData(getActivity(), pageDate, criteria, getPeriod()).execute().get();
                    lv = (ListView) getView().findViewById(R.id.financeContent);
                    lv.setAdapter(new ViewPagerContentAdapter(getActivity(), R.layout.finance_content_fragment, al, criteria));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            pageDate = getArguments().getString(ARG_SECTION_NUMBER);
            type = getArguments().getString(MyActivity.ARG_KEY_CATEGORY);
            criteria = getCriteria(type);
            rootView = null;
            dbHelper = new DBHelper(getActivity());
            if (type.equals(DBHelper.TAG_INCOME) || type.equals(DBHelper.TAG_EXPENCES)) { //income and expences
                    rootView = inflater.inflate(R.layout.finance_content_fragment,
                            container, false);
                    lv = (ListView) rootView.findViewById(R.id.financeContent);
                    if (al != null)
                        lv.setAdapter(new ViewPagerContentAdapter(getActivity(), R.layout.finance_content_fragment, al, criteria));

                }
            return rootView;
        }
    }
}
package com.spit.techfest.matrix;

/**
 * Created by Sumeet on 27-06-2014.
 */
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

public class EventTabFragment extends Fragment {

    private static final String tag = "Fragment: ";
    private final int[] COLOR_RES = { R.color.material_red, R.color.material_deep_purple,
            R.color.material_indigo,R.color.material_teal,R.color.material_light_green,
            R.color.material_orange,R.color.material_brown};

    public static final String TAG = EventTabFragment.class
            .getSimpleName();
    PagerSlidingTabStrip tabs;

    public static EventTabFragment newInstance() {
        return new EventTabFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabs = (PagerSlidingTabStrip) view
                .findViewById(R.id.tabs);
        ViewPager pager = (ViewPager) view.findViewById(R.id.pager);
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int position) {
                tabs.setIndicatorColorResource(COLOR_RES[position]);
                tabs.setUnderlineColorResource(COLOR_RES[position]);
                //tabs.setTextColorResource(R.color.holo_blue_light);
                Drawable d = new ColorDrawable(getResources().getColor(COLOR_RES[position]));
                ((ActionBarActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(d);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        //For first tab;
        tabs.setIndicatorColorResource(COLOR_RES[0]);
        tabs.setUnderlineColorResource(COLOR_RES[0]);
        //tabs.setTextColorResource(R.color.holo_blue_light);
        Drawable d = new ColorDrawable(getResources().getColor(COLOR_RES[0]));
        ((ActionBarActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(d);


    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        private final String[] QUERY_TITLES = { "Pre_fest","Fun","BotMania","TechFun","Coding","Tech-Cult","Brain_Drain"};
        private final String[] TITLES = { "Pre fest","Fun","Bot Mania","Tech Fun","Coding","Tech-Cult","Brain Drain"};


        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }


        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            Log.v(tag, "" + position);
            try{


                return EventListFragment.newInstance("category", QUERY_TITLES[position]);
            }
            catch (Exception ex){}

            return null;
        }


    }


}

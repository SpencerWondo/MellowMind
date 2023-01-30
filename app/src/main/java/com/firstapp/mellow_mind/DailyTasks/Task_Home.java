package com.firstapp.mellow_mind.DailyTasks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firstapp.mellow_mind.Admin.Admin_Home;
import com.firstapp.mellow_mind.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.transition.MaterialSharedAxis;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;

public class Task_Home extends Fragment {

    TextView welcome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task__home, container, false);

        welcome = view.findViewById(R.id.wlecome);

        welcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Check off your Daily tasks at the end of each day. Click on the ICON of the task to read more about it!", Toast.LENGTH_LONG).show();
            }
        });

        SmartTabLayout tabLayout = view.findViewById(R.id.tabs);
        ViewPager viewPager = view.findViewById(R.id.pager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());

        viewPagerAdapter.addFragment(new TasksFragment(), "Tasks");
        viewPagerAdapter.addFragment(new CompletionFragment(), "History");
        viewPagerAdapter.addFragment(new TaskGuideFragment(), "Guides");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setViewPager(viewPager);

        return view;
    }

    public static class ViewPagerAdapter extends FragmentPagerAdapter{

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position){
            return titles.get(position);
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setExitTransition(new MaterialFadeThrough());
//        setReenterTransition(new MaterialFadeThrough());
//        setEnterTransition(new MaterialFadeThrough());
//
//        getEnterTransition();
//        getExitTransition();
//        getReenterTransition();

        setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.Y, true));
        setReenterTransition(new MaterialSharedAxis(MaterialSharedAxis.Y, true));
        setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.Y, true));

        getEnterTransition();
        getReenterTransition();
        getExitTransition();


    }
}
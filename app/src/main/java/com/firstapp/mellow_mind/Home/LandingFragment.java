package com.firstapp.mellow_mind.Home;

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

import com.firstapp.mellow_mind.Admin.Guide_Upload_Fragment;
import com.firstapp.mellow_mind.DailyTasks.Task_Home;
import com.firstapp.mellow_mind.Feed.AuthorsFragment;
import com.firstapp.mellow_mind.Feed.Feed_Fragment;
import com.firstapp.mellow_mind.Feed.Following_Fragment;
import com.firstapp.mellow_mind.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.transition.MaterialSharedAxis;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;

public class LandingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_landing, container, false);

        SmartTabLayout tabLayout = view.findViewById(R.id.tabs);
        ViewPager viewPager = view.findViewById(R.id.pager);

        Task_Home.ViewPagerAdapter viewPagerAdapter = new Task_Home.ViewPagerAdapter(getChildFragmentManager());

        viewPagerAdapter.addFragment(new HomeFragment(), "Home");
        viewPagerAdapter.addFragment(new MoodFragment(), "Log Mood");
        viewPagerAdapter.addFragment(new GuidesFragment(), "Guides");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setViewPager(viewPager);


        return view;
    }

    public static class ViewPagerAdapter extends FragmentPagerAdapter {

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
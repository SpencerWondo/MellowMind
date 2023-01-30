package com.firstapp.mellow_mind.Login_Register;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;

import com.firstapp.mellow_mind.DailyTasks.CompletionFragment;
import com.firstapp.mellow_mind.DailyTasks.Task_Home;
import com.firstapp.mellow_mind.DailyTasks.TasksFragment;
import com.firstapp.mellow_mind.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class NewLogin extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    Fragment selectedfragment = null;

    public int extrasPosition;

    float v = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);

        Intent intent = getIntent();
        if (intent == null){
            extrasPosition = 0;
        } else {
            extrasPosition = intent.getIntExtra("position", 0);
        }

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        getSupportActionBar().hide();

        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("SignUp"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(new login_tab(), "LOGIN");
        viewPagerAdapter.addFragment(new register_tab(), "SIGNUP");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(extrasPosition);

        tabLayout.setTranslationY(300);

        tabLayout.setAlpha(v);
        
        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
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
            switch (position){
                case 0:
                    login_tab tab0 = new login_tab();
                    return tab0;
                case 1:
                    register_tab tab1 = new register_tab();
                    return tab1;
            }
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

}
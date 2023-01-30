package com.firstapp.mellow_mind.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import com.firstapp.mellow_mind.Addiction.Addiction_home;
import com.firstapp.mellow_mind.DailyTasks.Task_Home;
import com.firstapp.mellow_mind.Feed.Feed_Home_Fragment;
import com.firstapp.mellow_mind.Home.Profile.ProfileFragment;
import com.firstapp.mellow_mind.Home.Settings.SettingsFragment;
import com.firstapp.mellow_mind.R;
import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Fragment selectedfragment = null;
    ChipNavigationBar chipNavigationBar, topnav;

    private AdView adView;

    float v = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        adView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);

        topnav = findViewById(R.id.top_nav);
        topnav.setItemSelected(R.id.nav_mood, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LandingFragment()).commit();

        //chipNavigationBar.setTranslationX(-800);
        topnav.setTranslationY(800);

        //chipNavigationBar.setAlpha(v);
        topnav.setAlpha(v);

        //chipNavigationBar.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(200).start();
        topnav.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(200).start();

        List<Fragment> list = new ArrayList<>();
        list.add(new HomeFragment());
        list.add(new GuidesFragment());
        list.add(new MoodFragment());
        list.add(new ProfileFragment());
        list.add(new SettingsFragment());
        list.add(new Addiction_home());
        list.add(new Task_Home());

        getSupportActionBar().hide();

        topMenu();
        //bottomMenu();

    }

    private void bottomMenu(){
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                        case R.id.bottom_nav_home:
                            selectedfragment = new HomeFragment();
                            break;
                        case R.id.Guides:
                            selectedfragment = new GuidesFragment();
                            break;
                        case R.id.add:
                            selectedfragment = new MoodFragment();
                            //Toast.makeText(MainActivity.this, "Mood Entering Clicked!", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    if (selectedfragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedfragment).commit();
                    }
            }
        });
    }

    private void topMenu(){
        topnav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case R.id.nav_mood:
                        selectedfragment = new LandingFragment();
                        //chipNavigationBar.setVisibility(View.VISIBLE);
                        break;
                    case R.id.feed:
                        selectedfragment = new Feed_Home_Fragment();
                        //chipNavigationBar.setVisibility(View.GONE);
                        break;
                    case R.id.tasks:
                        selectedfragment = new Task_Home();
                        //chipNavigationBar.setVisibility(View.GONE);
                        break;
                    case R.id.Profile:
                        selectedfragment = new ProfileFragment();
                        //chipNavigationBar.setVisibility(View.GONE);
                        break;
                    case R.id.settings:
                        selectedfragment = new SettingsFragment();
                        //chipNavigationBar.setVisibility(View.GONE);
                        break;
                }
                if (selectedfragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedfragment).commit();
                }
            }
        });
    }

}
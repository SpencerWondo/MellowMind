package com.firstapp.mellow_mind.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firstapp.mellow_mind.Home.GuidesFragment;
import com.firstapp.mellow_mind.Home.HomeFragment;
import com.firstapp.mellow_mind.Home.MoodFragment;
import com.firstapp.mellow_mind.Model.User;
import com.firstapp.mellow_mind.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;

public class Admin_Home extends AppCompatActivity {

    Button back;
    Fragment selectedfragment = null;
    ChipNavigationBar chipNavigationBar;

    TextView posts, followers, views, likes;

    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__home);

        posts = findViewById(R.id.post_num);
        followers = findViewById(R.id.followers_num);
        views = findViewById(R.id.views_num);
        likes = findViewById(R.id.likes_num);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        chipNavigationBar = findViewById(R.id.bottom_navigation);
        chipNavigationBar.setItemSelected(R.id.bottom_nav_home, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Admin_Account_Details()).commit();

        getSupportActionBar().hide();

        back = findViewById(R.id.Guide_return);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bottomMenu();
        UserInfo();

    }

    private void UserInfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                posts.setText(String.valueOf(user.getPosts()));
                followers.setText(String.valueOf(user.getFollowers()));
                views.setText(String.valueOf(user.getViews()));
                likes.setText(String.valueOf(user.getLikes()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void bottomMenu(){
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case R.id.analtyics:
                        selectedfragment = new Admin_Account_Details();
                        break;
                    case R.id.upload:
                        selectedfragment = new Guide_Upload_Fragment();
                        break;
                    case R.id.guides:
                        selectedfragment = new Admin_Guides_Fragment();
                        break;
                    case R.id.account:
                        selectedfragment = new Admin_Author_Details();
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
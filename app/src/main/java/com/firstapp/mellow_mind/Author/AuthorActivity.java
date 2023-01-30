package com.firstapp.mellow_mind.Author;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firstapp.mellow_mind.Adapter.GuideAdapter;
import com.firstapp.mellow_mind.Home.Profile.HistoryFragment;
import com.firstapp.mellow_mind.Home.Profile.MoodHistoryFragment;
import com.firstapp.mellow_mind.Home.Profile.ProfileFragment;
import com.firstapp.mellow_mind.Home.Profile.SavedFragment;
import com.firstapp.mellow_mind.Model.Guide;
import com.firstapp.mellow_mind.Model.User;
import com.firstapp.mellow_mind.R;
import com.firstapp.mellow_mind.Utils.SpacingItemDecorator;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AuthorActivity extends AppCompatActivity {

    Button back, follow;
    TextView username, fullname, description, label;
    ImageView profile_img;
    String authorID, username_label;
    ViewPager viewPager;
    TabLayout tabLayout;

    RecyclerView recyclerView;
    GuideAdapter guideAdapter;

    List<Guide> author_guides;
    private List<String> author;

    FirebaseUser firebaseUser;

    float v = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);

        getSupportActionBar().hide();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        back = findViewById(R.id.back_btn);
        username = findViewById(R.id.username_label);
        fullname = findViewById(R.id.fullname_label);
        profile_img = findViewById(R.id.profile_img);
        follow = findViewById(R.id.follow_btn);

        tabLayout = findViewById(R.id.tabs2);
        viewPager = findViewById(R.id.pager2);

        AuthorActivity.ViewPagerAdapter viewPagerAdapter = new AuthorActivity.ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(new Author_guides(), "Guides");
        viewPagerAdapter.addFragment(new Author_Info(), "Info");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        back.setTranslationX(800);
        username.setTranslationX(800);
        fullname.setTranslationX(800);
        profile_img.setTranslationX(800);
        viewPager.setTranslationX(800);
        tabLayout.setTranslationX(800);
        follow.setTranslationX(800);

        back.setAlpha(v);
        username.setAlpha(v);
        fullname.setAlpha(v);
        profile_img.setAlpha(v);
        viewPager.setAlpha(v);
        tabLayout.setAlpha(v);
        follow.setAlpha(v);

        back.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(400).start();
        username.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(700).start();
        fullname.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(700).start();
        profile_img.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(600).start();
        viewPager.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(600).start();
        tabLayout.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(400).start();
        follow.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(600).start();

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        authorID = preferences.getString("authorID", "none");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        UserInfo();

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fm) {
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

    private void UserInfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(authorID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (getApplicationContext() == null){
                    return;
                }

                User user = snapshot.getValue(User.class);

                Glide.with(getApplicationContext()).load(user.getImageURL()).into(profile_img);
                username.setText(user.getUsername());
                fullname.setText(user.getFullname());

                username_label = user.getUsername();

                isFollowing(user.getId(), follow);

                if (user.getId().equals(firebaseUser.getUid())){
                    follow.setVisibility(View.GONE);
                }

                follow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (follow.getText().toString().equals("Follow")){
                            FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                                    .child("Following").child(user.getId()).setValue(true);
                            FirebaseDatabase.getInstance().getReference().child("Follow").child(user.getId())
                                    .child("Followers").child(firebaseUser.getUid()).setValue(true);
                        } else {
                            FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                                    .child("Following").child(user.getId()).removeValue();
                            FirebaseDatabase.getInstance().getReference().child("Follow").child(user.getId())
                                    .child("Followers").child(firebaseUser.getUid()).removeValue();
                        }
                    }
                });




                //searchGuides(user.getId());

                //guideNum.setText("("+ (mySaves.size()-1) +")");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

//    private void searchGuides(String s){
//        Query query = FirebaseDatabase.getInstance().getReference("Guides").orderByChild("authorID")
//                .startAt(s)
//                .endAt(s+"\uf8ff");
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                author_guides.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    Guide guide = snapshot.getValue(Guide.class);
//                    author_guides.add(guide);
//                }
//
//                label.setText(username_label + "'s Guides ( " + author_guides.size() + " )");
//                guideAdapter = new GuideAdapter(getApplicationContext(), author_guides);
//                recyclerView.setAdapter(guideAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

//    private void Author_Guides(){
//        author = new ArrayList<>();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Guides")
//                .child(firebaseUser.getUid());
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    author.add(snapshot.getKey());
//                }
//
//                readGuides();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//
//        });
//    }

    private void isFollowing(final String userid, final Button button){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(firebaseUser.getUid()).child("Following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(userid).exists()){
                    button.setText("Unfollow");
                } else {
                    button.setText("Follow");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    private void readGuides(){
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Guides");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    Guide guide = snapshot.getValue(Guide.class);
//
//                    for (String id:author){
//                        if (guide.getGuideID().equals(id)){
//                            author_guides.add(guide);
//                        }
//                    }
//                }
//
//                guideAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

}
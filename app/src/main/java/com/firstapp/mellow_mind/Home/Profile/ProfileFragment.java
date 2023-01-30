package com.firstapp.mellow_mind.Home.Profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firstapp.mellow_mind.Adapter.GuideAdapter;
import com.firstapp.mellow_mind.Home.Settings.EditProfileActivity;
import com.firstapp.mellow_mind.Model.Guide;
import com.firstapp.mellow_mind.Model.User;
import com.firstapp.mellow_mind.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    RecyclerView recyclerView;
    GuideAdapter guideAdapter;
    List<Guide> saved_guides;

    TextView username, fullname, guideNum;
    Button edit;
    ImageView profile_img;

    private List<String> mySaves;

    FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        username = view.findViewById(R.id.username_label);
        fullname = view.findViewById(R.id.fullname_label);
        profile_img = view.findViewById(R.id.profile_img);

        edit = view.findViewById(R.id.edit_profile);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditProfileActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        SmartTabLayout tabLayout = view.findViewById(R.id.tabs);
        ViewPager viewPager = view.findViewById(R.id.pager);

        ProfileFragment.ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());

        viewPagerAdapter.addFragment(new SavedFragment(), "Saved Guides");
        viewPagerAdapter.addFragment(new HistoryFragment(), "Task History");
        viewPagerAdapter.addFragment(new MoodHistoryFragment(), "Mood History");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setViewPager(viewPager);


//        recyclerView = view.findViewById(R.id.saved_guides);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(20);
//        recyclerView.addItemDecoration(itemDecorator);
//
//        saved_guides = new ArrayList<>();
//        guideAdapter = new GuideAdapter(getContext(), saved_guides);
//        recyclerView.setAdapter(guideAdapter);
//
//        mysaves();
        UserInfo();

        return view;
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
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (getContext() == null){
                    return;
                }

                User user = snapshot.getValue(User.class);

                Glide.with(getContext()).load(user.getImageURL()).into(profile_img);
                username.setText(user.getUsername());
                fullname.setText(user.getFullname());

                //guideNum.setText("("+ (mySaves.size()-1) +")");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

//    private void mysaves(){
//        mySaves = new ArrayList<>();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Saved")
//                .child(firebaseUser.getUid());
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    mySaves.add(snapshot.getKey());
//                }
//
//                readSaved();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//
//        });
//    }
//
//    private void readSaved(){
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Guides");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    Guide guide = snapshot.getValue(Guide.class);
//
//                    for (String id:mySaves){
//                        if (guide.getGuideID().equals(id)){
//                            saved_guides.add(guide);
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
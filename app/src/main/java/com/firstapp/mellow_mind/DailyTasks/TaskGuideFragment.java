package com.firstapp.mellow_mind.DailyTasks;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.firstapp.mellow_mind.Adapter.GuideAdapter;
import com.firstapp.mellow_mind.Model.Guide;
import com.firstapp.mellow_mind.R;
import com.firstapp.mellow_mind.Utils.SpacingItemDecorator;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskGuideFragment extends Fragment {

    ImageView options;
    //EditText search_bar;
    TextView guide_num;

    FirebaseUser firebaseUser;
    StorageReference storageRef;
    FirebaseDatabase firebaseDatabase;

    private RecyclerView recyclerView;
    private GuideAdapter guideAdapter;
    private List<Guide> mGuides;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_guide, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        guide_num = view.findViewById(R.id.task_num);

        recyclerView = view.findViewById(R.id.tasks_guides);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(5);
        recyclerView.addItemDecoration(itemDecorator);

        mGuides = new ArrayList<>();

        readGuides();

        searchGuides("tasks");

        options = view.findViewById(R.id.options);

        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.trending:
                                Collections.sort(mGuides, Guide.GuideTrending);
                                guideAdapter.notifyDataSetChanged();
                                return true;
                            case R.id.az:
                                Collections.sort(mGuides, Guide.GuideAZ);
                                guideAdapter.notifyDataSetChanged();
                                return true;
                            case R.id.za:
                                Collections.sort(mGuides, Guide.GuideZA);
                                guideAdapter.notifyDataSetChanged();
                                return true;
                        }

                        return false;
                    }
                });
                popupMenu.inflate(R.menu.options);
                popupMenu.show();
            }
        });

//        search_bar = view.findViewById(R.id.searchbar);
//        search_bar.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                searchGuidesTitle(charSequence.toString().toLowerCase());
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

        return view;
    }

    private void readGuides(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Guides");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mGuides.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Guide guide = snapshot.getValue(Guide.class);

                    assert guide != null;
                    mGuides.add(guide);
                }

                guideAdapter = new GuideAdapter(getContext(), mGuides);
                Collections.sort(mGuides, Guide.GuideTrending);
                guideAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(guideAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void searchGuides(String s){
        Query query = FirebaseDatabase.getInstance().getReference("Guides").orderByChild("keyword")
                .startAt(s)
                .endAt(s+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mGuides.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Guide guide = snapshot.getValue(Guide.class);
                    mGuides.add(guide);
                }

                guide_num.setText("("+ mGuides.size() +")");
                guideAdapter = new GuideAdapter(getContext(), mGuides);
                recyclerView.setAdapter(guideAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void searchGuidesTitle(String s){
        Query query = FirebaseDatabase.getInstance().getReference("Guides").orderByChild("title")
                .startAt(s)
                .endAt(s+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mGuides.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Guide guide = snapshot.getValue(Guide.class);
                    mGuides.add(guide);
                }

                guideAdapter = new GuideAdapter(getContext(), mGuides);
                recyclerView.setAdapter(guideAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
package com.firstapp.mellow_mind.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.android.material.transition.MaterialFadeThrough;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Queue;


public class GuidesFragment extends Fragment {

    private RecyclerView recyclerView;
    private GuideAdapter guideAdapter;
    private List<Guide> mGuides;

    TextView guide_num;

    ImageView options;

    EditText search_bar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_guides, container, false);

        guide_num = view.findViewById(R.id.guide_list_num);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(0);
        recyclerView.addItemDecoration(itemDecorator);

        mGuides = new ArrayList<>();

        search_bar = view.findViewById(R.id.searchbar);
        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchGuides(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        options = view.findViewById(R.id.options);

        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.popular:
                                Collections.sort(mGuides, Guide.GuidePopular);
                                guideAdapter.notifyDataSetChanged();
                                return true;
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
                            case R.id.old_new:
                                linearLayoutManager.setReverseLayout(false);
//                                Collections.sort(mGuides, Guide.GuideON);
//                                guideAdapter.notifyDataSetChanged();
                                return false;
                            case R.id.new_old:
                                linearLayoutManager.setReverseLayout(true);
                                return true;
                        }

                        return false;
                    }
                });
                popupMenu.inflate(R.menu.options);
                popupMenu.show();
            }
        });


        readGuides();

        return view;
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

                guideAdapter = new GuideAdapter(getContext(), mGuides);
                recyclerView.setAdapter(guideAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

                guide_num.setText(mGuides.size() +"  Guides");
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
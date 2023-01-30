package com.firstapp.mellow_mind.Admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.firstapp.mellow_mind.Adapter.GuideAdapter;
import com.firstapp.mellow_mind.Adapter.GuideDeleteAdpater;
import com.firstapp.mellow_mind.Model.Guide;
import com.firstapp.mellow_mind.Model.User;
import com.firstapp.mellow_mind.R;
import com.firstapp.mellow_mind.Utils.SpacingItemDecorator;
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


public class Admin_Guides_Fragment extends Fragment {

    RecyclerView recyclerView;
    GuideDeleteAdpater guideDeleteAdpater;
    RelativeLayout empty;

    List<Guide> your_guides;
    private List<String> author;

    FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin__guides_, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView = view.findViewById(R.id.your_guides);
        empty = view.findViewById(R.id.empty);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(5);

        your_guides = new ArrayList<>();
        guideDeleteAdpater = new GuideDeleteAdpater(getContext(), your_guides);
        recyclerView.setAdapter(guideDeleteAdpater);

        UserInfo();

        return view;
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

                searchGuides(user.getId());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void searchGuides(String s){
        Query query = FirebaseDatabase.getInstance().getReference("Guides").orderByChild("authorID")
                .startAt(s)
                .endAt(s+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                your_guides.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Guide guide = snapshot.getValue(Guide.class);
                    your_guides.add(guide);
                }

                guideDeleteAdpater = new GuideDeleteAdpater(getContext(), your_guides);
                recyclerView.setAdapter(guideDeleteAdpater);

                if (!your_guides.isEmpty()){
                    empty.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
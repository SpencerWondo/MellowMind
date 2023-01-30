package com.firstapp.mellow_mind.Author;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.firstapp.mellow_mind.Adapter.GuideAdapter;
import com.firstapp.mellow_mind.Model.Guide;
import com.firstapp.mellow_mind.Model.User;
import com.firstapp.mellow_mind.R;
import com.firstapp.mellow_mind.Utils.SpacingItemDecorator;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Author_guides extends Fragment {
    String authorID;

    RecyclerView recyclerView;
    GuideAdapter guideAdapter;

    List<Guide> author_guides;
    private List<String> author;

    FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_author_guides, container, false);

        recyclerView = view.findViewById(R.id.guides);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(5);
        recyclerView.addItemDecoration(itemDecorator);

        author_guides = new ArrayList<>();
        guideAdapter = new GuideAdapter(getContext(), author_guides);
        recyclerView.setAdapter(guideAdapter);

        SharedPreferences preferences = getActivity().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        authorID = preferences.getString("authorID", "none");

        UserInfo();

        return view; 
    }

    private void UserInfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(authorID);
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
                author_guides.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Guide guide = snapshot.getValue(Guide.class);
                    author_guides.add(guide);
                }

                guideAdapter = new GuideAdapter(getContext(), author_guides);
                recyclerView.setAdapter(guideAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Author_Guides(){
        author = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Guides")
                .child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    author.add(snapshot.getKey());
                }

                readGuides();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

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

    private void readGuides(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Guides");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Guide guide = snapshot.getValue(Guide.class);

                    for (String id:author){
                        if (guide.getGuideID().equals(id)){
                            author_guides.add(guide);
                        }
                    }
                }

                guideAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
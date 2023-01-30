package com.firstapp.mellow_mind.Feed;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.firstapp.mellow_mind.Adapter.GuideAdapter;
import com.firstapp.mellow_mind.Adapter.UserAdapter;
import com.firstapp.mellow_mind.Model.Guide;
import com.firstapp.mellow_mind.Model.User;
import com.firstapp.mellow_mind.R;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Following_Fragment extends Fragment {

    RecyclerView recyclerView;
    UserAdapter userAdapter;
    private List<User> userList;
    private List<String> followingList;

    RelativeLayout empty;

    FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_following_, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        empty = view.findViewById(R.id.empty);

        recyclerView = view.findViewById(R.id.users);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        userList = new ArrayList<>();
        userAdapter = new UserAdapter(getContext(), userList, false);

        recyclerView.setAdapter(userAdapter);

        followingList = new ArrayList<>();

        getFollowing();

        return view;
    }

    private void getFollowing(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Follow")
                .child(firebaseUser.getUid()).child("Following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                followingList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    followingList.add(snapshot.getKey());
                }


                showUsers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showUsers(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    for (String id : followingList){
//                        assert user != null;
                        if (id.equals(user.getId())){
                            userList.add(user);
                        }
                    }
                }
                userAdapter.notifyDataSetChanged();

                if (!userList.isEmpty()){
                    empty.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}
package com.firstapp.mellow_mind.Feed;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firstapp.mellow_mind.Adapter.GuideAdapter;
import com.firstapp.mellow_mind.Adapter.UserAdapter;
import com.firstapp.mellow_mind.Model.Guide;
import com.firstapp.mellow_mind.Model.User;
import com.firstapp.mellow_mind.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AuthorsFragment extends Fragment {

    RecyclerView recyclerView;
    UserAdapter userAdapter;
    private List<User> userList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authors, container, false);

        recyclerView = view.findViewById(R.id.authors);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        userList = new ArrayList<>();
//        userAdapter = new UserAdapter(getContext(), userList, false);

//        recyclerView.setAdapter(userAdapter);

        searchUsers("True");

        return view;
    }

    private void searchUsers(String s){
        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("author")
                .startAt(s)
                .endAt(s+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    userList.add(user);
                }

                userAdapter = new UserAdapter(getContext(), userList, false);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
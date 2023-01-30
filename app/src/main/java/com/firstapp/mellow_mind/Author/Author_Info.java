package com.firstapp.mellow_mind.Author;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firstapp.mellow_mind.Model.User;
import com.firstapp.mellow_mind.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Author_Info extends Fragment {

    TextView bio, name, degree, email, phone, address, website;
    TextView posts, followers, views, likes;

    String authorID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_author__info, container, false);

        bio = view.findViewById(R.id.info_bio);
        name = view.findViewById(R.id.info_name);
        degree = view.findViewById(R.id.info_degree);
        email = view.findViewById(R.id.info_email);
        phone = view.findViewById(R.id.info_phone);
        address = view.findViewById(R.id.info_address);
        website = view.findViewById(R.id.info_website);

        posts = view.findViewById(R.id.post_num);
        followers = view.findViewById(R.id.followers_num);
        views = view.findViewById(R.id.views_num);
        likes = view.findViewById(R.id.likes_num);

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
                User user = snapshot.getValue(User.class);

                bio.setText(user.getBio());
                name.setText(user.getFullname());
                degree.setText(user.getDegree());
                email.setText(user.getEmail());
                phone.setText(user.getPhone());
                address.setText(user.getAddress());
                website.setText(user.getWebsite());

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

}
package com.firstapp.mellow_mind.Admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.disklrucache.DiskLruCache;
import com.firstapp.mellow_mind.Adapter.GuideAdapter;
import com.firstapp.mellow_mind.Model.Guide;
import com.firstapp.mellow_mind.Model.User;
import com.firstapp.mellow_mind.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Admin_Account_Details extends Fragment {

    ImageView profile;

    TextView followers, likes, views, posts, name;

    FirebaseUser firebaseUser;

    int like_num = 0;
    int view_num = 0;
    int post = 0;
    int follower = 0;

    boolean alreadyExecuted;

    private List<User> userList;
    private List<Guide> author_guides;
    private List<String> followingList;
    private List<String> author;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin__account__details, container, false);

        profile = view.findViewById(R.id.account_img);
        followers = view.findViewById(R.id.followers_num);
        likes = view.findViewById(R.id.like_num);
        views = view.findViewById(R.id.views_num);
        posts = view.findViewById(R.id.post_num);
        name = view.findViewById(R.id.name);

        userList = new ArrayList<>();
        author_guides = new ArrayList<>();
        followingList = new ArrayList<>();
        author = new ArrayList<>();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        UserInfo();

        return view;
    }

    private void UserInfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (getActivity() == null) {
                    return;
                }

                Glide.with(getActivity()).load(user.getImageURL()).into(profile);
                name.setText(user.getUsername());

                getFollowing();
                searchGuides(user.getId());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getFollowing(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Follow")
                .child(firebaseUser.getUid()).child("Followers");
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

                    follower = userList.size();
                    followers.setText(String.valueOf(userList.size()));
                }

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

                    like_num += guide.getLikes();
                    view_num += guide.getViews();
                }

                post = author_guides.size();

                posts.setText(String.valueOf(author_guides.size()));
                likes.setText(String.valueOf(like_num));
                views.setText(String.valueOf(view_num));

                follower = followingList.size(); 


                if (!alreadyExecuted){
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("followers", follower);
                    hashMap.put("likes", like_num);
                    hashMap.put("views", view_num);
                    hashMap.put("posts", post);
                    reference.updateChildren(hashMap);

                    alreadyExecuted = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
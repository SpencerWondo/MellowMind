package com.firstapp.mellow_mind.Home;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firstapp.mellow_mind.Adapter.GuideAdapter;
import com.firstapp.mellow_mind.Model.Guide;
import com.firstapp.mellow_mind.Model.User;
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


public class HomeFragment extends Fragment {

    TextView current_mood, placeholder;
    ImageView mood_img;

    RelativeLayout empty;

    FirebaseUser firebaseUser;
    StorageReference storageRef;
    FirebaseDatabase firebaseDatabase;

    private RecyclerView recyclerView;
    private GuideAdapter guideAdapter;
    private List<Guide> mGuides;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        current_mood = view.findViewById(R.id.mood_label);
        mood_img = view.findViewById(R.id.mood_img2);

        empty = view.findViewById(R.id.empty);

        placeholder = view.findViewById(R.id.current_mood);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                assert user != null;


                current_mood.setText(user.getCurrentMood());

                searchGuides(user.getCurrentMood().toLowerCase());

                if (user.getCurrentMood().equals("none")){
                    placeholder.setVisibility(View.VISIBLE);
                } else {
                    placeholder.setVisibility(View.GONE);
                }


                if (user.getCurrentMood().equals("Happy")){
                    mood_img.setBackgroundResource(R.drawable.happy2);
                }
                if (user.getCurrentMood().equals("Sad")){
                    mood_img.setBackgroundResource(R.drawable.sad2);
                }
                if (user.getCurrentMood().equals("Mad")){
                    mood_img.setBackgroundResource(R.drawable.mad2);
                }
                if (user.getCurrentMood().equals("Anxious")){
                    mood_img.setBackgroundResource(R.drawable.anxious2);
                }
                if (user.getCurrentMood().equals("Angry")){
                    mood_img.setBackgroundResource(R.drawable.angry2);
                }
                if (user.getCurrentMood().equals("Depressed")){
                    mood_img.setBackgroundResource(R.drawable.depressed);
                }
                if (user.getCurrentMood().equals("Stressed")){
                    mood_img.setBackgroundResource(R.drawable.stressed);
                }
                if (user.getCurrentMood().equals("Lazy")){
                    mood_img.setBackgroundResource(R.drawable.lazy);
                }
                if (user.getCurrentMood().equals("Unmotivated")){
                    mood_img.setBackgroundResource(R.drawable.unmotivated);
                }
                if (user.getCurrentMood().equals("Motivated")){
                    mood_img.setBackgroundResource(R.drawable.motivated);
                }
                if (user.getCurrentMood().equals("Confident")){
                    mood_img.setBackgroundResource(R.drawable.confident);
                }
                if (user.getCurrentMood().equals("Shy")){
                    mood_img.setBackgroundResource(R.drawable.shy);
                }
                if (user.getCurrentMood().equals("Positive")){
                    mood_img.setBackgroundResource(R.drawable.positive);
                }
                if (user.getCurrentMood().equals("Negative")){
                    mood_img.setBackgroundResource(R.drawable.negative);
                }
                if (user.getCurrentMood().equals("Confused")){
                    mood_img.setBackgroundResource(R.drawable.confused);
                }
                if (user.getCurrentMood().equals("Scared")){
                    mood_img.setBackgroundResource(R.drawable.scared);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView = view.findViewById(R.id.help_guides);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(5);
        recyclerView.addItemDecoration(itemDecorator);

        mGuides = new ArrayList<>();

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

                if (!mGuides.isEmpty()){
                    empty.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.Y, true));
        setReenterTransition(new MaterialSharedAxis(MaterialSharedAxis.Y, true));
        setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.Y, true));



        getEnterTransition();
        getReenterTransition();
        getExitTransition();
    }
}
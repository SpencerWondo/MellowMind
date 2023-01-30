package com.firstapp.mellow_mind.Home.Profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.firstapp.mellow_mind.Adapter.GuideAdapter;
import com.firstapp.mellow_mind.Adapter.MoodAdapter;
import com.firstapp.mellow_mind.Model.Guide;
import com.firstapp.mellow_mind.Model.Mood;
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

public class MoodHistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private MoodAdapter moodAdapter;
    private List<Mood> moods;

    EditText search_bar;

    FirebaseUser firebaseUser;

    RelativeLayout empty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mood_history, container, false);

        empty = view.findViewById(R.id.empty);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        

        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(5);
        recyclerView.addItemDecoration(itemDecorator);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        moods = new ArrayList<>();

        search_bar = view.findViewById(R.id.searchbar2);
        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchMood(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        readMood();

        return view;
    }

    private void searchMood(String s){
        Query query = FirebaseDatabase.getInstance().getReference("Mood")
                .child(firebaseUser.getUid())
                .orderByChild("Date")
                .startAt(s)
                .endAt(s+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                moods.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Mood mood = snapshot.getValue(Mood.class);
                    moods.add(mood);
                }

                moodAdapter = new MoodAdapter(getContext(), moods);
                recyclerView.setAdapter(moodAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readMood(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Mood")
                .child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                moods.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Mood mood = snapshot.getValue(Mood.class);

                    assert mood != null;

                    moods.add(mood);
                }

                moodAdapter = new MoodAdapter(getContext(), moods);
                recyclerView.setAdapter(moodAdapter);

                if (!moods.isEmpty()){
                    empty.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
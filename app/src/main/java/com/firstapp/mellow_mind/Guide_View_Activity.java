package com.firstapp.mellow_mind;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firstapp.mellow_mind.Adapter.GuideViewAdapter;
import com.firstapp.mellow_mind.Home.HomeFragment;
import com.firstapp.mellow_mind.Model.Guide;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Guide_View_Activity extends AppCompatActivity {

    Button back;

    String guideID;
    private RecyclerView recyclerView;
    private GuideViewAdapter guideViewAdapter;
    private List<Guide> guideList;

    float v = 0;
    int view;

    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide__view_);

        getWindow().getAttributes().windowAnimations = R.style.WindowAnimationTransition;

        back = findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        back.setTranslationX(800);

        back.setAlpha(v);

        back.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(800).start();


        getSupportActionBar().hide();

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        guideID = preferences.getString("guideID", "none");

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        guideList = new ArrayList<>();
        guideViewAdapter = new GuideViewAdapter(getApplicationContext(), guideList);
        recyclerView.setAdapter(guideViewAdapter);

        readGuides();
    }

    private void readGuides(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Guides").child(guideID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                guideList.clear();
                Guide guide = snapshot.getValue(Guide.class);
                guideList.add(guide);

                guideViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
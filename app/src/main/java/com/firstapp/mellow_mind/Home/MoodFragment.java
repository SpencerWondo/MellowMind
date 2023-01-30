package com.firstapp.mellow_mind.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.firstapp.mellow_mind.Model.User;
import com.firstapp.mellow_mind.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.transition.MaterialFadeThrough;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class MoodFragment extends Fragment{

    ImageView profile, happy;
    TextView name;
    Spinner mood_log;
    RelativeLayout log_area;

    AdView adView;

    FirebaseUser firebaseUser;
    StorageReference storageRef;

    Button mood_track;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mood, container, false);

        mood_track = view.findViewById(R.id.mood_track_btn);
        log_area = view.findViewById(R.id.log_area);
        //happy = view.findViewById(R.id.happy);
        profile = view.findViewById(R.id.profile_img_mood);

        adView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        name = view.findViewById(R.id.name);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        mood_log = view.findViewById(R.id.log_mood);
        String[] mood = {"Happy", "Angry", "Anxious", "Sad", "Mad", "Depressed", "Stressed", "Lazy", "Unmotivated", "Motivated", "Confident", "Shy", "Positive", "Negative", "Confused", "Scared"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, mood);


        mood_log.setAdapter(adapter);
        mood_log.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String current_mood = mood_log.getSelectedItem().toString();

                mood_track.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("currentMood", current_mood);

                        reference.updateChildren(hashMap);


                        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child("Mood").child(firebaseUser.getUid());

                        String moodKey = reference2.push().getKey();
                        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

                        String dateNum = dateFormat.format(new Date());

                        HashMap<String, Object> hashMap2 = new HashMap<>();

                        hashMap2.put("moodKey", moodKey);
                        hashMap2.put("Date", dateNum);
                        hashMap2.put("Mood", current_mood);

                        reference2.child(moodKey).setValue(hashMap2);

                        Toast.makeText(getContext(), "You Mood was Successfully Updated to: " + current_mood, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (getContext() == null){
                    return;
                }

                assert user != null;
                Glide.with(getContext()).load(user.getImageURL()).into(profile);
                name.setText(user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

//    private void moodHistory(){
//        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child("Mood").child(firebaseUser.getUid());
//
//        String moodKey = reference2.push().getKey();
//        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
//
//        String dateNum = dateFormat.format(new Date());
//
//        HashMap<String, Object> hashMap = new HashMap<>();
//
//        hashMap.put("moodKey", moodKey);
//        hashMap.put("Date", dateNum);
//        hashMap.put("Mood", )
//
//    }

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
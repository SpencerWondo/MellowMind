package com.firstapp.mellow_mind.Home.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firstapp.mellow_mind.Model.User;
import com.firstapp.mellow_mind.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AboutActivity extends AppCompatActivity {

    Button close;

    ImageView profile_img;

    TextView username, fullname, email, bio, date, id, mood;

    AdView adView;

    FirebaseUser firebaseUser;

    float v = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().hide();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        username = findViewById(R.id.username);
        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        bio = findViewById(R.id.bio);
        date = findViewById(R.id.date);
        id = findViewById(R.id.userid);
        mood = findViewById(R.id.mood);
        profile_img = findViewById(R.id.profile_image);
        close = findViewById(R.id.close_btn);




        username.setTranslationX(800);
        fullname.setTranslationX(800);
        email.setTranslationX(800);
        bio.setTranslationX(800);
        date.setTranslationX(800);
        id.setTranslationX(800);
        mood.setTranslationX(800);
        profile_img.setTranslationX(800);
        close.setTranslationX(800);

        username.setAlpha(v);
        fullname.setAlpha(v);
        email.setAlpha(v);
        bio.setAlpha(v);
        date.setAlpha(v);
        id.setAlpha(v);
        mood.setAlpha(v);
        profile_img.setAlpha(v);
        close.setAlpha(v);

        username.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(100).start();
        fullname.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(200).start();
        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        bio.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
        date.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        id.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();
        mood.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        profile_img.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(800).start();
        close.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(800).start();




        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        UserInfo();
    }

    private void UserInfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                Glide.with(getApplicationContext()).load(user.getImageURL()).into(profile_img);
                username.setText(user.getUsername());
                fullname.setText(user.getFullname());
                email.setText(user.getEmail());
                bio.setText(user.getBio());
                date.setText("User Since:  "+ user.getCreate_date());
                id.setText(user.getId());
                mood.setText(user.getCurrentMood());

                //guideNum.setText("("+ (mySaves.size()-1) +")");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
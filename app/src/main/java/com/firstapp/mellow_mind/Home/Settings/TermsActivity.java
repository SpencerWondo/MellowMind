package com.firstapp.mellow_mind.Home.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firstapp.mellow_mind.Home.MainActivity;
import com.firstapp.mellow_mind.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class TermsActivity extends AppCompatActivity {

    TextView terms, label, content;
    Button accept, back;

    FirebaseUser firebaseUser;
    StorageReference storageReference;

    float v = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        getSupportActionBar().hide();

        accept = findViewById(R.id.accept);
        label = findViewById(R.id.terms_label);
        content = findViewById(R.id.view_terms);
        back = findViewById(R.id.back_btn);




        accept.setTranslationX(800);
        label.setTranslationX(800);
        content.setTranslationX(800);
        back.setTranslationX(800);

        accept.setAlpha(v);
        label.setAlpha(v);
        content.setAlpha(v);
        back.setAlpha(v);

        accept.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(200).start();
        label.animate().translationX(0).alpha(1).setDuration(600).setStartDelay(200).start();
        content.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(200).start();
        back.animate().translationX(0).alpha(1).setDuration(500).setStartDelay(200).start();



        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //storageReference = FirebaseStorage.getInstance().getReference("uploads");

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        back = findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
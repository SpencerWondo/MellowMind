package com.firstapp.mellow_mind.Home.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firstapp.mellow_mind.LoginActivity;
import com.firstapp.mellow_mind.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PswrdResetActivity extends AppCompatActivity {

    Button reset, back;
    EditText reset_email;
    ImageView pic;

    float v = 0;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pswrd_reset);

        reset = findViewById(R.id.pswrd_reset);
        reset_email = findViewById(R.id.reset_password);
        back = findViewById(R.id.back_btn_reset);
        pic = findViewById(R.id.imageView5);

        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();



        reset.setTranslationY(800);
        reset_email.setTranslationX(800);
        back.setTranslationX(800);
        pic.setTranslationY(800);

        reset.setAlpha(v);
        reset_email.setAlpha(v);
        back.setAlpha(v);
        pic.setAlpha(v);

        reset.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(600).start();
        reset_email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        back.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        pic.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = reset_email.getText().toString();

                if (TextUtils.isEmpty(userEmail)){
                    Toast.makeText(PswrdResetActivity.this, "Please Enter Your Valid Email Address", Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(PswrdResetActivity.this, "An Email Has Been Sent!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(PswrdResetActivity.this, LoginActivity.class));
                            } else {
                                String message = task.getException().getMessage();
                                Toast.makeText(PswrdResetActivity.this, "An Error Has Occurred: " + message + " Please Try Again!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
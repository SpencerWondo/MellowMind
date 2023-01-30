package com.firstapp.mellow_mind.Login_Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firstapp.mellow_mind.R;
import com.firstapp.mellow_mind.SplashScreen;

public class Login_Landing extends AppCompatActivity {

    Button create, login;
    float v = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__landing);

        create = findViewById(R.id.create);
        login = findViewById(R.id.login);

        getSupportActionBar().hide();

        create.setTranslationY(300);
        login.setTranslationY(300);

        create.setAlpha(v);
        login.setAlpha(v);

        create.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
        login.animate().translationY(0).alpha(1).setDuration(1500).setStartDelay(100).start();

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Landing.this, NewLogin.class);
                intent.putExtra("position", 1);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Landing.this, NewLogin.class);
                startActivity(intent);
            }
        });

    }
}
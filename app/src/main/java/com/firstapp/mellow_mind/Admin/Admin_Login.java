package com.firstapp.mellow_mind.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firstapp.mellow_mind.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class Admin_Login extends AppCompatActivity {

    EditText admin_key;
    Button enter, back;
    TextView label;

    FirebaseUser firebaseUser;

    float v = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__login);

        admin_key = findViewById(R.id.admin_key);
        enter = findViewById(R.id.admin_enter);
        back = findViewById(R.id.back_btn);
        label = findViewById(R.id.label);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        admin_key.setTranslationX(800);
        enter.setTranslationX(800);
        back.setTranslationX(800);
        label.setTranslationX(800);

        admin_key.setAlpha(v);
        enter.setAlpha(v);
        back.setAlpha(v);
        label.setAlpha(v);

        admin_key.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(100).start();
        enter.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(200).start();
        back.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(200).start();
        label.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(200).start();



        getSupportActionBar().hide();

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txt_admin_key = admin_key.getText().toString().trim();

                if (Objects.equals(txt_admin_key, "admin")){
                    startActivity(new Intent(getApplicationContext(), Admin_Home.class));
                }

                if (TextUtils.isEmpty(txt_admin_key)){
                    Toast.makeText(Admin_Login.this, "Enter your the Admin Key", Toast.LENGTH_SHORT).show();
                }

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("author", "True");
                reference.updateChildren(hashMap);
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
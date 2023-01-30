package com.firstapp.mellow_mind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firstapp.mellow_mind.Home.MainActivity;
import com.firstapp.mellow_mind.Home.Settings.PswrdResetActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button mLoginBtn;
    TextView mCreateBtn, reset;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.input_email);
        mPassword = findViewById(R.id.input_password);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.login_page);
        mCreateBtn = findViewById(R.id.signup_btn);
        reset = findViewById(R.id.reset_link);

        getSupportActionBar().hide();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PswrdResetActivity.class));
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input_email = mEmail.getText().toString().trim();
                String input_password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(input_email)){
                    mEmail.setError("Email is required!");
                    return;
                }
                if(TextUtils.isEmpty(input_password)){
                    mEmail.setError("Password is required!");
                    return;
                }
                if(input_password.length() < 6){
                    mPassword.setError("Password must be more than 6 characters!");
                    return;
                }


                // authenticate the user

                fAuth.signInWithEmailAndPassword(input_email, input_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext() , MainActivity.class));
                        }else
                            Toast.makeText(LoginActivity.this, "Login Attempt Failed!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
            }
        });

    }
}
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {
    EditText mFullName, mEmail, mPassword, mPasswordConfirm, mUsername;
    Button mRegisterbtn;
    TextView mLoginbtn;
    FirebaseAuth fAuth;

    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mUsername = findViewById((R.id.input_username));
        mFullName = findViewById(R.id.input_fullname);
        mEmail = findViewById(R.id.input_email4);
        mPassword = findViewById(R.id.input_password2);
        mPasswordConfirm = findViewById(R.id.input_password_confirm);
        mRegisterbtn = findViewById(R.id.signup_btn);
        mLoginbtn = findViewById(R.id.login_page);


        fAuth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();

        mLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });


        mRegisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_Fullname = mFullName.getText().toString().trim();
                String txt_Username = mUsername.getText().toString().trim();
                String txt_Email = mEmail.getText().toString().trim();
                String txt_Password = mPassword.getText().toString().trim();
                String txt_PasswordConfirm = mPasswordConfirm.getText().toString().trim();



                if (TextUtils.isEmpty(txt_Fullname) || TextUtils.isEmpty(txt_Username) || TextUtils.isEmpty(txt_Email) || TextUtils.isEmpty(txt_Password) || TextUtils.isEmpty(txt_PasswordConfirm)){                         mEmail.setError("Email is Required.");
                        Toast.makeText(RegistrationActivity.this, "All Fields are Required", Toast.LENGTH_SHORT).show();
                    }

                else if (txt_Password.length() < 6){
                    Toast.makeText(RegistrationActivity.this, "Pasword must be at least 6 Characters Long!", Toast.LENGTH_SHORT).show();
                }

                else if (!txt_PasswordConfirm.matches(txt_Password)){
                    mPasswordConfirm.setError("Passwords Do Not Match!");
                    mPasswordConfirm.requestFocus();
                    return;
                }

                else {
                    register(txt_Fullname, txt_Username, txt_Email, txt_Password);
                }

            }
        });

    }

    private void register (String mFullname, String mUsername, String mEmail, String mPassword){
        fAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = fAuth.getCurrentUser();
                            assert firebaseUser != null;
                            String userId = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userId);
                            hashMap.put("username", mUsername);
                            hashMap.put("fullname", mFullname);
                            hashMap.put("bio", "");
                            hashMap.put("currentMood", "");
                            hashMap.put("profileIMG", "");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        } else {
                            Toast.makeText(RegistrationActivity.this, "This Email is Already Taken" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }

}

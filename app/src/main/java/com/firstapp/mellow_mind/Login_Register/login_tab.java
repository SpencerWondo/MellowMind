package com.firstapp.mellow_mind.Login_Register;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firstapp.mellow_mind.Home.MainActivity;
import com.firstapp.mellow_mind.Home.Settings.PswrdResetActivity;
import com.firstapp.mellow_mind.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_tab extends Fragment {

    EditText mEmail, mPassword;
    Button mLoginBtn;
    TextView reset;
    FirebaseAuth fAuth;

    float v = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_tab, container, false);

        mEmail = view.findViewById(R.id.input_email);
        mPassword = view.findViewById(R.id.input_password);
        mLoginBtn = view.findViewById(R.id.login_page);
        reset = view.findViewById(R.id.reset_link);

        fAuth = FirebaseAuth.getInstance();


        mEmail.setTranslationX(800);
        mPassword.setTranslationX(800);
        mLoginBtn.setTranslationX(800);
        reset.setTranslationX(800);

        mEmail.setAlpha(v);
        mPassword.setAlpha(v);
        mLoginBtn.setAlpha(v);
        reset.setAlpha(v);

        mEmail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        mPassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        mLoginBtn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        reset.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PswrdResetActivity.class));
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
                            Toast.makeText(getContext(), "Login Successful.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext() , MainActivity.class));
                        }else
                            Toast.makeText(getContext(), "Login Attempt Failed!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        return view;
    }

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
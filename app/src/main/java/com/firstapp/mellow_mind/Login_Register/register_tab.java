package com.firstapp.mellow_mind.Login_Register;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firstapp.mellow_mind.Home.MainActivity;
import com.firstapp.mellow_mind.Home.Settings.TermsActivity;
import com.firstapp.mellow_mind.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.transition.MaterialFadeThrough;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class register_tab extends Fragment {

    CheckBox terms;
    boolean termsTF;
    TextView terms_label;
    EditText mFullName, mEmail, mPassword, mPhone, mUsername;
    Button mRegisterbtn;
    FirebaseAuth fAuth;

    DatabaseReference reference;

    float v = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_tab, container, false);

        mUsername = view.findViewById((R.id.input_username));
        mFullName = view.findViewById(R.id.input_fullname);
        mEmail = view.findViewById(R.id.input_email4);
        mPassword = view.findViewById(R.id.input_password2);
        mPhone = view.findViewById(R.id.input_phone);
        mRegisterbtn = view.findViewById(R.id.signup_btn);
        terms = view.findViewById(R.id.ch_terms);
        terms_label = view.findViewById(R.id.terms_label);


        mUsername.setTranslationX(800);
        mFullName.setTranslationX(800);
        mEmail.setTranslationX(800);
        mPassword.setTranslationX(800);
        mPhone.setTranslationX(800);
        mRegisterbtn.setTranslationX(800);
        terms.setTranslationX(800);
        terms_label.setTranslationX(800);

        mUsername.setAlpha(v);
        mFullName.setAlpha(v);
        mEmail.setAlpha(v);
        mPassword.setAlpha(v);
        mPhone.setAlpha(v);
        mRegisterbtn.setAlpha(v);
        terms.setAlpha(v);
        terms_label.setAlpha(v);

        mUsername.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        mFullName.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        mEmail.animate().translationX(0).alpha(1).setDuration(600).setStartDelay(300).start();
        mPassword.animate().translationX(0).alpha(1).setDuration(1400).setStartDelay(300).start();
        mPhone.animate().translationX(0).alpha(1).setDuration(1200).setStartDelay(300).start();
        mRegisterbtn.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        terms.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        terms_label.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(300).start();

        fAuth = FirebaseAuth.getInstance();

        terms_label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TermsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                startActivity(intent);
            }
        });

        //terms.setButtonTintList(ColorStateList.valueOf(getContext().getColor(R.color.black)));

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (terms.isChecked()){
                    termsTF = true;
                    terms.setButtonTintList(ColorStateList.valueOf(getContext().getColor(R.color.teal_200)));
                } else {
                    termsTF = false;
                    terms.setButtonTintList(ColorStateList.valueOf(getContext().getColor(R.color.white)));
                }
            }
        });

        mRegisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_Fullname = mFullName.getText().toString().trim();
                String txt_Username = mUsername.getText().toString().trim();
                String txt_Email = mEmail.getText().toString().trim();
                String txt_Password = mPassword.getText().toString().trim();
                String txt_PasswordConfirm = mPhone.getText().toString().trim();

                if (!termsTF) {
                    Toast.makeText(getContext(), "You have to Read the Terms of Service!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(txt_Fullname) || TextUtils.isEmpty(txt_Username) || TextUtils.isEmpty(txt_Email) || TextUtils.isEmpty(txt_Password) || TextUtils.isEmpty(txt_PasswordConfirm)) {
                    mEmail.setError("Email is Required.");
                    mFullName.setError("Name is Required.");
                    mUsername.setError("Username is Required.");
                    mPhone.setError("Phone Number is Required.");
                    mPassword.setError("Password is Required.");
                    Toast.makeText(getContext(), "All Fields are Required", Toast.LENGTH_SHORT).show();
                } else if (txt_Password.length() < 6) {
                    Toast.makeText(getContext(), "Pasword must be at least 6 Characters Long!", Toast.LENGTH_SHORT).show();
                }

//                else if (!txt_PasswordConfirm.matches(txt_Password)){
//                    mPasswordConfirm.setError("Passwords Do Not Match!");
//                    mPasswordConfirm.requestFocus();
//                    return;
//                }

                else {
                    register(txt_Fullname, txt_Username, txt_Email, txt_Password);
                }

            }
        });

        return view;
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
                        Integer num_followers = 0;
                        Integer num_following = 0;

                        DateFormat dateFormat = new SimpleDateFormat("EEE, MMM  d, ''yyyy");
                        String dateString = dateFormat.format(new Date());

                        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("id", userId);
                        hashMap.put("username", mUsername);
                        hashMap.put("fullname", mFullname);
                        hashMap.put("email", mEmail);
                        hashMap.put("phone", mPhone);
                        hashMap.put("create_date", dateString);
                        hashMap.put("bio", "");
                        hashMap.put("currentMood", "none");
                        hashMap.put("profileIMG", "");

                        hashMap.put("author", "False");
                        hashMap.put("location", "");
                        hashMap.put("followers", num_followers);
                        hashMap.put("following", num_following);
                        hashMap.put("terms", "");

                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(getContext(), TermsActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                //finish();
                            }
                        });
                    } else {
                        Toast.makeText(getContext(), "This Email is Already Taken" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
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
package com.firstapp.mellow_mind.Home.Settings;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firstapp.mellow_mind.Admin.Admin_Login;
import com.firstapp.mellow_mind.Login_Register.NewLogin;
import com.firstapp.mellow_mind.Model.User;
import com.firstapp.mellow_mind.R;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsFragment extends Fragment {

    ImageView admin, password, profile_edit, user_info, terms_btn;

    ImageView profile_img;
    TextView username, fullname, id;

    Button logout;

    FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        admin = view.findViewById(R.id.admin_btn);

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Admin_Login.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        logout = view.findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), NewLogin.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        password = view.findViewById(R.id.change_pswrd_btn);

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PswrdResetActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        profile_edit = view.findViewById(R.id.edit_profile_btn);

        profile_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditProfileActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        user_info = view.findViewById(R.id.user_info_btn);

        user_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AboutActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        terms_btn= view.findViewById(R.id.terms_btn);

        terms_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TermsActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        profile_img = view.findViewById(R.id.profile_img);
        username = view.findViewById(R.id.username_label);
        fullname = view.findViewById(R.id.fullname_label);
        id = view.findViewById(R.id.user_id);

        UserInfo();


        return view;
    }

    private void UserInfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (getContext() == null){
                    return;
                }

                User user = snapshot.getValue(User.class);

                Glide.with(getContext()).load(user.getImageURL()).into(profile_img);
                username.setText(user.getUsername());
                fullname.setText(user.getFullname());
                id.setText(user.getId());

                //guideNum.setText("("+ (mySaves.size()-1) +")");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
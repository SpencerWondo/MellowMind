package com.firstapp.mellow_mind.Admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.firstapp.mellow_mind.Model.User;
import com.firstapp.mellow_mind.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Admin_Author_Details extends Fragment {

    EditText degree, address, email, phone, website;

    Button update;

    FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin__author__details, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        degree = view.findViewById(R.id.degree);
        address = view.findViewById(R.id.address);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        website = view.findViewById(R.id.website);

        update = view.findViewById(R.id.update_profile);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                degree.setText(user.getDegree());
                address.setText(user.getAddress());
                email.setText(user.getEmail());
                phone.setText(user.getPhone());
                website.setText(user.getWebsite());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(
                        degree.getText().toString(),
                        address.getText().toString(),
                        email.getText().toString(),
                        phone.getText().toString(),
                        website.getText().toString()
                );
            }
        });

        return view;
    }
    private void updateProfile(String degree, String address, String email, String phone, String website){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("degree", degree);
        hashMap.put("address", address);
        hashMap.put("email", email);
        hashMap.put("phone", phone);
        hashMap.put("website", website);

        reference.updateChildren(hashMap);
    }
}
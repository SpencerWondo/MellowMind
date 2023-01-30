package com.firstapp.mellow_mind.Admin;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.firstapp.mellow_mind.Home.MainActivity;
import com.firstapp.mellow_mind.Home.Settings.EditProfileActivity;
import com.firstapp.mellow_mind.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Guide_Upload_Fragment extends Fragment {

    EditText title, time, body, keyword, author;
    Button upload;
    Spinner mood_log;

    ImageView image;

    private FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide__upload_, container, false);

        title = view.findViewById(R.id.Guide_title);
        time = view.findViewById(R.id.Guide_time);
        body = view.findViewById(R.id.Guide_body);

        mood_log = view.findViewById(R.id.log_mood);

        author = view.findViewById(R.id.Guide_author);
        //image = view.findViewById(R.id.Guide_image);


        upload = view.findViewById(R.id.Guide_enter);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        String[] mood = {"Happy", "Angry", "Anxious", "Sad", "Mad", "Depressed", "Stressed", "Lazy", "Unmotivated", "Motivated", "Confident", "Shy", "Positive", "Negative", "Confused", "Scared", "Tasks"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, mood);


        mood_log.setAdapter(adapter);
        mood_log.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String current_mood = mood_log.getSelectedItem().toString().toLowerCase().trim();

                upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String txt_title = title.getText().toString().trim();
                        String txt_search = title.getText().toString().toLowerCase().trim();
                        String txt_time = time.getText().toString().trim();
                        String txt_body = body.getText().toString().trim();
                        String txt_author = author.getText().toString().trim();
                        Integer num_likes = 0;
                        Integer num_views = 0;

                        DateFormat dateFormat1 = new SimpleDateFormat("MM-dd-yyyy");

                        String dateNum = dateFormat1.format(new Date());

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Guides");

                        String guideID = reference.push().getKey();

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("title", txt_title);
                        hashMap.put("search_title", txt_search);
                        hashMap.put("likes", num_likes);
                        hashMap.put("views", num_views);
                        hashMap.put("time", txt_time);
                        hashMap.put("body", txt_body);
                        hashMap.put("keyword", current_mood);
                        hashMap.put("guideID", guideID);

                        hashMap.put("author", txt_author);
                        hashMap.put("authorID", firebaseUser.getUid());
                        hashMap.put("date", dateNum);

                        reference.child(guideID).setValue(hashMap);

                        Toast.makeText(getActivity(), "Guide has been successfully uploaded", Toast.LENGTH_SHORT).show();

                        title.getText().clear();
                        time.getText().clear();
                        body.getText().clear();
                        author.getText().clear();

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;

    }

}
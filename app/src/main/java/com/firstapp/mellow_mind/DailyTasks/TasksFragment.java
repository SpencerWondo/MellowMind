package com.firstapp.mellow_mind.DailyTasks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.firstapp.mellow_mind.Model.Task;
import com.firstapp.mellow_mind.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TasksFragment extends Fragment {

    CheckBox sleep, teeth, shower, exercise, water, clean, love, scare;

    boolean sleepTF, teethTF, showerTF, exerciseTF, waterTF, cleanTF, loveTF, scareTF;

    private FirebaseUser firebaseUser;

    AdView adView;

    ImageView a, b, c, d, e, f, g;

    String taskID;

    List<Task> tasks;
    List<Task> mTasks;

    Button update;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        sleep = view.findViewById(R.id.ch_sleep);
        teeth = view.findViewById(R.id.ch_teeth);
        shower = view.findViewById(R.id.ch_shower);
        exercise = view.findViewById(R.id.ch_exercise);
        water = view.findViewById(R.id.ch_water);
        clean = view.findViewById(R.id.ch_clean);
        love = view.findViewById(R.id.ch_love);

        adView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        a = view.findViewById(R.id.image1);
        b = view.findViewById(R.id.image2);
        c = view.findViewById(R.id.image3);
        d = view.findViewById(R.id.image4);
        e = view.findViewById(R.id.image5);
        f = view.findViewById(R.id.image6);
        g = view.findViewById(R.id.image7);

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Studies have shown that 8 hours a sleep each day is the perfect number to stay Happy and Healthy", Toast.LENGTH_LONG).show();
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Brushing your teeth daily is not only essential for your Physical Health but a crucial step in anyone's daily routine to feel accomplished", Toast.LENGTH_LONG).show();
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Showering each and every day is a huge pert of keeping yourself both physically and mentally healthy. Showering gives you a private personal time to reflect", Toast.LENGTH_LONG).show();
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Exercising gives people something to feel accomplished about. One of the leading causes for depression is people being unhappy with their physical state", Toast.LENGTH_LONG).show();
            }
        });
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Not drinking enough water everyday has been shown to lead to some forms of depression", Toast.LENGTH_LONG).show();
            }
        });
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Cleaning is something that gives you the sense of accomplishment and a sense of a new start and does wonders for your mental health allowing you to feel decluttered", Toast.LENGTH_LONG).show();
            }
        });
        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Doing things that you love make you a happier person and if you try to do at least one thing you love everyday it will make you a happier person", Toast.LENGTH_LONG).show();
            }
        });




        update = view.findViewById(R.id.task_enter);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sleep.isChecked()){
                    sleepTF = true;
                } else {
                    sleepTF = false;
                }
            }
        });

        teeth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (teeth.isChecked()){
                    teethTF = true;
                } else {
                    teethTF = false;
                }
            }
        });

        shower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shower.isChecked()){
                    showerTF = true;
                } else {
                    showerTF = false;
                }
            }
        });

        exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (exercise.isChecked()){
                    exerciseTF = true;
                } else {
                    exerciseTF = false;
                }
            }
        });

        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (water.isChecked()){
                    waterTF = true;
                } else {
                    waterTF = false;
                }
            }
        });

        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clean.isChecked()){
                    cleanTF = true;
                } else {
                    cleanTF = false;
                }
            }
        });

        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (love.isChecked()){
                    loveTF = true;
                } else {
                    loveTF = false;
                }
            }
        });

        tasks = new ArrayList<>();
        mTasks = new ArrayList<>();

        DateFormat dateFormat1 = new SimpleDateFormat("MM-dd-yyyy");

        String dateNum = dateFormat1.format(new Date());

        searchTasks(dateNum);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateFormat dateFormat = new SimpleDateFormat("EEE, MMM  d, ''yyyy");
                DateFormat dateFormat2 = new SimpleDateFormat("MM-dd-yyyy");

                String dateString = dateFormat.format(new Date());
                String dateNum1 = dateFormat2.format(new Date());


                if ( tasks.size() == 0 ) {

                    final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Tasks").child(firebaseUser.getUid());

                    String TaskID = reference.push().getKey();

                    HashMap<String, Object> hashMap = new HashMap<>();

                    hashMap.put("UserID", firebaseUser.getUid());
                    hashMap.put("TaskID", TaskID);
                    hashMap.put("Date", dateString);
                    hashMap.put("Date_num", dateNum1);

                    hashMap.put("sleep", sleepTF);
                    hashMap.put("teeth", teethTF);
                    hashMap.put("shower", showerTF);
                    hashMap.put("exercise", exerciseTF);
                    hashMap.put("water", waterTF);
                    hashMap.put("clean", cleanTF);
                    hashMap.put("love", loveTF);

                    reference.child(TaskID).setValue(hashMap);

                    Toast.makeText(getContext(), "Your Daily Tasks Have Been Successfully Archived", Toast.LENGTH_SHORT).show();

                } if ( tasks.size() > 0 ){
                    //Toast.makeText(getContext(), "You Can Only Log Your Tasks Once A Day!", Toast.LENGTH_SHORT).show();

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Tasks").child(firebaseUser.getUid()).child(taskID);

                    HashMap<String, Object> hashMap2 = new HashMap<>();

                    hashMap2.put("sleep", sleepTF);
                    hashMap2.put("teeth", teethTF);
                    hashMap2.put("shower", showerTF);
                    hashMap2.put("exercise", exerciseTF);
                    hashMap2.put("water", waterTF);
                    hashMap2.put("clean", cleanTF);
                    hashMap2.put("love", loveTF);

                    reference.updateChildren(hashMap2);

                    Toast.makeText(getContext(), "Your Daily Tasks Have Been Successfully Updated!", Toast.LENGTH_SHORT).show();
               }

            }
        });

        return view;
    }

    private void searchTasks(String s){
        Query query = FirebaseDatabase.getInstance().getReference("Tasks").child(firebaseUser.getUid()).orderByChild("Date_num")
                .startAt(s)
                .endAt(s+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tasks.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Task task = snapshot.getValue(Task.class);
                    taskID = task.getTaskID();
                    tasks.add(task);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private int readTasks(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tasks")
                .child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mTasks.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Task task = snapshot.getValue(Task.class);

                    assert task != null;
                    mTasks.add(task);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return mTasks.size();
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
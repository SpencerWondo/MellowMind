package com.firstapp.mellow_mind.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firstapp.mellow_mind.Model.Task;
import com.firstapp.mellow_mind.Model.User;
import com.firstapp.mellow_mind.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    public Context mContext;
    public List<Task> mTasks;

    private FirebaseUser firebaseUser;

    public TaskAdapter(Context mContext, List<Task> mTasks){
        this.mContext = mContext;
        this.mTasks = mTasks;

    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.task_item, viewGroup, false);
        return new TaskAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder viewHolder, int i) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Task task = mTasks.get(i);

        viewHolder.date.setText(task.getDate());

        UserInfo(viewHolder.profile);

        if (task.getSleep()){
            viewHolder.sleep.setVisibility(View.VISIBLE);
        } else {
            viewHolder.sleep.setVisibility(View.INVISIBLE);
        }


        if (task.getTeeth()){
            viewHolder.teeth.setVisibility(View.VISIBLE);
        } else {
            viewHolder.teeth.setVisibility(View.INVISIBLE);
        }


        if (task.getShower()){
            viewHolder.shower.setVisibility(View.VISIBLE);
        } else {
            viewHolder.shower.setVisibility(View.INVISIBLE);
        }


        if (task.getExercise()){
            viewHolder.exercise.setVisibility(View.VISIBLE);
        } else {
            viewHolder.exercise.setVisibility(View.INVISIBLE);
        }


        if (task.getWater()){
            viewHolder.water.setVisibility(View.VISIBLE);
        } else {
            viewHolder.water.setVisibility(View.INVISIBLE);
        }


        if (task.getClean()){
            viewHolder.clean.setVisibility(View.VISIBLE);
        } else {
            viewHolder.clean.setVisibility(View.INVISIBLE);
        }


        if (task.getLove()){
            viewHolder.love.setVisibility(View.VISIBLE);
        } else {
            viewHolder.love.setVisibility(View.INVISIBLE);
        }

    }

    private void UserInfo(final ImageView profile){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                assert user != null;
                Glide.with(mContext).load(user.getImageURL()).into(profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView date;
        public ImageView sleep, teeth, shower, exercise, water, clean, love, scare, profile, delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sleep = itemView.findViewById(R.id.sleep);
            teeth = itemView.findViewById(R.id.teeth);
            shower = itemView.findViewById(R.id.shower);
            exercise = itemView.findViewById(R.id.exercise);
            water = itemView.findViewById(R.id.water);
            clean = itemView.findViewById(R.id.clean);
            love = itemView.findViewById(R.id.love);
            //scare = itemView.findViewById(R.id.scares);

            date = itemView.findViewById(R.id.task_date);
            profile = itemView.findViewById(R.id.profile_pic);
        }
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }
}

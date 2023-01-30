package com.firstapp.mellow_mind.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firstapp.mellow_mind.Login_Register.NewLogin;
import com.firstapp.mellow_mind.Model.Mood;
import com.firstapp.mellow_mind.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MoodAdapter extends RecyclerView.Adapter<MoodAdapter.ViewHolder> {

    public Context mContext;
    public List<Mood> mMood;

    private FirebaseUser firebaseUser;

    public MoodAdapter(Context mContext, List<Mood> mMood){
        this.mContext = mContext;
        this.mMood = mMood;
    }

    @NonNull
    @Override
    public MoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.mood_item, viewGroup, false);
        return new MoodAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Mood mood = mMood.get(i);

        viewHolder.date.setText(mood.getDate());
        viewHolder.current_mood.setText(mood.getMood());

        if (mood.getMood().equals("Happy")){
            viewHolder.mood_img.setBackgroundResource(R.drawable.happy2);
        }
        if (mood.getMood().equals("Sad")){
            viewHolder.mood_img.setBackgroundResource(R.drawable.sad2);
        }
        if (mood.getMood().equals("Mad")){
            viewHolder.mood_img.setBackgroundResource(R.drawable.mad2);
        }
        if (mood.getMood().equals("Anxious")){
            viewHolder.mood_img.setBackgroundResource(R.drawable.anxious2);
        }
        if (mood.getMood().equals("Angry")){
            viewHolder.mood_img.setBackgroundResource(R.drawable.angry2);
        }
        if (mood.getMood().equals("Depressed")){
            viewHolder.mood_img.setBackgroundResource(R.drawable.depressed);
        }
        if (mood.getMood().equals("Stressed")){
            viewHolder.mood_img.setBackgroundResource(R.drawable.stressed);
        }
        if (mood.getMood().equals("Lazy")){
            viewHolder.mood_img.setBackgroundResource(R.drawable.lazy);
        }


        if (mood.getMood().equals("Unmotivated")){
            viewHolder.mood_img.setBackgroundResource(R.drawable.unmotivated);
        }
        if (mood.getMood().equals("Motivated")){
            viewHolder.mood_img.setBackgroundResource(R.drawable.motivated);
        }
        if (mood.getMood().equals("Confident")){
            viewHolder.mood_img.setBackgroundResource(R.drawable.confident);
        }
        if (mood.getMood().equals("Shy")){
            viewHolder.mood_img.setBackgroundResource(R.drawable.shy);
        }


        if (mood.getMood().equals("Positive")){
            viewHolder.mood_img.setBackgroundResource(R.drawable.positive);
        }
        if (mood.getMood().equals("Negative")){
            viewHolder.mood_img.setBackgroundResource(R.drawable.negative);
        }
        if (mood.getMood().equals("Confused")){
            viewHolder.mood_img.setBackgroundResource(R.drawable.confused);
        }
        if (mood.getMood().equals("Scared")){
            viewHolder.mood_img.setBackgroundResource(R.drawable.scared);
        }


    }

    @Override
    public int getItemCount() {
        return mMood.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView date, current_mood;
        public ImageView mood_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.mood_date);
            current_mood = itemView.findViewById(R.id.mood_mood);
            mood_img = itemView.findViewById(R.id.mood_img);
        }
    }
}

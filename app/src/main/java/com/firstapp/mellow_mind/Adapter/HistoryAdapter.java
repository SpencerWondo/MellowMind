package com.firstapp.mellow_mind.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firstapp.mellow_mind.Model.User;
import com.firstapp.mellow_mind.R;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    public Context mContext;
    public List<User> mUsers;

    private FirebaseUser firebaseUser;

    public HistoryAdapter(Context mContext, List<User> mUsers, boolean b) {
        this.mContext = mContext;
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.mood_history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mood;
        public TextView date;
        public TextView occurance;
        public TextView percentage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mood = itemView.findViewById(R.id.mood_label_content);
            date = itemView.findViewById(R.id.mood_date_content);
            occurance = itemView.findViewById(R.id.mood_num_content);
            percentage = itemView.findViewById(R.id.mood_Percent_content); 
        }
    }

}



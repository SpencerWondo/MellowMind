package com.firstapp.mellow_mind.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firstapp.mellow_mind.Guide_View_Activity;
import com.firstapp.mellow_mind.Model.Guide;
import com.firstapp.mellow_mind.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.ViewHolder>{

    public Context mContext;
    public List<Guide> mGuides;

    private FirebaseUser firebaseUser;

    public GuideAdapter(Context mContext, List<Guide> mGuides){
        this.mContext = mContext;
        this.mGuides = mGuides;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.guide_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Guide guide = mGuides.get(i);

        viewHolder.title.setText(guide.getTitle());
        viewHolder.time.setText(guide.getAuthor());
        viewHolder.views.setText(String.valueOf(guide.getViews()) + " Views");
        Glide.with(mContext).load(guide.getImageURL()).into(viewHolder.logo);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                editor.putString("guideID", guide.getGuideID());
                editor.apply();

                mContext.startActivity(new Intent(mContext, Guide_View_Activity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        viewHolder.saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.saved.getText().equals("Save")){
                    FirebaseDatabase.getInstance().getReference().child("Saved").child(firebaseUser.getUid())
                            .child(guide.getGuideID()).setValue(true);
                } else {
                    FirebaseDatabase.getInstance().getReference().child("Saved").child(firebaseUser.getUid())
                            .child(guide.getGuideID()).removeValue();
                }
            }
        });

        isSaved(guide.getGuideID(), viewHolder.saved);

    }

    @Override
    public int getItemCount() {
        return mGuides.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title, time, views;
        public Button saved;
        public ImageView logo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.guide_item_title);
            time = itemView.findViewById(R.id.guide_item_length);
            views = itemView.findViewById(R.id.guide_item_views);
            saved = itemView.findViewById(R.id.guide_item_save);
            logo = itemView.findViewById(R.id.logo);

        }
    }


    public void isSaved(final String guideID, final Button button){

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Saved").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(guideID).exists()){
                    button.setText("Saved");
                } else {
                    button.setText("Save");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}

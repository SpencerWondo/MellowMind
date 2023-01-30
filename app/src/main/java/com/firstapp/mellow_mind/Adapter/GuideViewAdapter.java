package com.firstapp.mellow_mind.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firstapp.mellow_mind.Author.AuthorActivity;
import com.firstapp.mellow_mind.Model.Guide;
import com.firstapp.mellow_mind.Model.User;
import com.firstapp.mellow_mind.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class GuideViewAdapter extends RecyclerView.Adapter<GuideViewAdapter.ViewHolder>{
    public Context mContext;
    public List<Guide> mGuides;

    private FirebaseUser firebaseUser;

    long view = 0;
    boolean alreadyExecuted;

    public GuideViewAdapter(Context mContext, List<Guide> mGuides){
        this.mContext = mContext;
        this.mGuides = mGuides;
    }

    @NonNull
    @Override
    public GuideViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.guide_view_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuideViewAdapter.ViewHolder viewHolder, int i) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Guide guide = mGuides.get(i);

        Typeface typeface = ResourcesCompat.getFont(mContext, R.font.comfortaa_light);

        //UserInfo(viewHolder.profile_pic);

        if (guide.getAuthorID() != null){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(guide.getAuthorID());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    Glide.with(mContext).load(user.getImageURL()).into(viewHolder.profile_pic);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        viewHolder.title.setTypeface(typeface);
        viewHolder.author.setTypeface(typeface);
        viewHolder.time.setTypeface(typeface);
        viewHolder.mood.setTypeface(typeface);
        viewHolder.content.setTypeface(typeface);
        viewHolder.likenum.setTypeface(typeface);
        viewHolder.saved.setTypeface(typeface);

        viewHolder.title.setText(guide.getTitle());
        viewHolder.author.setText(guide.getAuthor());
        viewHolder.time.setText(guide.getTime());
        viewHolder.mood.setText(guide.getDate());
        viewHolder.content.setText(guide.getBody());
        viewHolder.likenum.setText(String.valueOf(guide.getLikes()));

        viewHolder.author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                editor.putString("authorID", guide.getAuthorID());
                editor.apply();

                mContext.startActivity(new Intent(mContext, AuthorActivity.class)
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

        viewHolder.likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Guides").child(guide.getGuideID());

                long likes = guide.getLikes() + 1;

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("likes", likes);

                reference.updateChildren(hashMap);
            }
        });

        if (!alreadyExecuted){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Guides").child(guide.getGuideID());

            view = guide.getViews();

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("views", view + 1);
            reference.updateChildren(hashMap);

            alreadyExecuted = true; 
        }

        isSaved(guide.getGuideID(), viewHolder.saved);

    }

    @Override
    public int getItemCount() {
        return mGuides.size();
    }

    private void UserInfo(final ImageView profile_pic){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Glide.with(mContext).load(user.getImageURL()).into(profile_pic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title, time, mood, content, likenum, author;
        public ImageView likes, profile_pic;
        ScrollView mScrollview;
        public Button saved;
        public RelativeLayout content_view;
        AdView adView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.view_title);
            time = itemView.findViewById(R.id.view_time);
            mood = itemView.findViewById(R.id.view_mood);
            content = itemView.findViewById(R.id.view_content);
            saved = itemView.findViewById(R.id.view_saved);
            likenum = itemView.findViewById(R.id.like_num);
            likes = itemView.findViewById(R.id.like);
            author = itemView.findViewById(R.id.view_author);
            content_view = itemView.findViewById(R.id.content_area);
            profile_pic = itemView.findViewById(R.id.view_picture);

            content.setMovementMethod(new ScrollingMovementMethod());



            adView = itemView.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);



            title.setTranslationX(800);
            author.setTranslationX(800);
            time.setTranslationX(800);
            mood.setTranslationX(800);
            content.setTranslationX(800);
            saved.setTranslationX(800);
            likenum.setTranslationX(800);
            likes.setTranslationX(800);
            content_view.setTranslationX(800);
            profile_pic.setTranslationX(-800);

            title.setAlpha(0);
            author.setAlpha(0);
            time.setAlpha(0);
            mood.setAlpha(0);
            content.setAlpha(0);
            saved.setAlpha(0);
            likenum.setAlpha(0);
            //likes.setAlpha(0);
            content_view.setAlpha(0);
            profile_pic.setAlpha(0);

            title.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(800).start();
            author.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(700).start();
            time.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(600).start();
            mood.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(600).start();
            content.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(500).start();
            saved.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(400).start();
            likenum.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(300).start();
            likes.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(200).start();
            content_view.animate().translationX(0).alpha(1).setDuration(600).setStartDelay(200).start();
            profile_pic.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(600).start();



            //content.setMovementMethod(new ScrollingMovementMethod());

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

package com.firstapp.mellow_mind.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firstapp.mellow_mind.Admin.EditGuide;
import com.firstapp.mellow_mind.Guide_View_Activity;
import com.firstapp.mellow_mind.Model.Guide;
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

public class GuideDeleteAdpater extends RecyclerView.Adapter<GuideDeleteAdpater.ViewHolder>{

    public Context mContext;
    public List<Guide> mGuides;

    private FirebaseUser firebaseUser;

    public GuideDeleteAdpater(Context mContext, List<Guide> mGuides){
        this.mContext = mContext;
        this.mGuides = mGuides;
    }

    @NonNull
    @Override
    public GuideDeleteAdpater.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.guide_item_delete, viewGroup, false);
        return new GuideDeleteAdpater.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuideDeleteAdpater.ViewHolder viewHolder, int i) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Guide guide = mGuides.get(i);

        viewHolder.title.setText(guide.getTitle());
        viewHolder.time.setText(String.valueOf(guide.getViews()));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                editor.putString("guideID", guide.getGuideID());
                editor.apply();

//                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new GuideViewFragment()).commit();

                mContext.startActivity(new Intent(mContext, EditGuide.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("Guides")
                        .child(guide.getGuideID())
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(mContext, "Guide Successfully Deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return mGuides.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title, time;
        public ImageView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.guide_item_title);
            time = itemView.findViewById(R.id.guide_item_length);
            delete = itemView.findViewById(R.id.guide_delete);

        }
    }

}


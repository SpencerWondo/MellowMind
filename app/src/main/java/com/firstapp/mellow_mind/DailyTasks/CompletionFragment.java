package com.firstapp.mellow_mind.DailyTasks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.firstapp.mellow_mind.Adapter.GuideAdapter;
import com.firstapp.mellow_mind.Adapter.TaskAdapter;
import com.firstapp.mellow_mind.Model.Guide;
import com.firstapp.mellow_mind.Model.Task;
import com.firstapp.mellow_mind.R;
import com.firstapp.mellow_mind.Utils.SpacingItemDecorator;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompletionFragment extends Fragment {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;

    RelativeLayout empty;

    EditText search_bar;

    List<Task> mTasks;

    FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_completion, container, false);

        empty= view.findViewById(R.id.empty);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(10);
        recyclerView.addItemDecoration(itemDecorator);

        mTasks = new ArrayList<>();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        mTasks = new ArrayList<>();

        readTasks();

        search_bar = view.findViewById(R.id.searchbar);
        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchTasks(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    private void searchTasks(String s){
        Query query = FirebaseDatabase.getInstance().getReference("Tasks")
                .child(firebaseUser.getUid())
                .orderByChild("Date_num")
                .startAt(s)
                .endAt(s+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mTasks.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Task task = snapshot.getValue(Task.class);
                    mTasks.add(task);
                }

                taskAdapter = new TaskAdapter(getContext(), mTasks);
                recyclerView.setAdapter(taskAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readTasks(){
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

                taskAdapter = new TaskAdapter(getContext(), mTasks);
                recyclerView.setAdapter(taskAdapter);

                if (!mTasks.isEmpty()){
                    empty.setVisibility(View.GONE);
                }

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
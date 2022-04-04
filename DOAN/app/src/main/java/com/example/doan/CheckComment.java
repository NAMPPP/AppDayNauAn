package com.example.doan;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.adapter.CheckCommentAdapter;
import com.example.doan.common.Common;
import com.example.doan.model.Rating;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CheckComment extends AppCompatActivity {
    private RecyclerView recyclerView;
    TextView textView;
    private CheckCommentAdapter checkCommentAdapter;
    private List<Rating> listRating;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        initUi();
        getListRating();

    }
    private void initUi(){
        textView = findViewById(R.id.phone);
        textView.setText("my name is: "+Common.currentUser.getName());
        recyclerView = findViewById(R.id.rcvComment);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayout);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        listRating = new ArrayList<>();
        checkCommentAdapter = new CheckCommentAdapter(listRating);

        recyclerView.setAdapter(checkCommentAdapter);
    }

    private void getListRating(){

        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference myRef =database.getReference("Rating");

        String name= Common.currentUser.getName();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Rating rating = dataSnapshot.getValue(Rating.class);
                    if (rating.getUserName().equals(name)){
                        listRating.add(rating);
                    }
                }
                checkCommentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "null " ,Toast.LENGTH_SHORT).show();
            }
        });
    }
}

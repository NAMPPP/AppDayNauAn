package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.doan.ViewHolder.CommentAdapter;
import com.example.doan.model.Rating;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShowComment extends AppCompatActivity {
    String foodId;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference Rating_tbl;
    FirebaseRecyclerAdapter<Rating, CommentAdapter.CommentViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_comment);
        database = FirebaseDatabase.getInstance();
        Rating_tbl = database.getReference("Rating");
        recyclerView=(RecyclerView) findViewById(R.id.recycler_detail_comment);





    }
}
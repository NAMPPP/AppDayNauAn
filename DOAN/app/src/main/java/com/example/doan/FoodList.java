package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doan.Interface.ItemClickListener;
import com.example.doan.ViewHolder.FoodViewHolder;
import com.example.doan.model.Foods;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodList extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference foodList;
    String categoryId = "";
    Button btn_search;
    EditText search_Box;
    FirebaseRecyclerAdapter<Foods, FoodViewHolder> adapter;
    FirebaseRecyclerAdapter<Foods, FoodViewHolder> searchBarAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        search_Box = (EditText)findViewById(R.id.Search_box);
        btn_search=(Button) findViewById(R.id.btn_search);
        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Foods");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        categoryId = getIntent().getStringExtra("CategoryId");
        loadListFood(categoryId);
        //
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = search_Box.getText().toString();
                showSearchFood(txt);
            }
        });
    }

    private void showSearchFood(String txt) {
        Query result = foodList.orderByChild("NameFood").startAt(txt).endAt(txt+ "\uf8ff");
        searchBarAdapter = new FirebaseRecyclerAdapter<Foods, FoodViewHolder>(Foods.class,
                R.layout.fooditem,FoodViewHolder.class,result) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Foods model, int position) {
                viewHolder.food_name.setText(model.getNameFood());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.food_image);
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(FoodList.this, ""+model.getNameFood(), Toast.LENGTH_SHORT).show();
                        Intent food_detail_inIntent = new Intent(FoodList.this,FoodDetail.class);
                        food_detail_inIntent.putExtra("FoodId",adapter.getRef(position).getKey());
                        startActivity(food_detail_inIntent);
                    }
                });
            }
        };
        recyclerView.setAdapter(searchBarAdapter);

    }


    private void loadListFood(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Foods, FoodViewHolder>(Foods.class,
                R.layout.fooditem,FoodViewHolder.class,foodList.orderByChild("MenuId").equalTo(categoryId)) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Foods model, int position) {
                viewHolder.food_name.setText(model.getNameFood());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.food_image);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference Table_user = database.getReference("User");

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(FoodList.this, ""+model.getNameFood(), Toast.LENGTH_SHORT).show();
                        Intent food_detail_inIntent = new Intent(FoodList.this,FoodDetail.class);
                        food_detail_inIntent.putExtra("FoodId",adapter.getRef(position).getKey());
                        startActivity(food_detail_inIntent);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }
}
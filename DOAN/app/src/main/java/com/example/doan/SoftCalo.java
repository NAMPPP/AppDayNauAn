package com.example.doan;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.Interface.ItemClickListener;
import com.example.doan.ViewHolder.FoodViewHolder;
import com.example.doan.adapter.CaloAdapter;
import com.example.doan.model.Foods;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SoftCalo extends AppCompatActivity {

    private EditText max,min;
    Button btnLoc;
    private CaloAdapter caloAdapter;
    private List<Foods> listFood;
    private RecyclerView recyclerView;

    FirebaseDatabase database;
    DatabaseReference foodList;

    RecyclerView.LayoutManager layoutManager;
    String categoryId = "";
    Foods food;
    FirebaseRecyclerAdapter<Foods, FoodViewHolder> adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitity_calo);


        max = findViewById(R.id.max);
        min = findViewById(R.id.min);
        initUi();

        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strmin = min.getText().toString();
                String strmax = max.getText().toString();
                if (strmin.matches("") || strmax.matches("")) {
                    Toast.makeText(getApplicationContext(), "Không được để rỗng" ,Toast.LENGTH_SHORT).show();
                }else if (Integer.parseInt(strmin)>Integer.parseInt(strmax)){
                    Toast.makeText(getApplicationContext(), "Gía trị min không thể lớn hơn max !! " ,Toast.LENGTH_SHORT).show();
                }else{
                    //getListFood(Integer.parseInt(strmin),Integer.parseInt(strmax));
                    loadListFood(Float.parseFloat(strmin),Float.parseFloat(strmax));
                }
            }
        });



//        recyclerView = (RecyclerView) findViewById(R.id.rcvCalo);
//        recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);




    }
    private void initUi(){

        btnLoc = findViewById(R.id.btnLoc);
        recyclerView = findViewById(R.id.rcvCalo);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayout);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        listFood = new ArrayList<>();
        caloAdapter = new CaloAdapter(listFood);
        recyclerView.setAdapter(caloAdapter);


    }
    private void getListFood(Integer min,Integer max){

        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference myRef =database.getReference("Foods");


        Query query = myRef.orderByChild("Kcal");
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Foods food = dataSnapshot.getValue(Foods.class);
                    if (min < food.getKcal() && food.getKcal()< max)
                    {
                        listFood.add(food);
                    }

                }
                caloAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "null " ,Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadListFood(Float min,Float max) {
        database = FirebaseDatabase.getInstance();

        foodList = database.getReference("Foods");

        Query query = foodList.orderByChild("Kcal").startAt(min).endAt(max);
        adapter = new FirebaseRecyclerAdapter<Foods, FoodViewHolder>(Foods.class,
                R.layout.fooditem,FoodViewHolder.class,query) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Foods model, int position) {
                viewHolder.food_name.setText(model.getNameFood());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.food_image);

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(SoftCalo.this, ""+model.getNameFood(), Toast.LENGTH_SHORT).show();
                        Intent food_detail_inIntent = new Intent(SoftCalo.this,FoodDetail.class);
                        food_detail_inIntent.putExtra("FoodId",adapter.getRef(position).getKey());
                        startActivity(food_detail_inIntent);
                    }
                });
            }
        };
        Log.d("TAG",""+adapter.getItemCount());
        recyclerView.setAdapter(adapter);
    }



}

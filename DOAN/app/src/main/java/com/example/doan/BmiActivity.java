package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan.Interface.ItemClickListener;
import com.example.doan.ViewHolder.FoodViewHolder;
import com.example.doan.model.Foods;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class BmiActivity extends AppCompatActivity {
    String BMIResult;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference foodList;
    String categoryId = "";
    Button btn_search;
    EditText search_Box;
    TextView Bmi_cua_ban,tinh_trang_cothe;
    FirebaseRecyclerAdapter<Foods, FoodViewHolder> adapter;
    FirebaseRecyclerAdapter<Foods, FoodViewHolder> searchBarAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        search_Box = (EditText)findViewById(R.id.Search_box);
        btn_search=(Button) findViewById(R.id.btn_search);
        Bmi_cua_ban=(TextView) findViewById(R.id.bmi_cua_ban);
        tinh_trang_cothe=(TextView) findViewById(R.id.tinh_trang_cothe);
        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Foods");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_foods);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        categoryId = getIntent().getStringExtra("CategoryId");
        BMIResult = getIntent().getStringExtra("BMISend");
        Log.d("aaa",""+BMIResult);
        //
        SeachBmi(BMIResult);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = search_Box.getText().toString();
                showSearchFood(txt);
            }
        });
    }

    private void SeachBmi(String BMIResult) {
        float result = Float.parseFloat(BMIResult);
        if(result >=40)
        {
            Bmi_cua_ban.setText(String.valueOf(result));
            tinh_trang_cothe.setText("Cơ thể bạn trong tình trạng béo phì cấp độ 3");
            adapter= new FirebaseRecyclerAdapter<Foods, FoodViewHolder>(
                    Foods.class,
                    R.layout.fooditem,
                    FoodViewHolder.class,
                    FirebaseDatabase.getInstance().getReference("Foods").orderByChild("Kcal").endAt(799))
            {
                @Override
                protected void populateViewHolder(FoodViewHolder viewHolder, Foods model, int position) {
                    viewHolder.food_name.setText(model.getNameFood());
                    Picasso.with(getBaseContext()).load(model.getImage())
                            .into(viewHolder.food_image);
                    viewHolder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            Toast.makeText(BmiActivity.this, ""+model.getNameFood(), Toast.LENGTH_SHORT).show();
                            Intent food_detail_inIntent = new Intent(BmiActivity.this,FoodDetail.class);
                            food_detail_inIntent.putExtra("FoodId",adapter.getRef(position).getKey());
                            startActivity(food_detail_inIntent);
                        }
                    });
                }
            };
            recyclerView.setAdapter(adapter);
        }
        else if(result > 35.1 && result < 39.9)
        {
            Bmi_cua_ban.setText(String.valueOf(result));
            tinh_trang_cothe.setText("Cơ thể bạn trong tình trạng béo phì cấp độ 2");
            adapter= new FirebaseRecyclerAdapter<Foods, FoodViewHolder>(
                    Foods.class,
                    R.layout.fooditem,
                    FoodViewHolder.class,
                    FirebaseDatabase.getInstance().getReference("Foods").orderByChild("Kcal").endAt(1000))
            {
                @Override
                protected void populateViewHolder(FoodViewHolder viewHolder, Foods model, int position) {
                    viewHolder.food_name.setText(model.getNameFood());
                    Picasso.with(getBaseContext()).load(model.getImage())
                            .into(viewHolder.food_image);
                    viewHolder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            Toast.makeText(BmiActivity.this, ""+model.getNameFood(), Toast.LENGTH_SHORT).show();
                            Intent food_detail_inIntent = new Intent(BmiActivity.this,FoodDetail.class);
                            food_detail_inIntent.putExtra("FoodId",adapter.getRef(position).getKey());
                            startActivity(food_detail_inIntent);
                        }
                    });
                }
            };
            recyclerView.setAdapter(adapter);
        }
        else if(result > 30 && result < 35.1)
        {
            Bmi_cua_ban.setText(String.valueOf(result));
            tinh_trang_cothe.setText("Cơ thể bạn trong tình trạng béo phì cấp độ 1");
            adapter= new FirebaseRecyclerAdapter<Foods, FoodViewHolder>(
                    Foods.class,
                    R.layout.fooditem,
                    FoodViewHolder.class,
                    FirebaseDatabase.getInstance().getReference("Foods").orderByChild("Kcal").endAt(1200))
            {
                @Override
                protected void populateViewHolder(FoodViewHolder viewHolder, Foods model, int position) {
                    viewHolder.food_name.setText(model.getNameFood());
                    Picasso.with(getBaseContext()).load(model.getImage())
                            .into(viewHolder.food_image);
                    viewHolder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            Toast.makeText(BmiActivity.this, ""+model.getNameFood(), Toast.LENGTH_SHORT).show();
                            Intent food_detail_inIntent = new Intent(BmiActivity.this,FoodDetail.class);
                            food_detail_inIntent.putExtra("FoodId",adapter.getRef(position).getKey());
                            startActivity(food_detail_inIntent);
                        }
                    });
                }
            };
            recyclerView.setAdapter(adapter);
        }
        else if(result > 25 && result < 29.9)
        {
            Bmi_cua_ban.setText(String.valueOf(result));
            tinh_trang_cothe.setText("Cơ thể bạn hiện đang bình thường");
            adapter= new FirebaseRecyclerAdapter<Foods, FoodViewHolder>(
                    Foods.class,
                    R.layout.fooditem,
                    FoodViewHolder.class,
                    FirebaseDatabase.getInstance().getReference("Foods").orderByChild("Kcal").endAt(1500))
            {
                @Override
                protected void populateViewHolder(FoodViewHolder viewHolder, Foods model, int position) {
                    viewHolder.food_name.setText(model.getNameFood());
                    Picasso.with(getBaseContext()).load(model.getImage())
                            .into(viewHolder.food_image);
                    viewHolder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            Toast.makeText(BmiActivity.this, ""+model.getNameFood(), Toast.LENGTH_SHORT).show();
                            Intent food_detail_inIntent = new Intent(BmiActivity.this,FoodDetail.class);
                            food_detail_inIntent.putExtra("FoodId",adapter.getRef(position).getKey());
                            startActivity(food_detail_inIntent);
                        }
                    });
                }
            };
            recyclerView.setAdapter(adapter);
        }
        else if(result > 18 && result < 29.9)
        {
            Bmi_cua_ban.setText(String.valueOf(result));
            tinh_trang_cothe.setText("Cơ thể bạn trong tình trạng gầy cấp độ 1");
            adapter= new FirebaseRecyclerAdapter<Foods, FoodViewHolder>(
                    Foods.class,
                    R.layout.fooditem,
                    FoodViewHolder.class,
                    FirebaseDatabase.getInstance().getReference("Foods").orderByChild("Kcal").endAt(1800))
            {
                @Override
                protected void populateViewHolder(FoodViewHolder viewHolder, Foods model, int position) {
                    viewHolder.food_name.setText(model.getNameFood());
                    Picasso.with(getBaseContext()).load(model.getImage())
                            .into(viewHolder.food_image);
                    viewHolder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            Toast.makeText(BmiActivity.this, ""+model.getNameFood(), Toast.LENGTH_SHORT).show();
                            Intent food_detail_inIntent = new Intent(BmiActivity.this,FoodDetail.class);
                            food_detail_inIntent.putExtra("FoodId",adapter.getRef(position).getKey());
                            startActivity(food_detail_inIntent);
                        }
                    });
                }
            };
            recyclerView.setAdapter(adapter);
        }
        else if(result > 16 && result < 17.9)
        {
            Bmi_cua_ban.setText(String.valueOf(result));
            tinh_trang_cothe.setText("Cơ thể bạn trong tình trạng gầy cấp độ 2");
            adapter= new FirebaseRecyclerAdapter<Foods, FoodViewHolder>(
                    Foods.class,
                    R.layout.fooditem,
                    FoodViewHolder.class,
                    FirebaseDatabase.getInstance().getReference("Foods").orderByChild("Kcal").endAt(2000))
            {
                @Override
                protected void populateViewHolder(FoodViewHolder viewHolder, Foods model, int position) {
                    viewHolder.food_name.setText(model.getNameFood());
                    Picasso.with(getBaseContext()).load(model.getImage())
                            .into(viewHolder.food_image);
                    viewHolder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            Toast.makeText(BmiActivity.this, ""+model.getNameFood(), Toast.LENGTH_SHORT).show();
                            Intent food_detail_inIntent = new Intent(BmiActivity.this,FoodDetail.class);
                            food_detail_inIntent.putExtra("FoodId",adapter.getRef(position).getKey());
                            startActivity(food_detail_inIntent);
                        }
                    });
                }
            };
            recyclerView.setAdapter(adapter);
        }
        else if(result < 16)
        {
            adapter= new FirebaseRecyclerAdapter<Foods, FoodViewHolder>(
                    Foods.class,
                    R.layout.fooditem,
                    FoodViewHolder.class,
                    FirebaseDatabase.getInstance().getReference("Foods").orderByChild("Kcal").endAt(2500))
            {
                @Override
                protected void populateViewHolder(FoodViewHolder viewHolder, Foods model, int position) {
                    viewHolder.food_name.setText(model.getNameFood());
                    Picasso.with(getBaseContext()).load(model.getImage())
                            .into(viewHolder.food_image);
                    viewHolder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            Toast.makeText(BmiActivity.this, ""+model.getNameFood(), Toast.LENGTH_SHORT).show();
                            Intent food_detail_inIntent = new Intent(BmiActivity.this,FoodDetail.class);
                            food_detail_inIntent.putExtra("FoodId",adapter.getRef(position).getKey());
                            startActivity(food_detail_inIntent);
                        }
                    });
                }
            };
            recyclerView.setAdapter(adapter);
        }

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
                        Toast.makeText(BmiActivity.this, ""+model.getNameFood(), Toast.LENGTH_SHORT).show();
                        Intent food_detail_inIntent = new Intent(BmiActivity.this,FoodDetail.class);
                        food_detail_inIntent.putExtra("FoodId",adapter.getRef(position).getKey());
                        startActivity(food_detail_inIntent);
                    }
                });
            }
        };
        recyclerView.setAdapter(searchBarAdapter);
    }
}
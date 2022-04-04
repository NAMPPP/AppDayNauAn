package com.example.doan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan.ViewHolder.CommentAdapter;
import com.example.doan.common.Common;
import com.example.doan.model.Foods;
import com.example.doan.model.Rating;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class FoodDetail extends AppCompatActivity implements RatingDialogListener {

    ScrollView view;
    TextView food_name_detail,
            food_kcal_detail, food_description_detail,
            food_material_detail, food_tutorial_detail;
    ImageView food_image_Detail;
    String foodId="";
    FirebaseDatabase database;
    DatabaseReference foods,ratingTbl,Comments;
    FloatingActionButton btnRating;
    RatingBar ratingBar;
    RecyclerView recyclerView;
    ArrayList<Rating> list;
    CommentAdapter commentAdapter;
    Button btn_showComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        database= FirebaseDatabase.getInstance();
        foods = database.getReference("Foods");
        ratingTbl = database.getReference("Rating");
        btn_showComment = (Button) findViewById(R.id.btn_showComment);
        btn_showComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodDetail.this,ShowComment.class);
                intent.putExtra(Common.INTENT_FOOD_ID,foodId);
                startActivity(intent);
            }
        });
        btnRating = (FloatingActionButton) findViewById(R.id.btn_rating);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        //comment
        recyclerView = findViewById(R.id.recycler_detail_comment);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        commentAdapter = new CommentAdapter(this,list);
        recyclerView.setAdapter(commentAdapter);



        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRatingDiaLog();
            }
        });
        food_name_detail=(TextView) findViewById(R.id.food_name_detail);
        food_kcal_detail=(TextView) findViewById(R.id.food_kcal_detail);
        food_description_detail=(TextView) findViewById(R.id.food_description_detail);
        food_material_detail=(TextView) findViewById(R.id.food_Material_detail);
        food_tutorial_detail=(TextView) findViewById(R.id.food_Tutorial_detail);
        food_image_Detail=(ImageView) findViewById(R.id.food_image_detail);

        if(getIntent()!=null)
        {
            foodId = getIntent().getStringExtra("FoodId");
        }
        if(!foodId.isEmpty())
        {
            getDetailFood(foodId);
            getRatingFood(foodId);
        }
    }

    private void getRatingFood(String foodId) {
        Query foodRating = ratingTbl.orderByChild("foodId").equalTo(foodId);
        foodRating.addValueEventListener(new ValueEventListener() {
            int count = 0,sum = 0;
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for(DataSnapshot postSnapshot:datasnapshot.getChildren())
                {
                    Rating item = postSnapshot.getValue(Rating.class);
                    sum += Integer.parseInt(item.getRateVale());
                    count++;
                }
                if(count!=0)
                {
                    float average = sum/count;
                    ratingBar.setRating(average);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showRatingDiaLog(){
        new AppRatingDialog.Builder().
                setPositiveButtonText("Bình luận").
                setNegativeButtonText("Thoát").
                setNoteDescriptions(Arrays.asList("Rất tệ","Tệ","Cũng được","Ngon","Rất ngon"))
                .setDefaultRating(1)
                .setTitle("Đánh giá món ăn")
                .setDescription("Chọn mức độ và đánh giá")
                .setTitleTextColor(R.color.purple_700)
                .setDescriptionTextColor(android.R.color.system_accent1_800)
                .setHint("Đánh giá tại đây")
                .setHintTextColor(android.R.color.system_accent1_800)
                .setCommentBackgroundColor(R.color.white)
                .setWindowAnimation(R.style.RatingDiaLogFadeAnim)
                .create(FoodDetail.this)
                .show();
    }

    private void getDetailFood(String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Foods food = snapshot.getValue(Foods.class);
                Picasso.with(getBaseContext()).load(food.getImage()).into(food_image_Detail);
                food_name_detail.setText(food.getNameFood());
                food_description_detail.setText(food.getDecription());
                food_kcal_detail.setText(String.valueOf(food.getKcal()));
                food_material_detail.setText(food.getMaterial());
                food_tutorial_detail.setText(food.getTutorial());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query query = ratingTbl.orderByChild("foodId").equalTo(foodId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    Rating rating = dataSnapshot.getValue(Rating.class);
                    list.add(rating);
                }
                commentAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onPositiveButtonClicked(int value, @NonNull String comments) {
        Random random = new Random();
        int r = random.nextInt(1000000000);
        String key = Integer.toString(r);
        String valueFinish = Integer.toString(value);
        Rating rating = new Rating(Common.currentUser.getName(),foodId,String.valueOf(value),comments);
        ratingTbl.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                    ratingTbl.child(key).setValue(rating);
                Toast.makeText(FoodDetail.this,"Cảm ơn đánh giá của bạn",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
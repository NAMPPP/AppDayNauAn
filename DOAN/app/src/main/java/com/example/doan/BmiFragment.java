package com.example.doan;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class BmiFragment extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference foodList;
    EditText Edt_cannang,Edt_chieucao;
    Button Btn_search_bmi, Btn_exit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_bmi);
        Edt_cannang = (EditText) findViewById(R.id.edt_cannang);
        Edt_chieucao = (EditText) findViewById(R.id.edt_chieucao);
        Btn_search_bmi = (Button) findViewById(R.id.btn_search_bmi);
        Btn_exit=(Button) findViewById(R.id.btn_exit);
        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Foods");
        Btn_search_bmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String height = Edt_chieucao.getText().toString();
                String weight = Edt_cannang.getText().toString();
                float chieucao =Float.parseFloat(height);
                float cannang = Float.parseFloat(weight);
                float resultBmi = ((cannang/(chieucao*chieucao))*10000);
                String result = String.valueOf(resultBmi).trim();
                Log.d("aaa",""+resultBmi);
                Intent intent = new Intent(BmiFragment.this,BmiActivity.class);
                intent.putExtra("BMISend",result);
                startActivity(intent);
            }
        });
    }

}
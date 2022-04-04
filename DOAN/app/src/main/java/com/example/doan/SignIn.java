package com.example.doan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doan.common.Common;
import com.example.doan.model.User;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {
    EditText edtPhoneUser, edtPassWord;
    Button btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPassWord=(EditText) findViewById(R.id.edtPassword);
        edtPhoneUser=(EditText) findViewById(R.id.edtPhone);
        btnSignIn=(Button) findViewById(R.id.btnSignindangnhap);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                mDialog.setMessage("Vui lòng đợi một xíu!!:D");
                mDialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(edtPhoneUser.getText().toString()).exists()){
                            mDialog.dismiss();
                            User user = snapshot.child(edtPhoneUser.getText().toString()).getValue(User.class);
                            if(user.getPassword().equals(edtPassWord.getText().toString()))
                            {
                                Toast.makeText(SignIn.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                                Intent homIntent = new Intent(SignIn.this,Home.class);
                                Common.currentUser=user;
                                startActivity(homIntent);
                                finish();

                            }
                            else {
                                Toast.makeText(SignIn.this,"Đăng nhập thất bại",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            mDialog.dismiss();
                            Toast.makeText(SignIn.this,"Người dùng không tồn tại",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


    }
}
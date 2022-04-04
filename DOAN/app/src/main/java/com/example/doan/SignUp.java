package com.example.doan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doan.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {
    EditText edtUserName, edtPassWordUser, edtPhoneUser;
    Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtPassWordUser = (EditText) findViewById(R.id.edtPassword);
        edtPhoneUser = (EditText) findViewById(R.id.edtPhone);

        btnSignUp = (Button) findViewById(R.id.btnSignupDangKy);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Vui lòng để một xiu!!:D");
                mDialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(edtPhoneUser.getText().toString()).exists()){
                            Toast.makeText(SignUp.this,"Số điện thoại này đã được đăng ký",Toast.LENGTH_SHORT).show();
                        }
                        else
                            {
                            mDialog.dismiss();
                            User user= new User(edtUserName.getText().toString(),edtPassWordUser.getText().toString());
                            table_user.child(edtPhoneUser.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this,"Đăng ký thành công!!",Toast.LENGTH_SHORT).show();
                            finish();
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
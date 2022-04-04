package com.example.doan;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.common.Common;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;

public class User extends AppCompatActivity {
    EditText editfullname, edtPassWord ;
    TextView editfield,edtPhoneUser;
    Button btnUPDATE;

    //    public View onCreateView(@NonNull LayoutInflater inflater,@NonNull ViewGroup container,@NonNull Bundle saveIntancesState){
//        mView = inflater.inflate(R.layout.activity_user,container,false);
//        initUi();
//        setUserInformation();
//        return mView;
//    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);


        initUi();
        setUserInformation();
        btnUPDATE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLogConfirm();
            }
        });
    }
    private void initUi(){

        editfullname = findViewById(R.id.full_name);
        edtPhoneUser = findViewById(R.id.phone_number);
        edtPassWord = findViewById(R.id.pass);
        editfield = findViewById(R.id.fullname_field);
        btnUPDATE = findViewById(R.id.UPDATE);

    }
    private void setUserInformation() {
        editfullname.setText(Common.currentUser.getName());
        edtPassWord.setText(Common.currentUser.getPassword());
        editfield.setText(Common.currentUser.getName());
    }
    private void update(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference table_user = database.getReference("User/"+edtPhoneUser.getText().toString()+"/name");
        table_user.setValue(editfullname.getText().toString().trim());
        DatabaseReference table_user1 = database.getReference("User/"+edtPhoneUser.getText().toString()+"/password");
        table_user1.setValue(edtPassWord.getText().toString().trim());
        Intent Intent = new Intent(User.this,MainActivity2.class);
        startActivity(Intent);
    }
    public void showDiaLogConfirm(){

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog);
        Button btnyes = dialog.findViewById(R.id.btnYes);
        Button btnno = dialog.findViewById(R.id.btnNo);
        dialog.show();
        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();

            }
        });
        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

    }






}

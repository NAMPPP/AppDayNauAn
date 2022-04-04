package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {

    Button btnButtonSignIn, btnButtonSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btnButtonSignIn=(Button) findViewById(R.id.btnSignin);
        btnButtonSignUp=(Button) findViewById(R.id.btnSignUp);

        btnButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSignUp = new Intent(MainActivity2.this,SignUp.class);
                startActivity(intentSignUp);

            }
        });
        btnButtonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSignIn = new Intent(MainActivity2.this,SignIn.class);
                startActivity(intentSignIn);
            }
        });

    }
}
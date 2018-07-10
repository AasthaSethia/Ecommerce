package com.example.aashtha.e_commerce;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Crossfire extends AppCompatActivity {
    Button button2;
    Button button1;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crossfire);
        button2 = (Button) findViewById(R.id.cart);
        button1 = (Button) findViewById(R.id.buy);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(Crossfire.this, Cart.class);
                startActivity(intent2);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(Crossfire.this, Buy.class);
                startActivity(intent2);
            }
        });
    }
}


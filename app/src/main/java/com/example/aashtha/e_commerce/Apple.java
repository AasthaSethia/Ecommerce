package com.example.aashtha.e_commerce;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Apple extends AppCompatActivity {
    Button cart;
    Button buy;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apple);
        cart = (Button) findViewById(R.id.cart);
        buy = (Button) findViewById(R.id.buy);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cart = new Intent(Apple.this, Cart.class);
                startActivity(cart);
            }
        });
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent buy = new Intent(Apple.this, Buy.class);
                startActivity(buy);
            }
        });
    }
}


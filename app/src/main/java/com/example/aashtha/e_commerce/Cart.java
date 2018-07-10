package com.example.aashtha.e_commerce;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Cart extends AppCompatActivity {
    String thank;
    Context context;
    TextView txt;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        txt=(TextView) findViewById(R.id.cart);
        thank=context.getResources().getString(R.string.Thank);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Cart.this,thank,Toast.LENGTH_SHORT).show();
            }
        });

    }

}
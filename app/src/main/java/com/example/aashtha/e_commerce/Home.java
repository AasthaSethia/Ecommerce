package com.example.aashtha.e_commerce;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Home extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    //a list to store all the products
    List<product> productList;
    int n;
    //the recyclerview
    RecyclerView recyclerView;
    UserSessionManager session;
    GoogleApiClient googleApiClient;
    GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

    TextView txt;
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        txt= (TextView)findViewById(R.id.noprod);
        EditText editText = (EditText)findViewById(R.id.thesearchFilter);
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,options).build();
        //getting the recyclerview from xml

        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //initializing the productlist
        productList = new ArrayList<>();
        product apple = new product(getResources().getString(R.string.a11),getResources().getString(R.string.a1), R.drawable.image2);
        product oneplus = new product(getResources().getString(R.string.a12),getResources().getString(R.string.a7), R.drawable.image3);
        product jbl = new product(getResources().getString(R.string.a15),getResources().getString(R.string.a2), R.drawable.image6);
        product sony = new product(getResources().getString(R.string.a16),getResources().getString(R.string.a8), R.drawable.image7);
        product skullc = new product(getResources().getString(R.string.a13),getResources().getString(R.string.a5), R.drawable.image5);
        product skullc1 = new product(getResources().getString(R.string.a14),getResources().getString(R.string.a6), R.drawable.image4);
        product crossfire = new product(getResources().getString(R.string.a17),getResources().getString(R.string.a11),R.drawable.image8);
        product harrypotter = new product(getResources().getString(R.string.a18),getResources().getString(R.string.a9), R.drawable.image9);
        product fossils = new product(getResources().getString(R.string.a19),getResources().getString(R.string.a10),R.drawable.image10 );
        product baggit = new product(getResources().getString(R.string.a20),getResources().getString(R.string.a4), R.drawable.image11);
        productList.add(apple);
        productList.add(oneplus);
        productList.add(jbl);
        productList.add(sony);
        productList.add(skullc);
        productList.add(skullc1);
        productList.add(crossfire);
        productList.add(harrypotter);
        productList.add(fossils);
        productList.add(baggit);
        final ProductAdapter adapter = new ProductAdapter(this, productList);
        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
        n=adapter.getItemCount();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                txt.setVisibility(View.VISIBLE);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txt.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //filter(s.toString());
                ArrayList<product> filteredList = new ArrayList<>();

                for (product item : productList) {
                    if (item.getname().toLowerCase().contains(s.toString().toLowerCase())) {
                        txt.setVisibility(View.GONE);
                        filteredList.add(item);

                    }
                    else{
                       // Toast.makeText(getApplicationContext(),"No Product Found", Toast.LENGTH_LONG).show();
                        txt.setVisibility(View.VISIBLE);
                        }

                }

                adapter.filterList(filteredList);
            }

        });
        session = new UserSessionManager(getApplicationContext());

    }

   @Override
    protected void onResume() {

       super.onResume();
       Toast.makeText(getApplicationContext(),
               "User Login Status: " + session.isUserLoggedIn(),
               Toast.LENGTH_LONG).show();
        if(session.checkLogin()) {
            finish();
        }
       // get user data from session
       HashMap<String, String> user = session.getUserDetails();

       // get password
       String email = user.get(UserSessionManager.KEY_EMAIL);

       // get password
       String password = user.get(UserSessionManager.KEY_PASSWORD);

   }

 @Override
    protected void onPause() {

     super.onPause();
 }
   @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
  }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_logout: session.logoutUser();
                                     signout();
                                     break;
             default: Toast.makeText(this,"Not Selected",
                     Toast.LENGTH_LONG);


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signout()
    {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {

            }
        });
    }
}
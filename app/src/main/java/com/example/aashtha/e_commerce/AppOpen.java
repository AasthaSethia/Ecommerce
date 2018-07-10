package com.example.aashtha.e_commerce;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class AppOpen extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener  {


    EditText editEmail, editPassword, editName, editOtp;
    Button btnSignIn, btnRegister,btnforgot;
    SignInButton google;
    public GoogleApiClient googleApiClient;
    UserSessionManager session;
    String avail_accounts;
    SharedPreferences pref;
    Context context=this;
    static final int REQ_CV= 9001;
    String Email;
    String ver="";

    String URL= "http://192.168.74.1/index.php";

    JSONParser jsonParser=new JSONParser();

    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appopen);
        pref=getSharedPreferences(getResources().getString(R.string.apppref),MODE_PRIVATE);
        session = new UserSessionManager(getApplicationContext());
        editEmail = (EditText) findViewById(R.id.editEmail);
        editName = (EditText) findViewById(R.id.editName);
        editPassword = (EditText) findViewById(R.id.editPassword);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnforgot=(Button)findViewById(R.id.Forgot);
        google = (SignInButton) findViewById(R.id.glog);
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,options).build();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* avail_accounts ="shivangikakkar15@gmail.com";
                SharedPreferences.Editor edit = pref.edit();
                //Storing Data using SharedPreferences
                edit.putString(getResources().getString(R.string.email),avail_accounts);
                edit.commit();
                new URLShort().execute();
                new Authenticate().execute();*/
                    AttemptLogin attemptLogin = new AttemptLogin();
                    attemptLogin.execute(editName.getText().toString(), editPassword.getText().toString(), "");


            }
        });
        btnforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Check for on click"," worked");
                Intent intent = new Intent(AppOpen.this, ForgotPassword.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (i == 0) {
                    i = 1;
                    editEmail.setVisibility(View.VISIBLE);
                    btnSignIn.setVisibility(View.GONE);
                    btnforgot.setVisibility(View.GONE);
                    btnRegister.setText("CREATE ACCOUNT");
                } else {

                    btnRegister.setText("REGISTER");
                    editEmail.setVisibility(View.GONE);
                    btnSignIn.setVisibility(View.VISIBLE);
                    btnforgot.setVisibility(View.VISIBLE);
                    Email= editEmail.toString();
                    i = 0;
                    AttemptLogin attemptLogin = new AttemptLogin();
                    attemptLogin.execute(editName.getText().toString(), editPassword.getText().toString(), editEmail.getText().toString());

                }

            }
        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
                new URLShort().execute();
            }


        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private void signIn(){
        session.createUserLoginSession(getResources().getString(R.string.mail),getResources().getString(R.string.pass));
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CV);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CV) {
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                avail_accounts = acct.getEmail();
            }
            SharedPreferences.Editor edit = pref.edit();
            //Storing Data using SharedPreferences
            edit.putString(getResources().getString(R.string.email), avail_accounts);
            edit.commit();
            new Authenticate().execute();
            GoogleSignInResult result= Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleresult(result);
        }
    }
    private void handleresult(GoogleSignInResult result){
        if(result.isSuccess()){
            Intent intent=new Intent(AppOpen.this,Home.class);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.WC),
                    Toast.LENGTH_LONG).show();
        }
    }

    private class AttemptLogin extends AsyncTask<String, String, JSONObject> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected JSONObject doInBackground(String... args) {



            String email = args[2];
            String password = args[1];
            String name= args[0];

            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("username", name));
            params.add(new BasicNameValuePair("password", password));
            if(email.length()>0)
                params.add(new BasicNameValuePair("email",email));

            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);


            return json;

        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            String res = null;
            try {
                if (result != null) {
                    Toast.makeText(getApplicationContext(),result.getString("message"),Toast.LENGTH_LONG).show();
                    res=result.getString("message");
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(res.equals("Successfully logged in")) {

                session.createUserLoginSession(editName.toString(),editPassword.toString());
                Intent intent=new Intent(context,Home.class);
                context.startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(), "Cannot move to next page ", Toast.LENGTH_LONG).show();
            }


        }

    }
    class Authenticate extends AsyncTask<String, String, String> {
        ProgressDialog pDialog;
        String mEmail;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AppOpen.this);
            pDialog.setMessage(getResources().getString(R.string.Authen));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            mEmail= pref.getString(getResources().getString(R.string.email), "");
            pDialog.show();
        }
        @Override
        protected void onPostExecute(String token) {
            pDialog.dismiss();
            if(token != null){

                SharedPreferences.Editor edit = pref.edit();
                //Storing Access Token using SharedPreferences
                edit.putString(getResources().getString(R.string.accesses),token);
                edit.commit();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.access)+token, Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            String token = null;

            try {
                token = GoogleAuthUtil.getToken(
                        AppOpen.this,
                        avail_accounts,
                        getResources().getString(R.string.oauth));
            } catch (IOException transientEx) {
                // Network or server error, try later
            } catch (UserRecoverableAuthException e) {
                // Recover (with e.getIntent())
                startActivityForResult(e.getIntent(), 1001);


            } catch (GoogleAuthException authEx) {
            }

            return token;
        }

    };
    private class URLShort extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        String Token,LongUrl;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AppOpen.this);
            pDialog.setMessage(getResources().getString(R.string.contact));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            Token = pref.getString(getResources().getString(R.string.accesses), "");
            LongUrl = "128.199.224.11";
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            URLShortener jParser = new URLShortener();
            JSONObject json = jParser.getJSONFromUrl(getResources().getString(R.string.address) + Token,LongUrl);
            return json;
        }
        String shortUrl;
        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                if (json != null){
                    shortUrl = json.getString(getResources().getString(R.string.id));
                    Toast.makeText(getApplicationContext(), shortUrl, Toast.LENGTH_LONG).show();
                    pDialog.dismiss();
                }else{

                    pDialog.dismiss();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
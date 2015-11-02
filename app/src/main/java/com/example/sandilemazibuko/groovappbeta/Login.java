package com.example.sandilemazibuko.groovappbeta;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Login extends AppCompatActivity {



    Button login;
    EditText txtemail,txtpassword;


    ImageView img;
    Bitmap bitmap;
    //ProgressDialog pDialog;

    // Progress Dialog
    private ProgressDialog pDialog;

    LocalStorage userDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userDatabase = new LocalStorage(this);

        txtemail = (EditText) findViewById(R.id.txtemail);
        txtpassword = (EditText) findViewById(R.id.txtpassword);

        login = (Button) findViewById(R.id.btnLoginUser);


        img = (ImageView)findViewById(R.id.imgImage);

        //new LoadImage().execute("https://m1.behance.net/rendition/modules/83055723/disp/729ebe0c689023040b7eb03fe48e7792.png");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtemail.getText().toString();
                String password = txtpassword.getText().toString();

                if(!email.equals("")){
                    if(!password.equals("")){
                        if(isOnline()){
                            String[] myTaskParams = {email,password};

                            new LoginTask().execute(myTaskParams);

                        }else{
                            Toast.makeText(Login.this,"Your Device is not Connected",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(Login.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Login.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                }
            }
        });


        TextView txtAccount  = (TextView) findViewById(R.id.txtAccount);
        txtAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        TextView txtResetPassword  = (TextView) findViewById(R.id.txtResetPassword);
        txtResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ForgotPassword.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    private class LoginTask extends AsyncTask<String, String, JSONObject> {

        /**
         * THESE FUNCTION PROCESS INFORMATION FROM FIRST TIME BEFORE RUNNING THE DO BACKGROUND TASK
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("User Login Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();

            String url = "http://groovapp.codist.co.za/get_user.php";


            RequestBody formBody = new FormEncodingBuilder()
                    .add("email", params[0])
                    .add("password", params[1])
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            Response response = null;
            JSONObject Jobject = null;
            try {
                response = client.newCall(request).execute();
                String StringRespons = response.body().string();
                Jobject = new JSONObject(StringRespons);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return Jobject;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();

            try {
                if(result.getString("success").equals("1")){

                    JSONArray Jarray = null;
                    try {
                        Jarray = result.getJSONArray("user");
                        JSONObject object = Jarray.getJSONObject(0);

                        String status  = object.getString("status");

                        if(!status.equals("0")){

                            String user_id = object.getString("user_id");
                            String name = object.getString("user_first_name");
                            String surname = object.getString("user_surname");
                            String email = object.getString("user_email");
                            String user_dob = object.getString("user_dob");
                            String user_membership_type = object.getString("user_membership_type");
                            String profile_pic = object.getString("user_picture");
                            String password = object.getString("user_password");
                            String cellphone = object.getString("user_cell_phone");

                            //String date_registered = object.getString("user_registered");
                            //String date_modified = object.getString("user_registered");

                            User newUser = new User(user_id,name,surname,
                                    profile_pic,password,cellphone,email,user_dob,user_membership_type,"1");
                            userDatabase.storeUserDetailsOnPreference(newUser);

                            Intent intent = new Intent(Login.this, Profile.class);
                            startActivity(intent);

                        }else{
                            Toast.makeText(Login.this, "Please Activate your Account", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    Toast.makeText(Login.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Loading Image ....");
            pDialog.show();

        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){
                img.setImageBitmap(image);
                pDialog.dismiss();

            }else{

                pDialog.dismiss();
                Toast.makeText(Login.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }


}

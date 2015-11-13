package com.example.sandilemazibuko.groovappbeta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class CreditCard extends AppCompatActivity {


    EditText txtPAN,txtCVV,txtExpYear;
    TextView display_user_id,disUserNames;
    Button btnCreditCard;

    LocalStorage userDatabase;

    // Progress Dialog
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);

        /**
         * INITIALIZING LOCAL STORE DB
         */
        userDatabase = new LocalStorage(this);

        String current_user_id = "";
        String current_full_names = "";

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            current_user_id = extras.getString("current_user_id");
            current_full_names = extras.getString("current_full_names");
        }



        int curr_user_id = Integer.parseInt(current_user_id);

        String user_id_log_in =  String.valueOf(curr_user_id);

        String full_names = current_full_names;
        display_user_id = (TextView) findViewById(R.id.disUserid);
        disUserNames = (TextView) findViewById(R.id.disUserName);

        String user_id_details = "User id : " + user_id_log_in;
        String user_full_names_details = "User Card Holder : " + full_names;
        display_user_id.setText(user_id_details);
        disUserNames.setText(user_full_names_details);

        txtPAN = (EditText) findViewById(R.id.txtPAN);
        txtCVV = (EditText) findViewById(R.id.txtCVV);
        txtExpYear = (EditText) findViewById(R.id.txtExpYear);

        btnCreditCard = (Button) findViewById(R.id.btnCreditCard);
        btnCreditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pan = txtPAN.getText().toString();
                String cvv = txtCVV.getText().toString();
                String exYear = txtExpYear.getText().toString();

                if(!pan.equals("")){
                    if(!cvv.equals("")){
                        if(!exYear.equals("")){

                            String current_user_id = "";
                            String current_full_names = "";

                            Bundle extras = getIntent().getExtras();
                            if (extras != null) {
                                current_user_id = extras.getString("current_user_id");
                                current_full_names = extras.getString("current_full_names");
                            }


                            userDatabase = new LocalStorage(CreditCard.this);
                            String user_id_log_in = current_user_id;
                            String full_names = current_full_names;

                            String[] myTaskParams = {user_id_log_in,full_names,pan,cvv,exYear};
                            new AddCreditCard().execute(myTaskParams);

                            //Intent intent = new Intent(CreditCard.this, Profile.class);
                            //startActivity(intent);
                        }else{
                            Toast.makeText(CreditCard.this, "Please Enter Expiry Year", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(CreditCard.this, "Please Enter Cvv", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CreditCard.this, "Please Enter Pan", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private class AddCreditCard extends AsyncTask<String, String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CreditCard.this);
            pDialog.setMessage("Adding Credit Card Information Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = "http://groovapp.codist.co.za/creditcard.php";

            String user_id = params[0];
            String card_holder_name = params[1];
            String pan = params[2];
            String cvv = params[3];
            String year = params[4];

            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormEncodingBuilder()
                    .add("user_id", user_id)
                    .add("pan", pan)
                    .add("cvv", cvv)
                    .add("year", year)
                    .add("holder_name", card_holder_name)
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


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
        }
    }
}

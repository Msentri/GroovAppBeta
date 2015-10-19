package com.example.sandilemazibuko.groovappbeta;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Register extends AppCompatActivity {

    Spinner spMonth,spDay,spType;
    EditText txtname,txtsurname,txtemail,txtpassword,txtyear,txtcellphone;
    Button btnGoToRegister;

    // Progress Dialog
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /**
         * DATE OF BIRTH AREA.....
         */
        spMonth = (Spinner) findViewById(R.id.spMonths);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.month,android.R.layout.simple_spinner_item);
        spMonth.setAdapter(adapter);

        spDay = (Spinner) findViewById(R.id.spDay);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.day,android.R.layout.simple_spinner_item);
        spDay.setAdapter(adapter1);

        /**
         * DATE OF BIRTH AREA.....
         */
        spType = (Spinner) findViewById(R.id.spType);
        ArrayAdapter adapterSP_type = ArrayAdapter.createFromResource(this, R.array.type,android.R.layout.simple_spinner_item);
        spType.setAdapter(adapterSP_type);

        txtname = (EditText) findViewById(R.id.txtname);
        txtsurname = (EditText) findViewById(R.id.txtsurname);
        txtemail = (EditText) findViewById(R.id.txtemail);
        txtpassword = (EditText) findViewById(R.id.txtpassword);
        txtyear = (EditText) findViewById(R.id.txtYear);
        txtcellphone = (EditText) findViewById(R.id.txtcellphone);

        btnGoToRegister = (Button) findViewById(R.id.btnReg);

        btnGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = txtname.getText().toString();
                String surname = txtsurname.getText().toString();
                String email = txtemail.getText().toString();
                String password = txtpassword.getText().toString();
                String year = txtyear.getText().toString();
                String cellphone = txtcellphone.getText().toString();

                if(!name.equals("")){
                    if(!surname.equals("")){
                        if(!email.equals("")){
                            if(!password.equals("")){
                                if(!year.equals("")){
                                    if(!spMonth.getSelectedItem().toString().equals("Month")) {
                                        if(!spDay.getSelectedItem().toString().equals("Day")) {
                                                if(!spType.getSelectedItem().toString().equals("None")) {
                                                    if(!cellphone.equals("")){
                                                        if(isOnline()){
                                                            new RegisterTask().execute();

                                                            String[] myTaskParams = {name,surname,email};

                                                            new EmailConfirm().execute(myTaskParams);
                                                        }else{
                                                            Toast.makeText(Register.this, "Network isn't available", Toast.LENGTH_LONG).show();
                                                        }

                                                    }else{
                                                        Toast.makeText(Register.this, "Please Enter Cellphone Number", Toast.LENGTH_SHORT).show();
                                                    }
                                                }else{
                                                    Toast.makeText(Register.this, "Please Select Membership Type", Toast.LENGTH_LONG).show();
                                                }
                                        }else {
                                            Toast.makeText(Register.this, "Please Select Day you were born at.", Toast.LENGTH_LONG).show();
                                        }
                                    }else{
                                        Toast.makeText(Register.this, "Please Select Month you were born at.", Toast.LENGTH_LONG).show();
                                    }

                                }else{
                                    Toast.makeText(Register.this, "Please Enter Year you were born at", Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                Toast.makeText(Register.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(Register.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(Register.this, "Please Enter Surname", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(Register.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private class EmailConfirm extends AsyncTask<String, String, Void>{

        @Override
        protected Void doInBackground(String... params) {

            String url = "http://groovapp.msentri.co.za/email.php";


            String name = params[0];
            String surname = params[1];
            String email = params[2];

            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormEncodingBuilder()
                    .add("name", name)
                    .add("surname", surname)
                    .add("email", email)
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
    }

    /**
     * INNER CLASS PROCESS INFORMATION OF REGISTERING USER BACKGROUND
     */
    private class RegisterTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Register.this);
            pDialog.setMessage("Registering New User Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {



            String obj = Create_user();
            String test = null;

            try {
                JSONObject Jobject = new JSONObject(obj);
                test = Jobject.getString("message");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            //Log.v("--------------------------------------- ", obj);
            return test;
        }

        @Override
        protected void onPostExecute(String result) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();

            if(result != null){
                if(result.equals("Email Address taken already")){
                    Toast.makeText(Register.this, result, Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent(Register.this, CreditCard.class);
                    startActivity(intent);
                }
                //Toast.makeText(Register.this, result, Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(Register.this, result, Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * FUNCTION CHECK IF THE IS CONNECTIVITY ONLINE
     * @return
     */
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * FUNCTION CREATE USER AND SUBMIT INFORMATION BACKGROUND
     * @return
     */
    public String Create_user(){

        txtname = (EditText) findViewById(R.id.txtname);
        txtsurname = (EditText) findViewById(R.id.txtsurname);
        txtemail = (EditText) findViewById(R.id.txtemail);

        txtpassword = (EditText) findViewById(R.id.txtpassword);
        txtcellphone = (EditText) findViewById(R.id.txtcellphone);

        txtyear = (EditText) findViewById(R.id.txtYear);

        String name = txtname.getText().toString();
        String surname = txtsurname.getText().toString();
        String email = txtemail.getText().toString();
        String password  = txtpassword.getText().toString();
        String cellphone = txtcellphone.getText().toString();
        String year = txtyear.getText().toString();



        /**
         * DATE OF BIRTH AREA.....
         */
        String dob = spDay.getSelectedItem().toString() + "-" + spMonth.getSelectedItem().toString() + "-" + year;
        /**
         * DATE OF BIRTH AREA.....
         */


        OkHttpClient client = new OkHttpClient();

        String url = "http://groovapp.codist.co.za/Register.php";

        String obj = "";

        String type = "";

        String spTypes = spType.getSelectedItem().toString();

        if(spTypes.equals("Black Opening")){
            type = "1";
        }else if(spTypes.equals("Gold")){
            type = "2";
        }else if(spTypes.equals("Platinum")){
            type = "3";
        }

        RequestBody formBody = new FormEncodingBuilder()
                .add("name", name)
                .add("surname", surname)
                .add("password", password)
                .add("cell", cellphone)
                .add("email", email)
                .add("dob", dob)
                .add("member_type", type)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Response response = null;
        try {

            response = client.newCall(request).execute();
            obj= response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return obj;
    }
}

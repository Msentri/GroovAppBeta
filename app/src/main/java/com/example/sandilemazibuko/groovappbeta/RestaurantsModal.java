package com.example.sandilemazibuko.groovappbeta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
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
import java.util.concurrent.ExecutionException;

public class RestaurantsModal extends AppCompatActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    ImageView res_image;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_restaurants_modal);

        DisplayMetrics dm =  new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .9), (int) (height * .7));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            String restaurant_name = extras.getString("Place");
            String RES_NUMBER = extras.getString("RES_ID_NUMBER");
            String RES_IMAGE_URL = "";



            JSONArray Jarray = null;

            String restaurant_provinces =  "";
            String introContentRes = "";

            try {
                String[] myTaskParams = {RES_NUMBER};

                JSONObject PlaceDetails =  new RequestRestaurantsDetails().execute(myTaskParams).get();

                Jarray = PlaceDetails.getJSONArray("places");
                JSONObject object = Jarray.getJSONObject(0);

                restaurant_provinces = object.getString("street");
                introContentRes = object.getString("intro");
                RES_IMAGE_URL = object.getString("image");

                //Toast.makeText(RestaurantsModal.this, restaurant_provinces, Toast.LENGTH_SHORT).show();


            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            res_image = (ImageView)findViewById(R.id.res_image);

            new LoadImage().execute(RES_IMAGE_URL);




            TextView txtCity = (TextView)findViewById(R.id.txtCity);
            txtCity.setText(restaurant_provinces);

            TextView txtPlace = (TextView)findViewById(R.id.txtPlace);
            txtPlace.setText(restaurant_name);

            TextView introContent = (TextView)findViewById(R.id.introContent);
            introContent.setText(introContentRes);


        }

        ImageView imageClose = (ImageView)findViewById(R.id.imageClose);
        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(RestaurantsModal.this, "", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        Button attendVanue = (Button)findViewById(R.id.buttonAttend);
        attendVanue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RestaurantsModal.this, "Attend", Toast.LENGTH_SHORT).show();
            }
        });




    }

    private class RequestRestaurantsDetails extends AsyncTask<String, String, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pDialog = new ProgressDialog(RestaurantsModal.this);
//            pDialog.setMessage("Please wait loading Restaurants Details...");
//            pDialog.show();
        }
        @Override
        protected JSONObject doInBackground(String... params) {

            OkHttpClient client = new OkHttpClient();

            String url = "http://groovapp.codist.co.za/get_res_by_id.php";

            RequestBody formBody = new FormEncodingBuilder()
                    .add("id", params[0])
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
            pDialog.dismiss();
        }
    }

    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RestaurantsModal.this);
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
                res_image.setImageBitmap(image);
                pDialog.dismiss();

            }else{

                pDialog.dismiss();
                Toast.makeText(RestaurantsModal.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }
}

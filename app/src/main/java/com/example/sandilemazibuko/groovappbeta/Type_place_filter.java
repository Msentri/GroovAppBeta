package com.example.sandilemazibuko.groovappbeta;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.widget.ListView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Type_place_filter extends AppCompatActivity {

    ListView lv;
    Context context;

    String[] prgmName;
    public static int [] prgmImages = {R.drawable.account_pic,R.drawable.map,R.drawable.map,
            R.drawable.account_pic, R.drawable.account_pic,
            R.drawable.account_pic,R.drawable.account_pic,
            R.drawable.account_pic,R.drawable.account_pic};
    public static String [] prgmNameList={"Let Us C","c++","JAVA","Jsp","Microsoft .Net","Android","PHP","Jquery","JavaScript"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_place_filter);

        DisplayMetrics dm =  new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .9), (int) (height * .4));

        //getWindow().setLayout((int) (width * .9), (int) (height * .7));

        try {
            List<String> place_type_results = new RequestPlaceTypes().execute().get();
            List<String> prgmName = place_type_results;
            context=this;

            lv=(ListView) findViewById(R.id.listView);
            lv.setAdapter(new CustomAdapter(Type_place_filter.this,prgmName , prgmImages));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }



    /**
     * ********************************************************************************************************
     */
    private class RequestPlaceTypes extends AsyncTask<String, String, List<String>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //pDialog = new ProgressDialog(this);
            //pDialog.setMessage("Please wait loading Place Types");
            //pDialog.show();
        }


        @Override
        protected List<String> doInBackground(String... params) {

            String url = "http://groovapp.codist.co.za/get_all_place_type.php";

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = null;
            JSONObject Jobject = null;
            JSONArray Jarray = null;

            List<String> place_type_results = new ArrayList<>();

            try {
                response = client.newCall(request).execute();
                String StringRespons = response.body().string();
                Jobject = new JSONObject(StringRespons);

                Jarray = Jobject.getJSONArray("place_type");

                for(int x = 0; x < Jarray.length();x++){
                    JSONObject object = Jarray.getJSONObject(x);

                    String place_type_id = object.getString("id");
                    String place_type_name = object.getString("name");
                    String place_type_date_added = object.getString("added");
                    String place_type_date_modified = object.getString("modified");


                    String all = place_type_id + "*" + place_type_name + "#" + place_type_date_added + "@" + place_type_date_modified;

                    // place_type_results.add(place_type_id);
                    place_type_results.add(all);
                    //place_type_results.add(place_type_date_added);
                    //place_type_results.add(place_type_date_modified);

                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return place_type_results;

        }
        @Override
        protected void onPostExecute(List<String> result) {
            //pDialog.dismiss();
        }
    }

    /**
     * ********************************************************************************************************
     */
}

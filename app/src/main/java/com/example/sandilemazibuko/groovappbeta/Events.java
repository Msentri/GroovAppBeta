package com.example.sandilemazibuko.groovappbeta;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Events extends AppCompatActivity {

    ArrayList all_events;
    ArrayList image_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        DisplayMetrics dm =  new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .9), (int) (height * .7));

        image_url = new ArrayList<String>();

        try {
            image_url = new RequestAllEvents().execute().get();

                final ListView listView = (ListView) findViewById(R.id.event_list);
                listView.setAdapter(new CustomListAdapter(this, image_url));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                        String newsData = (String) listView.getItemAtPosition(position);

                        String place_id = newsData.substring(0,newsData.indexOf("@"));
                        String place_name = newsData.substring(newsData.indexOf("!") + 1);

                        Intent intent = new Intent(getApplicationContext(), RestaurantsModal.class);
                        intent.putExtra("Place", place_name);
                        intent.putExtra("RES_ID_NUMBER", place_id);
                        startActivity(intent);

                        //Toast.makeText(Events.this, "Selected :" + " " + newsData, Toast.LENGTH_LONG).show();
                    }
                });

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private class RequestAllEvents extends AsyncTask<String, String, ArrayList> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected ArrayList doInBackground(String... params) {

            OkHttpClient client = new OkHttpClient();

            String url = "http://groovapp.codist.co.za/get_all_events.php";

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = null;
            JSONObject Jobject = null;

            all_events = new ArrayList<String>();

            try {
                response = client.newCall(request).execute();
                String StringRespons = response.body().string();
                Jobject = new JSONObject(StringRespons);

                JSONArray Jarray = Jobject.getJSONArray("place_type");

                String event_url_image = "";
                String start_date = "";
                String end_date = "";
                String event_description = "";
                String place_id = "";
                String event_id = "";
                String place_name = "";



                for (int x = 0;x < Jarray.length();x++){
                    JSONObject object = Jarray.getJSONObject(x);

                    place_id = object.getString("place_id").toString();
                    event_id = object.getString("id").toString();
                    event_description = object.getString("event_description").toString();
                    event_url_image = object.getString("event_banner_url").toString();
                    start_date = object.getString("start_date").toString();
                    end_date = object.getString("end_date").toString();
                    place_name  = object.getString("place_name").toString();


                    String event_info = place_id + "@"
                            + event_id + "#"
                            + event_description
                            + "$" + event_url_image
                            + "&" + start_date
                            + "*" + end_date
                            + "!" + place_name;

                    all_events.add(event_info);

                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return all_events;
        }

        @Override
        protected void onPostExecute(ArrayList result) {
        }
    }
}

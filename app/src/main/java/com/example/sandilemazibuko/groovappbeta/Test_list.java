package com.example.sandilemazibuko.groovappbeta;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Test_list extends AppCompatActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    ListView list_place_type;
    ArrayAdapter adapter1;
    ArrayList place_type_results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);

        list_place_type = (ListView) findViewById(R.id.list_places);

        try {
            place_type_results = new RequestPlaceTypes().execute().get();


            //Toast.makeText(Test_list.this, place_type_results.toString(), Toast.LENGTH_SHORT).show();


            ArrayList palce_details = new ArrayList();


            String name = "";
            for (int x = 0; x < place_type_results.size(); x++) {
                name = place_type_results.get(x).toString().substring( place_type_results.get(x).toString().indexOf('*') + 1, place_type_results.get(x).toString().indexOf('#'));
                palce_details.add(name);

            }
            adapter1 =
                    new ArrayAdapter(this, android.R.layout.simple_spinner_item, palce_details);
            //list_place_type.setAdapter(adapter1);


            AlertDialog.Builder builderSingle = new AlertDialog.Builder(Test_list.this);
//            builderSingle.setIcon(R.drawable.groovapp_logo);
            builderSingle.setTitle("Places type");
            //builderSingle.setC



            builderSingle.setAdapter(adapter1,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String strName = "Name : " + place_type_results.get(which).toString().substring( place_type_results.get(which).toString().indexOf('*') + 1, place_type_results.get(which).toString().indexOf('#')) +
                           "\nDate Added : " + place_type_results.get(which).toString().substring(place_type_results.get(which).toString().indexOf('#') + 1,place_type_results.get(which).toString().indexOf('@')) +
                            "\nDate Modified : " + place_type_results.get(which).toString().substring(place_type_results.get(which).toString().indexOf('@') + 1);
                            AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                    Test_list.this);
                            builderInner.setMessage(strName);
                            builderInner.setTitle("Place Type Details");
                            builderInner.setPositiveButton(
                                    "Ok",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            builderInner.show();
                        }
                    }
                    );

            builderSingle.show();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    private class RequestPlaceTypes extends AsyncTask<String, String, ArrayList>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Test_list.this);
            pDialog.setMessage("Please wait loading Place Types");
            pDialog.show();
        }


        @Override
        protected ArrayList doInBackground(String... params) {

            String url = "http://groovapp.codist.co.za/get_all_place_type.php";

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = null;
            JSONObject Jobject = null;
            JSONArray Jarray = null;

            ArrayList<String> place_type_results = new ArrayList<>();

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
        protected void onPostExecute(ArrayList result) {
            pDialog.dismiss();
        }
    }
}

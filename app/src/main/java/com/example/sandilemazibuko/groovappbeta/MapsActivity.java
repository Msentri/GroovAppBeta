package com.example.sandilemazibuko.groovappbeta;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.Sprite;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.views.MapView;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MapsActivity extends AppCompatActivity {

    private GoogleMap mMap;

    public static final int DEFAULT_ZOOM = 10;


    public static JSONObject titleSandile = null;


    // Progress Dialog
    private ProgressDialog pDialog;

    private MapView mapView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        try {
//            JSONObject sandile = new RequestRestaurants().execute().get();
//
//            titleSandile = sandile;
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

        if(isOnline() == true){
            /** Create a mapView and give it some properties */
            mapView = (MapView) findViewById(R.id.mapview);
            mapView.setStyleUrl(Style.DARK);


            JSONArray Jarray = null;
            try {
                JSONObject sandile = new RequestRestaurants().execute().get();
                Jarray = sandile.getJSONArray("places");

                for(int x = 0; x < Jarray.length();x++){
                    JSONObject object = Jarray.getJSONObject(x);

                    final String restaurant_name = object.getString("restaurant_name");

                    String latitude = object.getString("latitude");
                    String longitude = object.getString("longitude");
                    String place_id = object.getString("id");
                    String type = object.getString("type");
                    final String contact = object.getString("contact");
                    String street = object.getString("street");
                    String city = object.getString("city");
                    final String province = object.getString("province");


                    double lati = Double.parseDouble(latitude);
                    double longLat = Double.parseDouble(longitude);

                    com.mapbox.mapboxsdk.geometry.LatLng sydney = new com.mapbox.mapboxsdk.geometry.LatLng(lati,longLat);





                    com.mapbox.mapboxsdk.annotations.MarkerOptions myMarker = new com.mapbox.mapboxsdk.annotations.MarkerOptions()
                            .position(sydney)
                            .title(restaurant_name)
                            .snippet(place_id)
                            ;
                    mapView.addMarker(myMarker);


                    mapView.setOnMarkerClickListener(new MapView.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(com.mapbox.mapboxsdk.annotations.Marker marker) {

                            Intent intent = new Intent(getApplicationContext(), RestaurantsModal.class);
                            intent.putExtra("Place", marker.getTitle());
                            intent.putExtra("RES_ID_NUMBER", marker.getSnippet());
                            startActivity(intent);
                            return false;
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            mapView.setCenterCoordinate(new com.mapbox.mapboxsdk.geometry.LatLng(-26.1019238, 28.0230654));

            mapView.setZoomLevel(9);
            mapView.onCreate(savedInstanceState);
        }else{
            Toast.makeText(MapsActivity.this, "Please Be Connected to internet.", Toast.LENGTH_SHORT).show();
        }

        ImageView imgHome = (ImageView)findViewById(R.id.imgHome);
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });

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

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private class RequestRestaurants extends AsyncTask <String, String, JSONObject>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MapsActivity.this);
            pDialog.setMessage("Please wait loading Restaurants....");
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            String url = "http://groovapp.codist.co.za/get_restaurants_places.php";

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
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

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause()  {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }





}

package com.example.sandilemazibuko.groovappbeta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    public static final int DEFAULT_ZOOM = 6;


    public static JSONObject titleSandile = null;


    // Progress Dialog
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        try {
            JSONObject sandile = new RequestRestaurants().execute().get();

            titleSandile = sandile;



        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        JSONArray Jarray = null;
        try {
            Jarray = titleSandile.getJSONArray("places");

            for(int x = 0; x < Jarray.length();x++){
                JSONObject object = Jarray.getJSONObject(x);

                String restaurant_name = object.getString("restaurant_name");

                String latitude = object.getString("latitude");
                String longitude = object.getString("longitude");

                double lati = Double.parseDouble(latitude);
                double longLat = Double.parseDouble(longitude);

                // Add a marker in Sydney and move the camera
                LatLng sydney = new LatLng(lati, longLat);
                mMap.addMarker(
                        new MarkerOptions()
                                .position(sydney)
                                .title(restaurant_name)
                                .snippet(latitude + " " + longitude)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map)));

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        Toast.makeText(MapsActivity.this, marker.getTitle() + " " + marker.getPosition().latitude
                                , Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, DEFAULT_ZOOM));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    private class RequestRestaurants extends AsyncTask <String, String, JSONObject>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MapsActivity.this);
            pDialog.setMessage("Please wait loading Restaurants  ....");
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



}

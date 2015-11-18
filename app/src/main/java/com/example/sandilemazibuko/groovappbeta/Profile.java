package com.example.sandilemazibuko.groovappbeta;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.Sprite;
import com.mapbox.mapboxsdk.annotations.SpriteFactory;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
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
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Profile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    LocalStorage userDatabase;

    private GoogleMap mMap;

    public static final int DEFAULT_ZOOM = 10;


    public static JSONObject titleSandile = null;


    // Progress Dialog
    private ProgressDialog pDialog;

    private MapView mapView = null;

    ListView list_place_type;
    ArrayAdapter adapter1,adapter2;
    ArrayList place_type_results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userDatabase = new LocalStorage(this);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            if (isOnline() == true) {
                /** Create a mapView and give it some properties */
                mapView = (MapView) findViewById(R.id.mapview);
                mapView.setStyleUrl(Style.DARK);


                JSONArray Jarray = null;
                try {
                    String[] mainMap = {"main_map"};

                    JSONObject sandile = new RequestRestaurants().execute(mainMap).get();
                    Jarray = sandile.getJSONArray("places");

                    for (int x = 0; x < Jarray.length(); x++) {
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

                        SpriteFactory spriteFactory = new SpriteFactory(mapView);
                        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.map);
                        Sprite icon = spriteFactory.fromDrawable(drawable);

                        com.mapbox.mapboxsdk.geometry.LatLng sydney = new com.mapbox.mapboxsdk.geometry.LatLng(lati, longLat);

                        com.mapbox.mapboxsdk.annotations.MarkerOptions myMarker = new com.mapbox.mapboxsdk.annotations.MarkerOptions()
                                .position(sydney)
                                .title(restaurant_name)
                                .snippet(place_id)
                                .icon(icon)
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

                /** Create a mapView and give it some properties */
            } else {
                Toast.makeText(Profile.this, "Please Be Connected to internet.", Toast.LENGTH_SHORT).show();
            }
        }else{
            if (isOnline() == true) {
                /** Create a mapView and give it some properties */
                mapView = (MapView) findViewById(R.id.mapview);
                mapView.setStyleUrl(Style.DARK);

                String placetypeid = extras.getString("place_type_id");


                JSONArray Jarray = null;
                try {
                    String[] mainMap = {placetypeid};

                    JSONObject sandile = new RequestRestaurants().execute(mainMap).get();
                    Jarray = sandile.getJSONArray("places");

                    for (int x = 0; x < Jarray.length(); x++) {
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

                        SpriteFactory spriteFactory = new SpriteFactory(mapView);
                        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.map);
                        Sprite icon = spriteFactory.fromDrawable(drawable);


                        com.mapbox.mapboxsdk.geometry.LatLng sydney = new com.mapbox.mapboxsdk.geometry.LatLng(lati, longLat);

                        com.mapbox.mapboxsdk.annotations.MarkerOptions myMarker = new com.mapbox.mapboxsdk.annotations.MarkerOptions()
                                .position(sydney)
                                .title(restaurant_name)
                                .snippet(place_id)
                                .icon(icon);
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

                /** Create a mapView and give it some properties */
            } else {
                Toast.makeText(Profile.this, "Please Be Connected to internet.", Toast.LENGTH_SHORT).show();
            }
        }




        ImageView imgHome = (ImageView)findViewById(R.id.imgHome);
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });

        ImageView imageUber = (ImageView)findViewById(R.id.imageUber);
        imageUber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Uber.class);
                startActivity(intent);
            }
        });

        ImageView imageSearch = (ImageView)findViewById(R.id.imageSearch);
        imageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Search.class);
                startActivity(intent);
            }
        });

        ImageView imageDrinks = (ImageView)findViewById(R.id.imageDrinks);
        imageDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Drinks.class);
                startActivity(intent);
            }
        });


        ImageView imgFilter = (ImageView)findViewById(R.id.btnFilter);
        imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**SANDILE DIALOG BOX*/

                //list_place_type = (ListView) findViewById(R.id.list_places);

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
                            new ArrayAdapter(Profile.this, android.R.layout.simple_spinner_item, palce_details);


                    //list_place_type.setAdapter(adapter1);


                    AlertDialog.Builder builderSingle = new AlertDialog.Builder(Profile.this,AlertDialog.THEME_HOLO_DARK);
//            builderSingle.setIcon(R.drawable.groovapp_logo);
                    builderSingle.setTitle("Places type");

                    //builderSingle.setC



                    builderSingle.setAdapter(adapter1,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String strName = place_type_results.get(which).toString().substring( place_type_results.get(which).toString().indexOf('*') + 1, place_type_results.get(which).toString().indexOf('#')) +
                                            "\nDate Added : " + place_type_results.get(which).toString().substring(place_type_results.get(which).toString().indexOf('#') + 1,place_type_results.get(which).toString().indexOf('@')) +
                                            "\nDate Modified : " + place_type_results.get(which).toString().substring(place_type_results.get(which).toString().indexOf('@') + 1);


                                    String restaurant_name = place_type_results.get(which).toString()
                                            .substring( place_type_results.get(which).toString().indexOf('*') + 1,
                                            place_type_results.get(which).toString().indexOf('#'));

                                    String place_type_id = place_type_results.get(which).toString()
                                            .substring( 0,
                                                    place_type_results.get(which).toString().indexOf('*'));

                                    Intent intent = new Intent(getApplicationContext(), Profile.class);
                                    intent.putExtra("place_type_id", place_type_id);
                                    startActivity(intent);
                                }
                            }
                    );
                    builderSingle.setPositiveButton(
                            "Cancel",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(
                                        DialogInterface dialog,
                                        int which) {
                                    dialog.dismiss();
                                }
                            });
                    builderSingle.show();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


                /**SANDILE DIALOG BOX*/

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /**
         * LOCAL STORE
         */
        String USER_ID = userDatabase.sharedPreferences.getString("user_id","");
        String NAME = userDatabase.sharedPreferences.getString("user_name","");
        String SURNAME = userDatabase.sharedPreferences.getString("user_surname","");
        String EMAIL = userDatabase.sharedPreferences.getString("user_email","");
        /**
         * LOCAL STORE
         */

//        TextView txtname = (TextView) findViewById(R.id.txtnamedisplay);
//        String USER_FULL_NAMES = NAME + " " + SURNAME;
//        txtname.setText(USER_FULL_NAMES);
//        TextView txtemailProfile = (TextView) findViewById(R.id.txtemailProfile);
//        txtemailProfile.setText(EMAIL);

//        Button myMapButton = (Button) findViewById(R.id.myMapButton);
//        myMapButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Profile.this, MapsActivity.class);
//                startActivity(intent);
//            }
//        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.action_logout){
            userDatabase.clearUserData();
            Intent intent = new Intent(Profile.this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.upgrade_membership) {
            // Handle the camera action
        } else if (id == R.id.account_settings) {

        } else if (id == R.id.upgrade_membership) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
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

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private class RequestRestaurants extends AsyncTask <String, String, JSONObject>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Profile.this);
            pDialog.setMessage("Please wait loading Restaurants....");
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            String url = "http://groovapp.codist.co.za/get_restaurants_places.php";

            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormEncodingBuilder()
                    .add("condition", params[0])
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
    private class RequestPlaceTypes extends AsyncTask<String, String, ArrayList>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Profile.this);
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

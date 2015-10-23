package com.example.sandilemazibuko.groovappbeta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class RestaurantsModal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_restaurants_modal);

        DisplayMetrics dm =  new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("Place");

            TextView txtPlace = (TextView)findViewById(R.id.txtPlace);
            txtPlace.setText(value);
        }




    }
}

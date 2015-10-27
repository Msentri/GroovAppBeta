package com.example.sandilemazibuko.groovappbeta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RestaurantsModal extends AppCompatActivity {

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
            String restaurant_province = extras.getString("province");

            TextView txtPlace = (TextView)findViewById(R.id.txtPlace);
            txtPlace.setText(restaurant_name);

            TextView txtCity = (TextView)findViewById(R.id.txtCity);
            txtCity.setText(restaurant_province);
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
}

package com.example.sandilemazibuko.groovappbeta;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCode extends AppCompatActivity {
    LocalStorage userDatabase;

    public String entryStatus;
    public String bottleOffter;
    public String restaurant_name;
    public String restaurant_provinces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        userDatabase = new LocalStorage(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String NAME = userDatabase.sharedPreferences.getString("user_name","");
        String SURNAME = userDatabase.sharedPreferences.getString("user_surname","");
        String EMAIL = userDatabase.sharedPreferences.getString("user_email","");
        String DOB = userDatabase.sharedPreferences.getString("user_dob","");





        Bundle extras = getIntent().getExtras();
        if (extras != null) {

             entryStatus = extras.getString("entryStatus");
             bottleOffter = extras.getString("bottleOffter");
             restaurant_name = extras.getString("restaurant_name");
             restaurant_provinces = extras.getString("restaurant_provinces");
        }

        TextView txtEntrance = (TextView)findViewById(R.id.txtEntrance);
        TextView txtBottleOffer = (TextView)findViewById(R.id.txtBottleOffer);
        TextView txtProvince = (TextView)findViewById(R.id.txtProvince);


        txtEntrance.setText(entryStatus + " Entrance");
        txtBottleOffer.setText(bottleOffter + " Bottles");
        txtProvince.setText(restaurant_name + " - " + restaurant_provinces);


        String QRC = "Name : " +NAME
                + "\n Surname : "+ SURNAME
                + "\nEmail : " + EMAIL
                + "\nDateOfBirth : " + DOB
                + "\nApplication Name : GroovApp"
                + "ENTRY STATUS : " + entryStatus
                + "BOTTLE OFFER :" + bottleOffter
                + "RESTAURANT NAME " + restaurant_name
                + "RESTAURANT PROVINCE " + restaurant_provinces;


        ImageView imgeQR = (ImageView)findViewById(R.id.QRcodeImageViewer);

        QRCodeWriter writer = new QRCodeWriter();
        try {

            BitMatrix bitMatrix = writer.encode(QRC , BarcodeFormat.QR_CODE, 400, 400);

            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    bitmap.setPixel(i, j, bitMatrix.get(i, j) ? Color.BLACK: Color.WHITE);
                }
            }
            imgeQR.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}

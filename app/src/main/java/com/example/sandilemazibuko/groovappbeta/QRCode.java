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

        TextView txtname = (TextView)findViewById(R.id.txtNames);
        TextView txtsurname = (TextView)findViewById(R.id.txtSurname);
        TextView txtEmail = (TextView)findViewById(R.id.txtEmail);
        TextView txtdob = (TextView)findViewById(R.id.txtdateofbirth);

        txtname.setText("Name : " + NAME);
        txtsurname.setText("Surname : " + SURNAME);
        txtEmail.setText("Email : " + EMAIL);
        txtdob.setText("Date Of Birth " + DOB);


        String QRC = "Name : " +NAME + "\n Surname : "+ SURNAME + "\nEmail : " + EMAIL + "\nDateOfBirth : " + DOB + "\nApplication Name : GroovApp" ;


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

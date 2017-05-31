package com.example.himanshu.rentbasera;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.himanshu.rentbasera.url.WebServiceURL;

public class MoreDetailsAboutPG extends AppCompatActivity {
    TextView pgname,pgaddress,no_of_beds,bed_price,amount;
     ImageView pgimage;
    Button bookNowBtn;
    Bundle bundle;
    PG myPg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_details_about_pg);
        bundle= getIntent().getBundleExtra("bundle");

         myPg =(PG)bundle.getSerializable("pgdata");
        Log.d("name----",myPg.getPgname());
        Log.d("area----",myPg.getPgArea());
      //  Log.d("area----",myPg.getNo_of_beds());

        pgname=(TextView)findViewById(R.id.pgname);
        pgaddress=(TextView)findViewById(R.id.pgaddress);
        no_of_beds=(TextView) findViewById(R.id.no_of_beds);
        bed_price=(TextView) findViewById(R.id.bedprice);
        amount=(TextView) findViewById(R.id.amount);
        bookNowBtn=(Button) findViewById(R.id.buttonBookNow);
          pgimage=(ImageView) findViewById(R.id.pgimage);
        Glide.with(getApplicationContext())
                .load("http://"+WebServiceURL.IP+"/RestServiceRegisterLogin/"+myPg.getImageurl())
                .into(pgimage);
        Log.i("imagename"," "+myPg.getImageurl());
          pgname.setText(myPg.getPgname());
          pgaddress.setText(myPg.getPgArea() + " , " +myPg.getLocation());
          no_of_beds.setText(String.valueOf(myPg.getNo_of_beds()));
          bed_price.setText(String.valueOf(myPg.getAmount()));
          amount.setText(String.valueOf(myPg.getAmount()));

         bookNowBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent =new Intent(getApplicationContext(),LoginActivity.class);
                 Bundle bundle=new Bundle();
                 bundle.putSerializable("pgdata",myPg);

                 intent.putExtra("bundle",bundle);

                 startActivity(intent);
             }
         });



    }
}

package com.example.himanshu.rentbasera;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.himanshu.rentbasera.url.WebServiceURL;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CheckOutActivity extends AppCompatActivity {
      TextView pgname,pgaddress,no_of_bed,bed_price,security,total_price;
      ImageView pgimage;
      Button confirmBooking;
      Bundle bundle;
    private static  final String TAG="event";
      PG myPg;
      User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        user=new User(CheckOutActivity.this);
        bundle= getIntent().getBundleExtra("bundle");
        myPg =(PG)bundle.getSerializable("pgdata");

        pgimage=(ImageView)findViewById(R.id.pgimage);
        pgname=(TextView)findViewById(R.id.pgname);
        pgaddress=(TextView)findViewById(R.id.pgaddress);
        no_of_bed=(TextView)findViewById(R.id.no_of_beds);
        bed_price=(TextView)findViewById(R.id.bedprice);
        security=(TextView)findViewById(R.id.security);
        total_price=(TextView)findViewById(R.id.totalamount);
        confirmBooking=(Button)findViewById(R.id.buttonConfirm);

        Glide.with(getApplicationContext())
                .load("http://"+WebServiceURL.IP+"/RestServiceRegisterLogin/"+myPg.getImageurl())
                .into(pgimage);
          pgname.setText(myPg.getPgname());
          pgaddress.setText(myPg.getPgArea() + " " + myPg.getLocation());
          no_of_bed.setText(String.valueOf(myPg.getNo_of_beds()));
          String bedprice=String.valueOf(myPg.getAmount()* myPg.getNo_of_beds() );
          bed_price.setText(bedprice);
          String security_amount= String.valueOf((Integer.parseInt(bedprice)/2));
          security.setText(security_amount);
           String total_amount=String.valueOf(Integer.parseInt(bedprice) + ((Integer.parseInt(bedprice)/2)));
            total_price.setText(total_amount);

        confirmBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              volleyCall(String.valueOf(myPg.getPgid()),user.getEmail());
            }
        });

    }


    private void volleyCall(final String pgid,String email)
    {

        RequestQueue queue= Volley.newRequestQueue(this);
        Map<String,String> jsonParams=new HashMap<String,String>();
        final ProgressDialog progressDialog=new ProgressDialog(CheckOutActivity.this);
        progressDialog.setMessage("Please wait..!!");
        progressDialog.setTitle("Checking Your Details....");
        progressDialog.show();
        progressDialog.setMax(200);
        progressDialog.setCancelable(true);
        progressDialog.setIcon(R.mipmap.ic_launcher);



        jsonParams.put("pgid",pgid);

        jsonParams.put("email",email);
        Log.d(TAG,"Json:"+new JSONObject(jsonParams));
        JsonObjectRequest postRequest=new JsonObjectRequest(Request.Method.POST, WebServiceURL.BOOKING_URL, new JSONObject(jsonParams), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String msg = null;
                try {
                    progressDialog.dismiss();
                    msg = (String) response.get("msg");
                    Log.d(TAG," "+msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (msg.equals("Booked")) {

                      progressDialog.dismiss();
                    Toast.makeText(CheckOutActivity.this, "Booking Confirmed", Toast.LENGTH_SHORT).show();
                   // User user=new User(LoginActivity.this);
                    //user.setEmail(email);
                    //Log.d(TAG,"email: "+email);
                    Intent intent=new Intent(CheckOutActivity.this,Home.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }
                else if(msg.equals("email not found")) {
                    progressDialog.dismiss();
                    Toast.makeText(CheckOutActivity.this, "Email Not found", Toast.LENGTH_SHORT).show();
                }
                else if(msg.equals("pg not available")) {
                    progressDialog.dismiss();
                    Toast.makeText(CheckOutActivity.this, "PG Not Available", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(CheckOutActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
                    Log.d(TAG, "Json" + response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(CheckOutActivity.this, "Booking Confirmed", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(CheckOutActivity.this,Home.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                        Log.d(TAG,"ERROR:"+error+ "\nmessage" + error.getMessage());
                    }
                });
        postRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });



        queue.add(postRequest);
    }




}

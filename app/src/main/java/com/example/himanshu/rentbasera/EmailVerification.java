package com.example.himanshu.rentbasera;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.himanshu.rentbasera.url.WebServiceURL;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EmailVerification extends AppCompatActivity {
    private static final String TAG="event";
     Button verifyEmailBtn;
    EditText otpEditText;
    Bundle bundle;
    PG myPg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);

        bundle= getIntent().getBundleExtra("bundle");
        myPg =(PG)bundle.getSerializable("pgdata");

        verifyEmailBtn=(Button)findViewById(R.id.confirmEmailbtn);
        otpEditText=(EditText)findViewById(R.id.otpEditBox);

        Bundle bundle=getIntent().getExtras();
        final String otp=(String)bundle.getString("otp");
        final String email=(String)bundle.getString("email");
        verifyEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(otpEditText.getText().toString().equals(otp)) {
                    Toast.makeText(EmailVerification.this, "Verified", Toast.LENGTH_SHORT).show();
                    volleyCallForEmailVerification(email,otp);
                }
                else
                    Toast.makeText(EmailVerification.this,"OTP not macthed",Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void volleyCallForEmailVerification(String email,String otp)
    {

        RequestQueue queue= Volley.newRequestQueue(this);
        Map<String,String> jsonParams=new HashMap<String,String>();
        jsonParams.put("email",email);
        jsonParams.put("otp",otp);
        Log.d(TAG,"Json:"+new JSONObject(jsonParams));
        JsonObjectRequest postRequest=new JsonObjectRequest(Request.Method.POST, WebServiceURL.EMAIL_VERIFICATION_URL, new JSONObject(jsonParams), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String msg = null;
                try {
                    msg = (String) response.get("msg");
                    Log.d(TAG," "+msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (msg.equals("Registered Successfully")) {

                    Toast.makeText(EmailVerification.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    //sendMail(email);
                     Intent intent=new Intent(EmailVerification.this,LoginActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("pgdata",myPg);

                    intent.putExtra("bundle",bundle);

                    startActivity(intent);
                    finish();
                }
                else
                {

                }

                Log.d(TAG, "Json" + response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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

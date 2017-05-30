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

public class ForgotPasswordActivity extends AppCompatActivity {
    private static final String TAG="event";
    Button passwordResetBtn;
    EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        email= (EditText)findViewById(R.id.emailEditText);
        passwordResetBtn=(Button)findViewById(R.id.resetPasswordBtn);
        passwordResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(email.getText().toString().equals(""))
                    Toast.makeText(ForgotPasswordActivity.this, "Enter Email Id", Toast.LENGTH_SHORT).show();
                else
                    volleyCall(email.getText().toString());

            }
        });

    }

    private void volleyCall(final String email)
    {

        RequestQueue queue= Volley.newRequestQueue(this);
        final Map<String,String> jsonParams=new HashMap<String,String>();
        jsonParams.put("email",email);

        Log.d(TAG,"Json:"+new JSONObject(jsonParams));
        JsonObjectRequest postRequest=new JsonObjectRequest(Request.Method.POST, WebServiceURL.PASSWORD_RESET_URL, new JSONObject(jsonParams), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String msg = null;
                try {
                    msg = (String) response.get("msg");
                    Log.d(TAG," "+msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (msg.equals("Network Error")) {

                    Toast.makeText(ForgotPasswordActivity.this, "Password Reset OTP not sent", Toast.LENGTH_SHORT).show();
                    //sendMail(email);

                }
                else
                {
                     // String otp= jsonParams.get(msg);
                    Toast.makeText(ForgotPasswordActivity.this, "OTP sent", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(ForgotPasswordActivity.this,PasswordReset.class);
                    intent.putExtra("email",email);
                    intent.putExtra("passwordResetOTP",msg);
                    startActivity(intent);
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

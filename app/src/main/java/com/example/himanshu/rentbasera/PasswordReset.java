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

public class PasswordReset extends AppCompatActivity {
    private static final String TAG="event";
  EditText otpEditBox,newPassword,confirmPassword;
    Button resetPasswordBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        otpEditBox=(EditText)findViewById(R.id.otpEditBox);
        newPassword=(EditText)findViewById(R.id.NewPasswordEditText);
        confirmPassword=(EditText)findViewById(R.id.ConfirmPasswordEditText);
        resetPasswordBtn=(Button)findViewById(R.id.resetButton);

        Bundle bundle=getIntent().getExtras();
        final String passwordResetOTP=bundle.getString("passwordResetOTP");
        Log.d("event"," "+passwordResetOTP);
       // Log.d("otp::",passwordResetOTP);
        final String email=bundle.getString("email");


        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("event"," "+passwordResetOTP);
                if(otpEditBox.getText().toString().equals(passwordResetOTP))
                {
                    if(newPassword.getText().toString().equals(confirmPassword.getText().toString()))
                    {
                        volleyCallForPasswordReset(email,newPassword.getText().toString());
                        //Toast.makeText(PasswordReset.this,"Password Changed",Toast.LENGTH_SHORT).show();
                    }
                }else
                {
                    Toast.makeText(PasswordReset.this,"OTP not Matched",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



    private void volleyCallForPasswordReset(String email,String newPassword)
    {

        RequestQueue queue= Volley.newRequestQueue(this);
        Map<String,String> jsonParams=new HashMap<String,String>();
        jsonParams.put("email",email);
        jsonParams.put("password",newPassword);

        Log.d(TAG,"Json:"+new JSONObject(jsonParams));
        JsonObjectRequest postRequest=new JsonObjectRequest(Request.Method.POST, WebServiceURL.PASSWORD_RESET, new JSONObject(jsonParams), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String msg = null;
                try {
                    msg = (String) response.get("msg");
                    Log.d(TAG," "+msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (msg.equals("password reset successfully"))
                {

                    Toast.makeText(PasswordReset.this, "Password Reset", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(PasswordReset.this,LoginActivity.class);
                     startActivity(intent);
                    finish();
                 }
                else
                {
                    Toast.makeText(PasswordReset.this, "Fail", Toast.LENGTH_SHORT).show();
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

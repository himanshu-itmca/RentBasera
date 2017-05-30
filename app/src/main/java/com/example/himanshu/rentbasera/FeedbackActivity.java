package com.example.himanshu.rentbasera;

import android.app.ProgressDialog;
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

public class FeedbackActivity extends AppCompatActivity {
    private static final String TAG ="event" ;
    EditText titleEditText,emailEditText,commentEditText;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        titleEditText=(EditText)findViewById(R.id.title);
        emailEditText=(EditText)findViewById(R.id.email);
        commentEditText=(EditText)findViewById(R.id.comments);
        submit=(Button)findViewById(R.id.feedbacksubmitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=titleEditText.getText().toString();
                String email=emailEditText.getText().toString();
                String comments=commentEditText.getText().toString();
                if(!Utility.isNotNull(title) )
                    titleEditText.setError("Enter Some Title For Feedback");
               else if(!Utility.isNotNull(email))
                {
                  emailEditText.setError("Enter your Email");
                }
               else if(!Utility.validateEmail(email))
                  emailEditText.setError("Wrong Email Format");
                else if(!Utility.isNotNull(comments))
                    commentEditText.setError("Enter Comments For Feedback");
                else sendFeedback(title,email,comments);
            }
        });
    }


    public void sendFeedback(String title,String email,String comments)
    {
        RequestQueue queue= Volley.newRequestQueue(this);
        Map<String,String> jsonParams=new HashMap<String,String>();
        final ProgressDialog progressDialog=new ProgressDialog(FeedbackActivity.this);;
        progressDialog.setMessage("Please wait..!!");
        progressDialog.setTitle("Sending Your Feedback...");
        progressDialog.show();
        progressDialog.setMax(200);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.ic_launcher);



        jsonParams.put("title",title);
        jsonParams.put("email",email);

        jsonParams.put("comments",comments);
        Log.d(TAG,"Json:"+new JSONObject(jsonParams));
        JsonObjectRequest postRequest=new JsonObjectRequest(Request.Method.POST, WebServiceURL.FEEDBACK_URL, new JSONObject(jsonParams), new Response.Listener<JSONObject>() {
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

                if (msg.equals("feedback sent")) {

                         progressDialog.dismiss();
                    Toast.makeText(FeedbackActivity.this, "Feedback Sent", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(FeedbackActivity.this,Home.class);
                    startActivity(intent);
                    finish();

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

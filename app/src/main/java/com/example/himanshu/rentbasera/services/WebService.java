package com.example.himanshu.rentbasera.services;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.himanshu.rentbasera.UserProfileActivity;
import com.example.himanshu.rentbasera.url.WebServiceURL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by himanshu on 5/21/2017.
 */

public class WebService {
    private static String TAG="event";

    Context context;
    public static void getUserData(String email, final Context context)
    {

        RequestQueue queue= Volley.newRequestQueue(context);
        Map<String,String> jsonParams=new HashMap<String,String>();
        jsonParams.put("email",email);
//        final ProgressDialog progressDialog=new ProgressDialog(Home.this);
//        progressDialog.setMessage("Please wait..!!");
//        progressDialog.setTitle("Getting PG List");
//        progressDialog.show();
//        progressDialog.setMax(200);
//        progressDialog.setCancelable(false);
//        progressDialog.setIcon(R.mipmap.ic_launcher);
//        final AlertDialog.Builder builder=new AlertDialog.Builder(Home.this);



        Log.d(TAG,"Json:"+new JSONObject(jsonParams));
        JsonObjectRequest postRequest=new JsonObjectRequest(Request.Method.POST, WebServiceURL.GET_USER_DATA, new JSONObject(jsonParams), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String msg = null;
                try {
                     String username ,useremail,mobile,password;
                //    progressDialog.dismiss();
                    // msg = (String)response.get("location");
                    Log.d(TAG," "+msg);

                    JSONArray jsonArray= response.getJSONArray("user");
                    Log.d(TAG,jsonArray.toString());
                    //pgList.clear();
                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                         username = jsonObject.get("name").toString();
                        Log.d("name---",username);
                         useremail = jsonObject.get("email").toString();
                         mobile = jsonObject.get("mobile").toString();
                         password=jsonObject.get("password").toString();
                        Intent intent=new Intent(context,UserProfileActivity.class);

                        intent.putExtra("username",username);
                        intent.putExtra("useremail",useremail);
                        intent.putExtra("mobile",mobile);
                        intent.putExtra("password",password);
                        context.startActivity(intent);

                    }





                } catch (Exception e) {
                    e.printStackTrace();
                }

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

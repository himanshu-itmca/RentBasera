package com.example.himanshu.rentbasera;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserProfileActivity extends AppCompatActivity {
    private static final String TAG ="event" ;
    ListView listView;
     Dialog dialog;
    String password,useremail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Bundle bundle=getIntent().getExtras();
        String username=(String)bundle.get("username");
        useremail=(String)bundle.get("useremail");
         password=(String)bundle.get("password");
        String mobile=(String)bundle.get("mobile");
        List list=new ArrayList();
        list.add("Name:-  "+username);
        list.add("Email:-  "+useremail);
      //  list.add(password);
        list.add("Mobile No:-  "+mobile);
        list.add("Change Password");

        listView=(ListView)findViewById(R.id.listView);
        ArrayAdapter arrayAdapter =new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = (String)parent.getItemAtPosition(position);
                if(value.equals("Change Password"))
                {
                    showChangePassword();
                }
            }
        });
    }


    public void showChangePassword()
    {

         dialog = new Dialog(UserProfileActivity.this,android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.chnage_password_dialog);
        final   EditText currentPassword=(EditText)dialog.findViewById(R.id.curentPassword);
        final   EditText newPassword=(EditText)dialog.findViewById(R.id.newPassword);
        final   EditText confirmPassword=(EditText)dialog.findViewById(R.id.confirmPassword);
        Button update=(Button)dialog.findViewById(R.id.updateButton);
        dialog.show();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String currentPass=currentPassword.getText().toString();
                 String newPass=newPassword.getText().toString();
                String confirmPass=confirmPassword.getText().toString();
                 if(currentPass.equals(password))
                 {
                     if(!com.example.himanshu.rentbasera.Utility.validatePassword(newPass))
                     {
                         newPassword.setError("Password must have minimum 6 letters");
                     }
                     else
                     {
                         if(newPass.equals(confirmPass))
                         {
                           //  Toast.makeText(UserProfileActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                             changePassword(useremail, newPass);
                         }
                         else
                             confirmPassword.setError("password not same");
                     }
                 }
                else
                 {currentPassword.setError("Your Current password is Wrong");}
            }
        });




    }
    public void changePassword(String email,String newPass)
    {
        final ProgressDialog progressDialog = new ProgressDialog(UserProfileActivity.this);
        progressDialog.setMessage("Please wait..!!");
        progressDialog.setTitle("Changing Password ");
        progressDialog.setCancelable(false);
        progressDialog.setMax(200);
        progressDialog.show();

        Log.d("email---",email);
        RequestQueue queue= Volley.newRequestQueue(this);
        Map<String,String> jsonParams=new HashMap<String,String>();
        jsonParams.put("email",email);
        jsonParams.put("password",newPass);

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
                      progressDialog.dismiss();
                    Toast.makeText(UserProfileActivity.this, "Password Changed", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                else
                {
                    Toast.makeText(UserProfileActivity.this, "Fail", Toast.LENGTH_SHORT).show();
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

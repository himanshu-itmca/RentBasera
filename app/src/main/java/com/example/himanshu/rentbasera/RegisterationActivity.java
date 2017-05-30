package com.example.himanshu.rentbasera;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;

public class RegisterationActivity extends AppCompatActivity {
    private static final String TAG="event";
    EditText fullname,email,mobile,password,confirmPassword;
    Button registerbtn;
    Bundle bundle;
    PG myPg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        bundle= getIntent().getBundleExtra("bundle");

        myPg =(PG)bundle.getSerializable("pgdata");


        fullname=(EditText)findViewById(R.id.fullNameEditText);
        email=(EditText)findViewById(R.id.emailEditText);
        mobile=(EditText)findViewById(R.id.mobileEditText);
        password=(EditText)findViewById(R.id.passwordEditText);
        confirmPassword=(EditText)findViewById(R.id.confirmEditText);
        registerbtn=(Button)findViewById(R.id.registerButton);
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 String name=fullname.getText().toString();
                 String emailid=email.getText().toString();
                 String mob=mobile.getText().toString();
                String pass=password.getText().toString();
                String confirmpass=confirmPassword.getText().toString();
                if(name.equals("") || emailid.equals("") || mob.equals("") ||pass.equals("") || confirmpass.equals(""))
                    Toast.makeText(RegisterationActivity.this,"Fields Must Not Be Blank",Toast.LENGTH_SHORT).show();
                else if(!Utility.validateEmail(emailid)){

                    email.setError("Wrong Format");
                }
                else if(Utility.validateMinMobile(mob)) {
                     mobile.setError("Minimum No.must be 10");
                }
                else if(Utility.validateMaxMobile(mob)){
                    mobile.setError("Mobile Number Cant be More than 10");
                }
                else if(!Utility.validatePassword(pass)){
                    password.setError("Password must have minimum 6 letters");
                }

                else
                {
                     if(pass.equals(confirmpass))
                         volleyCall(name,emailid,mob,pass);
                      else
                         Toast.makeText(RegisterationActivity.this,"Password Must Be Same",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void volleyCall(String name, final String email,String mobile,String password)
    {

        RequestQueue queue= Volley.newRequestQueue(this);
        Map<String,String> jsonParams=new HashMap<String,String>();
          jsonParams.put("name",name);
        jsonParams.put("email",email);
        jsonParams.put("mobile",mobile);
        jsonParams.put("password",password);
        final ProgressDialog progressDialog=new ProgressDialog(RegisterationActivity.this);
        progressDialog.setMessage("Please wait..!!");
        progressDialog.setTitle("Registration is in progress");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        final AlertDialog.Builder builder=new AlertDialog.Builder(RegisterationActivity.this);



        Log.d(TAG,"Json:"+new JSONObject(jsonParams));
        JsonObjectRequest postRequest=new JsonObjectRequest(Request.Method.POST, WebServiceURL.REGISTRATION_URL, new JSONObject(jsonParams), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String msg = null;
                try {
                    msg = (String) response.get("msg");
                    Log.d(TAG," "+msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (msg.equals("User Already Exist")) {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterationActivity.this, "User Already Exists", Toast.LENGTH_SHORT).show();
                    //sendMail(email);

                }
                else if(msg.equals("fail"))
                {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterationActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                }
                else
                {

                progressDialog.dismiss();
                    builder.setIcon(R.mipmap.ic_launcher);
                    builder.setTitle("Alert");
                    builder.setMessage("Click Ok To Verify Your Email Adress");
                    final String otp = msg;
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(RegisterationActivity.this,EmailVerification.class);
                            intent.putExtra("otp", otp);
                            intent.putExtra("email",email);
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("pgdata",myPg);

                            intent.putExtra("bundle",bundle);


                            startActivity(intent);
                            finish();
                        }
                    });

                    builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                          Toast.makeText(getApplicationContext(),"Please Use Forget Button",Toast.LENGTH_SHORT).show();
                               }
                    });



                    AlertDialog alertDialog=builder.create();

                    alertDialog.show();


                    Toast.makeText(RegisterationActivity.this, "Enter OTP to verify your Mail Id", Toast.LENGTH_SHORT).show();

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

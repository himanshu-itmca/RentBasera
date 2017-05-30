package com.example.himanshu.rentbasera;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.himanshu.rentbasera.url.WebServiceURL;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
   private static  final String TAG="event";
    EditText email,pass;
    TextView skipTextView ;
    Button newUSer,signbtn;
    CheckBox showPasswordCheckBox;
    User user;
    PG myPg;
    Bundle bundle;
    LoginButton loginButton;
    private CallbackManager callbackManager;
    FacebookCallback<LoginResult> callback;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;



    public LoginActivity(){}
    @Nullable
   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        bundle= getIntent().getBundleExtra("bundle");

        myPg =(PG)bundle.getSerializable("pgdata");

        user=new User(LoginActivity.this);
         if(user.getEmail() != "")
        {
            Intent intent=new Intent(getApplicationContext(),CheckOutActivity.class);
            Bundle bundle=new Bundle();
            bundle.putSerializable("pgdata",myPg);

            intent.putExtra("bundle",bundle);

            startActivity(intent);
            finish();
        }


        loginButton=(LoginButton)findViewById(R.id.login_button);


//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                callbackManager = CallbackManager.Factory.create();
//
//                accessTokenTracker = new AccessTokenTracker() {
//                    @Override
//                    protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
//
//                    }
//                };
//
//                profileTracker = new ProfileTracker() {
//                    @Override
//                    protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
//                        displayMessage(newProfile);
//
//                    }
//                };
//
//                accessTokenTracker.startTracking();
//                profileTracker.startTracking();
//
//
//                callback = new FacebookCallback<LoginResult>() {
//                    @Override
//                    public void onSuccess(LoginResult loginResult) {
//                        AccessToken accessToken = loginResult.getAccessToken();
//                        Profile profile = Profile.getCurrentProfile();
//
//                        displayMessage(profile);
//
//                    }
//                    @Override
//                    public void onCancel() {
//
//                    }
//
//                    @Override
//                    public void onError(FacebookException e) {
//
//                    }
//
//
//                };
//                loginButton.setReadPermissions("user_friends");
//
//                loginButton.registerCallback(callbackManager, callback);
//
//
//
//            }
//        });

//        skipTextView=(TextView)findViewById(R.id.skipTextView);
//         skipTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(),Home.class);
//                startActivity(intent);
//                finish();
//
//
//
//            }
//        });


        showPasswordCheckBox=(CheckBox)findViewById(R.id.showPasswordCheckBox);
//          if(showPasswordCheckBox.isChecked())
//           pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//          else

      showPasswordCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              if(isChecked)
                 // pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                  pass.setTransformationMethod(null);
              else
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                 // pass.setInputType(InputType.TYPE_CLASS_TEXT);
          }
      });

        newUSer=(Button)findViewById(R.id.newuser);
        newUSer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),RegisterationActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("pgdata",myPg);

                intent.putExtra("bundle",bundle);

                startActivity(intent);

                finish();

            }
        });


        email=(EditText)findViewById(R.id.emailEditText);
        pass=(EditText)findViewById(R.id.passwordEditText);
        signbtn=(Button)findViewById(R.id.signin);
        signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String emailid=email.getText().toString();
                String password=pass.getText().toString();
                if(emailid.equals("") || password.equals(""))
                    Toast.makeText(LoginActivity.this,"Fields Must Not Be Blank",Toast.LENGTH_SHORT).show();
                else if(!Utility.isNotNull(emailid)  ){
                   // Toast.makeText(LoginActivity.this, "Fields Have Only Spaces ", Toast.LENGTH_SHORT).show();
                    email.setError("Field have only Spaces");
                }
                else if(!Utility.isNotNull(password)){
                    pass.setError("Field have only Spaces");
                }

                else if(!Utility.validateEmail(emailid)){
                    //Toast.makeText(LoginActivity.this, "Wrong Email Format", Toast.LENGTH_SHORT).show();
                     email.setError("Wrong Format");
                }

                else

                                   volleyCall(emailid, password);

            }
        });

    }

    void goForgetPassActivity(View view)
    {
        Intent intent=new Intent(LoginActivity.this,ForgotPasswordActivity.class);
        startActivity(intent);

    }




    private void volleyCall(final String email,String password)
    {

        RequestQueue queue= Volley.newRequestQueue(this);
        Map<String,String> jsonParams=new HashMap<String,String>();
        final ProgressDialog progressDialog=new ProgressDialog(LoginActivity.this);;
        progressDialog.setMessage("Please wait..!!");
        progressDialog.setTitle("Logging In....");
        progressDialog.show();
        progressDialog.setMax(200);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        final AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);



        jsonParams.put("email",email);

        jsonParams.put("password",password);
        Log.d(TAG,"Json:"+new JSONObject(jsonParams));
        JsonObjectRequest postRequest=new JsonObjectRequest(Request.Method.POST, WebServiceURL.LOGIN_URL, new JSONObject(jsonParams), new Response.Listener<JSONObject>() {
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

                if (msg.equals("Logged In Successfully")) {


                    Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                    User user=new User(LoginActivity.this);
                    user.setEmail(email);
                    Log.d(TAG,"email: "+email);
                    Intent intent=new Intent(LoginActivity.this,CheckOutActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("pgdata",myPg);

                    intent.putExtra("bundle",bundle);
                    startActivity(intent);
                    finish();

                }
                else if(msg.equals("User Not Verified"))

                    Toast.makeText(LoginActivity.this, "User Not Verified", Toast.LENGTH_SHORT).show();
                else
                 Toast.makeText(LoginActivity.this, "Invalid User", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Json" + response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG,"ERROR:"+error+ "\nmessage" + error.getMessage());
                    }
                });


        queue.add(postRequest);
    }



    private void displayMessage(Profile profile) {
        if (profile != null) {

              FBuser fBuser=new FBuser(LoginActivity.this);
            String email=profile.getId();
            fBuser.setEmail(email);
              Log.d("email", fBuser.getEmail());
//            Intent intent = new Intent(LoginActivity.this, Home.class);
//            intent.putExtra("name",profile.getName());
//            intent.putExtra("url",profile.getProfilePictureUri(150,150).toString());
//            Log.d("TAG",profile.getName()) ;
//            startActivity(intent);
//            finish();
            skipTextView.setText(profile.getName());
        }

    }
    @Override
    public void onStop () {
        super.onStop();
        // accessTokenTracker.stopTracking();
        //  profileTracker.stopTracking();
    }

    @Override
    public void onResume () {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();

        displayMessage(profile);
        //String url = profile.getProfilePictureUri(150, 150).toString();

        //displayImage(url);
    }

}







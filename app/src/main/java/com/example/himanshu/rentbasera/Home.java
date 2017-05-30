package com.example.himanshu.rentbasera;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.example.himanshu.rentbasera.services.WebService;
import com.example.himanshu.rentbasera.url.WebServiceURL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private List<PG> pgList = new ArrayList<>();
    EditText searchEditText;
    Button searchButton;
    private static final String TAG="event";
      User user;
    static int counter=0;
    String[] language ={"noida","delhi","gurugram"};
    ArrayAdapter<String> adapter;
    AutoCompleteTextView selectCity;
    FragmentManager fragmentManager=getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
//BACK BUTTON PRESSING


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
         adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,language);
        selectCity=  (AutoCompleteTextView) findViewById(R.id.cityAutoComplete);
        selectCity.setThreshold(1);//will start working from first character
        selectCity .setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        selectCity.setTextColor(Color.BLACK);


       // searchEditText=(EditText)findViewById(R.id.searchEditText);
         searchButton=(Button)findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectCity.getText().toString().equals(""))
                {
                    Toast.makeText(Home.this, "Enter A Location", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    volleyForPGList(selectCity.getText().toString());
                }
            }
        });

      //  Bundle bundle=getIntent().getExtras();
//        name=bundle.getString("name");
//        url=bundle.getString("url");
//        Log.d("TAG",name) ;
      //  Toast.makeText(this, name+" "+url, Toast.LENGTH_SHORT).show();
          //FBuser fBuser=new FBuser(Home.this);


        //    View view = navigationView.inflateHeaderView(R.layout.nav_header_home);


          //  final ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            //TextView textView = (TextView) view.findViewById(R.id.navhaeadertextview);
//            textView.setText(name);
//
//
//            Glide.with(Home.this).load(url).asBitmap().into(new BitmapImageViewTarget(imageView) {
//                @Override
//                protected void setResource(Bitmap resource) {
//                    RoundedBitmapDrawable circularBitmapDrawable =
//                            RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
//                    circularBitmapDrawable.setCircular(true);
//                    imageView.setImageDrawable(circularBitmapDrawable);
//
//                    // imageView.setImageIcon(getApplicationContext().getResources(), resource);
//
//
//                }
//            });
//
//





//        user=new User(Home.this);
//        if(user.getEmail() != "")
//        {
//
//        }



    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click Back again to exit", Toast.LENGTH_SHORT).show();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }

        },2000);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if(id==R.id.logout_setting)
        {
            User user=new User(Home.this);

             if(user.getEmail() != "")
              user.removeUser();
            //new User(Home.this).removeUser();
            Intent intent=new Intent(Home.this,Home.class);
            Toast.makeText(this, "Logged Out", Toast.LENGTH_LONG).show();
            startActivity(intent);

            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
                if (id == R.id.content_home) {
//             HomeFragment homeFragment=new HomeFragment();
//             fragmentTransaction.replace(android.R.id.content, homeFragment);
//             fragmentTransaction.addToBackStack(null);
//             fragmentTransaction.commit();

        } else if (id == R.id.contactus) {
//           Fragment  homeFragment=new HomeFragment();
//             fragmentTransaction.replace(android.R.id.content, homeFragment);
//
//             fragmentTransaction.addToBackStack(null);
//
//             fragmentTransaction.commit();

        } else if (id == R.id.Profile) {
              User user=new User(Home.this);
                    if(user.getEmail() != "")
                    {
                        WebService.getUserData(user.getEmail(),getApplicationContext());
                    }
                    else
                    {
                        Toast.makeText(this, "You Are Not Logged In", Toast.LENGTH_SHORT).show();
                    }
        } else if (id == R.id.policies) {

        } else if (id == R.id.feedback)
                {
                   Intent intent=new Intent(getApplicationContext(),FeedbackActivity.class);
                    startActivity(intent);
                    finish();
                }

             DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

  public void volleyForPGList(String location)
    {

        RequestQueue queue= Volley.newRequestQueue(this);
        Map<String,String> jsonParams=new HashMap<String,String>();
        jsonParams.put("location",location);
        final ProgressDialog progressDialog=new ProgressDialog(Home.this);
        progressDialog.setMessage("Please wait..!!");
        progressDialog.setTitle("Getting PG List");
        progressDialog.show();
        progressDialog.setMax(200);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        final AlertDialog.Builder builder=new AlertDialog.Builder(Home.this);



        Log.d(TAG,"Json:"+new JSONObject(jsonParams));
        JsonObjectRequest postRequest=new JsonObjectRequest(Request.Method.POST, WebServiceURL.PG_DETAILS_URL, new JSONObject(jsonParams), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String msg = null;
                try {

                         progressDialog.dismiss();
                   // msg = (String)response.get("location");
                    Log.d(TAG," "+msg);

                   JSONArray jsonArray= response.getJSONArray("location");
                    Log.d(TAG,jsonArray.toString());
                    pgList.clear();
                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int pgid = jsonObject.getInt("pgid");
                        String imageurl = jsonObject.get("imageurl").toString();
                        int no_of_rooms = jsonObject.getInt("no_of_rooms");
                        String location = jsonObject.get("location").toString();
                        String pgname = jsonObject.get("pgname").toString();
                        int no_of_beds=jsonObject.getInt("no_of_beds");
                        String available_for=jsonObject.get("available_for").toString();
                        int amount=jsonObject.getInt("payment_amount");
                        String pgArea=jsonObject.get("pgarea").toString();
                        PG mypg = new PG(pgid, pgname, no_of_rooms, location, imageurl,no_of_beds,available_for,amount,pgArea);
                        pgList.add(mypg);
                       // Log.d("pglistttttt",pgList.toString());
                    }
                        Intent intent=new Intent(getApplicationContext(),AvailablePGList.class);
                         Bundle bundle=new Bundle();
                          bundle.putSerializable("pgList",(Serializable)pgList);

                         intent.putExtra("bundle",bundle);
                         startActivity(intent);


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


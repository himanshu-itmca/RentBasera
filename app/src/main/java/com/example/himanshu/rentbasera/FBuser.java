package com.example.himanshu.rentbasera;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by himanshu on 5/7/2017.
 */

public class FBuser {

    String name;

    public String getEmail() {
        email=sharedPreferences.getString("email","");
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        sharedPreferences.edit().putString("email",email).commit();
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String email;
    String imageurl;
    Context context;
    SharedPreferences sharedPreferences;

    public FBuser(Context context)
    {
        this.context=context;
        sharedPreferences=context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);

    }

    public void removeFBuser()
    {
        sharedPreferences.edit().clear().commit();

    }


}

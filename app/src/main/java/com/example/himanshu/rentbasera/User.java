package com.example.himanshu.rentbasera;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by himanshu on 4/18/2017.
 */

public class User
{
     Context context;
    SharedPreferences sharedPreferences;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    String name;


    public String getEmail() {
        email=sharedPreferences.getString("email","");
        return email;

    }

    public void setEmail(String email) {
        this.email = email;
        sharedPreferences.edit().putString("email",email).commit();
    }

    String email;

    public User(Context context)
    {
       this.context=context;
        sharedPreferences=context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);

    }

    public void removeUser()
    {
        sharedPreferences.edit().clear().commit();

    }

}

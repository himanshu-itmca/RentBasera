package com.example.himanshu.rentbasera;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by SI-Andriod on 6/9/2015.
 */
public class Utility {

    private static Pattern pattern;
    private static Matcher matcher;
    //Email Pattern
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Validate Email with regular expression
     *
     * @param email
     * @return true for Valid Email and false for Invalid Email
     */

    public static boolean validateEmail(String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

    }

    /**
     * Checks for Null String object
     *
     * @param txt
     * @return true for not null and false for null String object
     */
    public static boolean isNotNull(String txt) {
        return txt != null && txt.trim().length() > 0 ? true : false;
    }

    // validating password with retype password
    public static boolean validatePassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }


    public static boolean UserNameHaveSpace(String userName) {

        boolean checkSpace=false;
        int f=0;
        for(int i=0;i<userName.length();i++){
            if(userName.contains(" ") ){
                f=1;
                checkSpace= true;
            }
        }
        if(f==1){
            return checkSpace;

        }else{
            return checkSpace;
        }


    }
    public static boolean validateMaxMobile(String mobile) {
        if (mobile != null && mobile.length() > 10) {
            return true;
        }
        return false;
    }

    public static boolean validateMinMobile(String mobile) {
        if (mobile != null && mobile.length() < 10) {
            return true;
        }
        return false;
    }

    public static boolean isNetworkAvailable(Context context){
        final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isConnectedOrConnecting() || mobile.isConnectedOrConnecting()) {
            Log.d("Network", "Available");
            return true;
        } else {
            Log.d("Network", "Not Available");
            return false;
        }
    }

}

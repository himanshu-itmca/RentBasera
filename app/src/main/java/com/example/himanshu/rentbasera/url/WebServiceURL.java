package com.example.himanshu.rentbasera.url;

/**
 * Created by himanshu on 5/19/2017.
 */

public class WebServiceURL
{
      public final static String IP ="139.59.78.124:8080";
    //public final static String IP ="192.168.43.177:8010";
    public final static String LOGIN_URL="http://"+IP+"/RestServiceRegisterLogin/rest/user/login";
    public final  static String REGISTRATION_URL="http://"+IP+"/RestServiceRegisterLogin/rest/user/register";
    public final  static String EMAIL_VERIFICATION_URL="http://"+IP+"/RestServiceRegisterLogin/rest/user/SetUserStatus";
    public final  static String PASSWORD_RESET_URL="http://"+IP+"/RestServiceRegisterLogin/rest/user/PasswordResetOTP";
    public final  static String PG_DETAILS_URL="http://"+IP+"/RestServiceRegisterLogin/rest/pgdetails";
    public final  static String PASSWORD_RESET="http://"+IP+"/RestServiceRegisterLogin/rest/user/PasswordReset";
    public final  static String  BOOKING_URL="http://"+IP+"/RestServiceRegisterLogin/rest/booking";
    public final  static String  GET_USER_DATA="http://"+IP+"/RestServiceRegisterLogin/rest/userprofile/getuserdata";
    public final static String FEEDBACK_URL="http://"+IP+"/RestServiceRegisterLogin/rest/feedback";
}

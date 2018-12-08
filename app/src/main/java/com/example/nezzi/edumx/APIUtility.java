package com.example.nezzi.edumx;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;

public class APIUtility {

    public static final String SERVER_URL = "http://23ec6142.ngrok.io";
    public static final String CAT_URL = SERVER_URL + "/api/categories";
    public static final String COURSE_CAT_URL = SERVER_URL + "/api/category/";
    public static final String MY_COURSES_URL = SERVER_URL + "/api/my/courses/";

    public static final String COURSE_URL = SERVER_URL + "/api/courses/";


    public static final String LOGIN_URL = SERVER_URL + "/api/login/";
    public static final String LOGOUT_URL = SERVER_URL + "/api/logout/";
    public static final String USER_URL = SERVER_URL + "/api/user/";

    public static final String PAYPAL_TOKEN_URL = SERVER_URL + "/api/paypal/client_token/";
    public static final String PAYPAL_CHECKOUT_URL = SERVER_URL + "/api/paypal/checkout/";

    private static String ACCESS_TOKEN = null;

    public static String clientToken = null;

    public static void setAccesToken(String token, Activity activity) {
        ACCESS_TOKEN = token;

        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("access_token", token);
        editor.apply();
    }

    public static String getAccesToken(Activity activity) {

        if (ACCESS_TOKEN == null || ACCESS_TOKEN.isEmpty()) {
            SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
            ACCESS_TOKEN = sharedPref.getString("access_token", "");return ACCESS_TOKEN;
        }

        return ACCESS_TOKEN;
    }

    public static void removeAccesToken(Activity activity) {
        ACCESS_TOKEN = null;

        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("access_token");
        editor.apply();
    }
}

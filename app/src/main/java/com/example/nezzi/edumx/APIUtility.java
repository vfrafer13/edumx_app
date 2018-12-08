package com.example.nezzi.edumx;

public class APIUtility {

    public static final String SERVER_URL = "http://23ec6142.ngrok.io";
    public static final String CAT_URL = SERVER_URL + "/api/categories";
    public static final String COURSE_CAT_URL = SERVER_URL + "/api/category/";
    public static final String MY_COURSES_URL = SERVER_URL + "/api/my/courses/";

    public static final String COURSE_URL = SERVER_URL + "/api/courses/";


    public static final String LOGIN_URL = SERVER_URL + "/api/login/";

    public static final String PAYPAL_TOKEN_URL = SERVER_URL + "/api/paypal/client_token/";
    public static final String PAYPAL_CHECKOUT_URL = SERVER_URL + "/api/paypal/checkout/";

    public static String ACCESS_TOKEN = null;

    public static String clientToken = null;
}

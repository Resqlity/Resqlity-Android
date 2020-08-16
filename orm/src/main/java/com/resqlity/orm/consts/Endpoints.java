package com.resqlity.orm.consts;

import android.os.Build;

import com.resqlity.orm.BuildConfig;

/**
 * Resqlity API Endpoints
 */
public class Endpoints {

    public static final String INSERT_URL = BuildConfig.DEBUG ? "http://10.0.2.2:55391/api/Das/InsertData" : "https://api.resqlity.com/api/Das/InsertData";
    public static final String SELECT_URL = BuildConfig.DEBUG ? "http://10.0.2.2:55391/api/Das/FetchData" : "https://api.resqlity.com/api/Das/FetchData";
    public static final String DELETE_URL = BuildConfig.DEBUG ? "http://10.0.2.2:55391/api/Das/Delete" : "https://api.resqlity.com/api/Das/Delete";
    public static final String UPDATE_URL = BuildConfig.DEBUG ? "https://10.0.2.2:55391/api/Das/Update" : "https://api.resqlity.com/api/Das/Update";
    public static final String WEB_SOCKET_CLIENT_URL = BuildConfig.DEBUG ? "ws://10.0.2.2:55391/realtime?apiKey=" : "wss://api.resqlity.com/realtime?apiKey=";

}

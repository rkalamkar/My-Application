package com.rahul.myapplication.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rahul.myapplication.Model.Response;
import com.rahul.myapplication.Model.UserData;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class GeneralUtils {
    public static String epochTodte(String str) {
        long time = Long.valueOf(str);
        String format = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date(time * 1000));
    }

    public static String getBaseUrl() {
        String str = "https://randomuser.me/api/0.4/?randomapi";
        String baseUrl = "";
        try {
            URL url = URI.create(str).toURL();
            baseUrl = url.getProtocol() + "://" + url.getAuthority() + "/";
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return baseUrl;
    }

    public static String getCapitalized(String str) {
        StringBuilder sb = new StringBuilder(str);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        return sb.toString();
    }

    public static void addFav(Context context, ArrayList<UserData> list) {
        clearList(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyFavList", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("user", json);
        editor.commit();
    }

    public static void clearList(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyFavList", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public static ArrayList<UserData> getFavList(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyFavList", 0);
        String json = sharedPreferences.getString("user", null);
        Type type = new TypeToken<ArrayList<UserData>>() {
        }.getType();
        Gson gson = new Gson();
        ArrayList<UserData> arrayList = new ArrayList<>();
        arrayList = gson.fromJson(json, type);
        return arrayList;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}

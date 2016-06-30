package com.multicampus.anddbsample.restIF;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by student on 2016-06-28.
 */
public class RemoteFetch {
    private static final String OPEN_MY_BEAR_API = "http://localhost:8080/api/bears/57721c41aa42685017000001";

    public static JSONObject getJSON(Context context, String name){
        try{
            URL url = new URL(OPEN_MY_BEAR_API);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer json = new StringBuffer(1024);
            String tmp = "";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();
            JSONObject data = new JSONObject(json.toString());

            Toast.makeText(context, data.getString("name"), Toast.LENGTH_LONG).show();

            return data;
        }catch(Exception e){
            return null;
        }
    }
}

package com.multicampus.anddbsample.restIF;

import android.os.AsyncTask;
import android.util.Log;

import com.multicampus.anddbsample.vo.Bear;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * AsyncTask를 이용하여 REST POST콜을 통해 JSON을 입력하는 클래스.
 */
public class PostBear {

    private Bear bear;

    public void postBear(Bear bear){
        this.bear = bear;
        new PostTask().execute("http://70.12.108.133:8080/api/bears");
    }

    // AsyncTask를 inner class로 구현
    private class PostTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            try{
                return POST(params[0]);
            }catch (IOException e){
                return "Unable to retreive data. URL may be invalid.";
            }
        }
    }

    private String POST(String myurl) throws IOException{
        InputStream inputStream = null;
        String returnString = "";

        JSONObject json = new JSONObject();

        int length = 500;

        try{
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.connect();

            json.accumulate(Bear.NAME, bear.getName());

            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(json.toString());
            writer.flush();
            writer.close();

            int response = conn.getResponseCode();
            Log.d("REST POST", "The response is : " + response);
        }catch(Exception e){
            Log.e("REST POST", "Error : " + e.getMessage());
        }finally{
            if(inputStream != null)
                inputStream.close();
        }
        return returnString;
    }
}

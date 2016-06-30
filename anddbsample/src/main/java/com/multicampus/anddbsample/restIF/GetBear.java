package com.multicampus.anddbsample.restIF;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by student on 2016-06-29.
 */
public class GetBear {

    public GetBear() {

    }

    public void getBear(String id){
        new GetTask().execute("http://70.12.108.133:8080/api/bears/"+id);
    }

    private class GetTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try{
                return GET(params[0]);
            }catch (IOException e){
                return "Unable to retreive data. URL may be invalid.";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    private String GET(String myurl) throws IOException{
        InputStream inputStream = null;
        String returnString = "";

        int length = 500;


        try{
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("REST GET", "The response is : " + response);
            inputStream = conn.getInputStream();


            // convert inputStream into json
            returnString = convertInputStreamToString(inputStream, length);

            JSONObject json = new JSONObject(returnString);

            Log.d("REST GET", "The response is : " + json.toString());
        }catch (Exception e){
            Log.e("REST GET", "Error : "+e.getMessage());
        }finally {
            if(inputStream != null)
                inputStream.close();
        }
        return returnString;
    }

    public String convertInputStreamToString(InputStream stream, int length) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[length];
        reader.read(buffer);
        return new String(buffer);
    }
}

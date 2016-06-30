package com.multicampus.anddbsample.restIF;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.multicampus.anddbsample.R;
import com.multicampus.anddbsample.vo.Contact;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by student on 2016-06-30.
 */
public class ContactInterface {

    public static final String REST_METHOD_GET = "GET";
    public static final String REST_METHOD_POST = "POST";
    public static final String REST_METHOD_DELETE = "DELETE";
    public static final String REST_METHOD_PUT = "PUT";

    public static final String REST_API_URL = "http://70.12.108.133:8080/api/contact";

    Context context;
    Contact contact;

    public ContactInterface(Context context){
        this.context = context;
    }

    public void getContact(){
        new GetTask().execute(REST_API_URL);
    }

    public void getContact(String id){

    }

    public void deleteContact(String id){

    }

    public void updateContact(Contact contact){

    }

    public void postContact(Contact contact){
        this.contact = contact;
        new PostTask().execute(REST_API_URL);
    }

    private class PostTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            try{
                return POST(params[0]);
            }catch (IOException e){
                return "Unable to POST. URL may be invalid.";
            }
        }
    }


    private class GetTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            try{
                return GET(params[0]);
            }catch (IOException e){
                return "Unable to retreive data. URL may be invalid.";
            }
        }
    }

    private String POST(String myurl) throws IOException{
        InputStream inputStream = null;
        String returnString = "";

        JSONObject json = new JSONObject();

        try{
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(R.integer.httpTimeOut);
            conn.setConnectTimeout(R.integer.httpTimeOut);
            conn.setRequestMethod(REST_METHOD_POST);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.connect();

            json.accumulate(Contact.KEY_NAME, contact.getName());
            json.accumulate(Contact.KEY_ID, contact.getId());
            json.accumulate(Contact.KEY_ADDRESS, contact.getAddress());
            json.accumulate(Contact.KEY_DESC, contact.getDesc());
            json.accumulate(Contact.KEY_TELNUM, contact.getTelNum());

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

    private String GET(String myurl) throws IOException{
        InputStream is = null;
        String returnString = "";
        int length = 500;

        try{
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(R.integer.httpTimeOut);
            conn.setConnectTimeout(R.integer.httpTimeOut);
            conn.setRequestMethod(REST_METHOD_GET);
            conn.setDoInput(true);
            conn.connect();

            int response = conn.getResponseCode();
            is = conn.getInputStream();

            returnString = convertInputStreamToString(is, length);
            JSONObject json = new JSONObject(returnString);

        }catch (Exception e){
            Log.e("REST GET", "Error : "+e.getMessage());
        }finally {
            if(is != null)
                is.close();
        }
        return returnString;
    }

    public String convertInputStreamToString(InputStream stream, int length) throws IOException, UnsupportedEncodingException {
        Reader reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[length];
        reader.read(buffer);
        return new String(buffer);
    }
}

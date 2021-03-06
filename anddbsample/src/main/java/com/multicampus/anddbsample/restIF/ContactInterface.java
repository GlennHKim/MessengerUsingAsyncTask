package com.multicampus.anddbsample.restIF;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.multicampus.anddbsample.R;
import com.multicampus.anddbsample.activities.ListActivity;
import com.multicampus.anddbsample.activities.LoginActivity;
import com.multicampus.anddbsample.activities.MainActivity;
import com.multicampus.anddbsample.vo.Contact;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by student on 2016-06-30.
 */
public class ContactInterface {

    public static final String REST_METHOD_GET = "GET";
    public static final String REST_METHOD_POST = "POST";
    public static final String REST_METHOD_DELETE = "DELETE";
    public static final String REST_METHOD_PUT = "PUT";
    public static final String REST_API_URL = "http://70.12.108.133:8080/api/contact";

    private static final int NEXT_ACTION_LOGIN = 1;
    private static final int NEXT_ACTION_FIND = 2;
    private static final int NEXT_ACTION_UPDATE = 3;
    private static final int NEXT_ACTION_JOIN = 4;


    ArrayList<Contact> contactList;

    Context context;
    Contact contact;

    int nextStep;

    public ContactInterface(Context context){
        contactList = new ArrayList<>();
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

    public void getContactList(){
        new GetContacts().execute(REST_API_URL);
    }

    public void Join(Contact contact){
        this.contact = contact;
        nextStep = NEXT_ACTION_JOIN;
        new PostTask().execute(REST_API_URL);
    }

    public void Login(Contact contact){
        this.contact = contact;
        nextStep = NEXT_ACTION_LOGIN;
        new GetTask().execute(REST_API_URL + "/" + contact.getId());
    }

    private class GetContacts extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            try{
                Log.d("REST GetContacts URL", params[0]);
                return GETContacts(params[0]);
            }catch (IOException e){
                return "Unable to retreive data. URL may be invalid.";
            }
        }
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
                Log.d("REST GET URL", params[0]);
                return GET(params[0]);
            }catch (IOException e){
                return "Unable to retreive data. URL may be invalid.";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO: 2016-06-30 성공 시 할 작업
            Log.d("REST POST", "onPostExecute : " + result);
            switch(nextStep){
                case NEXT_ACTION_FIND:
                    break;
                case NEXT_ACTION_JOIN:
                    MainActivity mainActivity = (MainActivity) context;
                    if(result == "success")
                        mainActivity.joinSuccess();
                    else
                        mainActivity.joinFailure();
                    break;
                case NEXT_ACTION_LOGIN:
                    LoginActivity loginActivity = (LoginActivity) context;
                    if(result == "success")
                        loginActivity.loginSuccess(contact);
                    else
                        loginActivity.loginFailure();
                    break;
                case NEXT_ACTION_UPDATE:
                    break;
            }

            // show result
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

            if(response == 200){
                returnString = "success";
            }
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
        int length = 100000;

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

            Log.d("REST POST", "The response is : " + response);
            Log.d("REST POST", "Data is : " + json.toString());

            if(response == 200){
                this.contact = new Contact(json.getString(Contact.KEY_ID), json.getString(Contact.KEY_NAME), json.getString(Contact.KEY_TELNUM), json.getString(Contact.KEY_DESC), json.getString(Contact.KEY_ADDRESS));
                returnString = "success";
            }

        }catch (Exception e){
            Log.e("REST GET", "Error : "+e.getMessage());
        }finally {
            if(is != null)
                is.close();
        }
        return returnString;
    }

    private String GETContacts(String myurl) throws IOException{
        InputStream is = null;
        String returnString = "";
        int length = 100000;

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

            JSONArray jsonArray = new JSONArray(returnString);

            contactList.clear();

            int jsonlength = jsonArray.length();

            for( int i=0; i<jsonlength; i++){
                JSONObject j = jsonArray.getJSONObject(i);
                contactList.add(new Contact(
                        j.getString(Contact.KEY_ID),
                        j.getString(Contact.KEY_NAME),
                        j.getString(Contact.KEY_TELNUM),
                        j.getString(Contact.KEY_DESC),
                        j.getString(Contact.KEY_ADDRESS)
                ));
            }

            Log.d("REST POST", "The response is : " + response);

            if(response == 200){
                ListActivity activity = (ListActivity)context;
                activity.getSuccss(contactList);
                returnString = "success";
            }

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

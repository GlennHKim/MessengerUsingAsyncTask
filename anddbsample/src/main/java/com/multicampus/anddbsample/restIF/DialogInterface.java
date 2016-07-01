package com.multicampus.anddbsample.restIF;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.multicampus.anddbsample.R;
import com.multicampus.anddbsample.activities.DialogActivity;
import com.multicampus.anddbsample.activities.ListActivity;
import com.multicampus.anddbsample.vo.Contact;
import com.multicampus.anddbsample.vo.Dialog;

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
public class DialogInterface {

    public static final String REST_METHOD_GET = "GET";
    public static final String REST_METHOD_POST = "POST";
    public static final String REST_API_URL = "http://70.12.108.133:8080/api/dialog/";

    private static final int NEXT_ACTION_DIALOG = 1;
    private static final int NEXT_ACTION_LIST = 2;

    Context context;
    Contact contact;
    Contact another;
    Dialog dialog;

    ArrayList<Dialog> dialogList;

    int nextStep;

    public DialogInterface(Context context, Contact contact){
        this.context = context;
        this.contact = contact;
        dialogList = new ArrayList<>();
    }

    public void getDialogs(Contact another){
        this.another = another;
        nextStep = NEXT_ACTION_DIALOG;
        new GetTask().execute(REST_API_URL + contact.getId() + "/" + another.getId());
    }

    public void sendDialog(Dialog dialog){
        this.dialog = dialog;
        new PostTask().execute(REST_API_URL + contact.getId());
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
                case NEXT_ACTION_LIST:
                    ListActivity listActivity = (ListActivity) context;
                    if(result == "success")
                        listActivity.sendSuccess();
                    else
                        listActivity.sendFailure();
                    break;
                case NEXT_ACTION_DIALOG:
                    DialogActivity dialogActivity = (DialogActivity) context;
                    if(result == "success")
                        dialogActivity.getSuccess(dialogList);
                    else
                        dialogActivity.sendFailure();
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

            json.accumulate(Dialog.KEY_SENDER, dialog.getSender());
            json.accumulate(Dialog.KEY_RECEIVER, dialog.getReceiver());
            json.accumulate(Dialog.KEY_TEXT, dialog.getText());
            json.accumulate(Dialog.KEY_TIME, dialog.getTime());

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
        int length = 10000;

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

            dialogList.clear();

            int jsonlength = jsonArray.length();

            for( int i=0; i<jsonlength; i++){
                JSONObject j = jsonArray.getJSONObject(i);
                dialogList.add(new Dialog(
                        j.getString(Dialog.KEY_SENDER),
                        j.getString(Dialog.KEY_RECEIVER),
                        j.getString(Dialog.KEY_TEXT),
                        j.getString(Dialog.KEY_TIME)
                ));
            }


            Log.d("REST POST", "The response is : " + response);

            if(response == 200){
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

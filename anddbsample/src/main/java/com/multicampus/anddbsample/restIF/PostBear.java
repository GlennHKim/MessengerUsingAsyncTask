package com.multicampus.anddbsample.restIF;

import android.os.AsyncTask;
import android.util.Log;

import com.multicampus.anddbsample.vo.Bear;

import org.json.JSONObject;

/**
 * Created by student on 2016-06-29.
 */
public class PostBear {
    public PostBear(){

    }

    public void postBear(Bear bear){
        JSONObject json = new JSONObject();
        try{
            json.accumulate(Bear.NAME, bear.getName());
            // post
        }catch (Exception e){
            Log.d("REST POST", ": Failed to make json data - " + e.getMessage());
        }

    }

        private class PostTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            return null;
        }
    }
}

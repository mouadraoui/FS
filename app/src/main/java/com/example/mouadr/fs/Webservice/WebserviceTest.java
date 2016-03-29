package com.example.mouadr.fs.Webservice;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.loopj.android.http.*;
import com.example.mouadr.fs.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by mouadr on 26/03/2016.
 */
public class WebserviceTest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://192.168.44.50:8080/mouad?test=raoui",null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONObject j=response.getJSONObject(0);
                  Toast.makeText(getApplicationContext(), j.getString("name"),Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
               // String test= response.optString(Integer.parseInt("name"));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

    }
}

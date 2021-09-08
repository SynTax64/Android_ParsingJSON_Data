package com.example.parsedata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    String url = "https://jsonplaceholder.typicode.com/todos";
    String getApiurl = "https://jsonplaceholder.typicode.com/todos/1";
    //    RequestQueue queue;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        queue = Volley.newRequestQueue(this);
//        JsonArrayRequest jsonArrayRequest = getJsonArrayRequest();
//        queue.add(jsonArrayRequest);
        queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                getApiurl, null, response -> {
            try {
                Log.d("jsonObj", "onCreate: " + response.getInt("userId") + ", "
                        + response.getInt("id") + ", "
                        + response.getString("title") + ", "
                        + response.getBoolean("completed") + "\n");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Log.d("jsonObj", "onCreate: Failed!");
        });

//        getString(queue);

//        queue.add(jsonObjectRequest);

        queue.add(jsonObjectRequest);
    }

    @NonNull
    private JsonArrayRequest getJsonArrayRequest() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject jsonObject = response.getJSONObject(i);
                    Log.d("JSON", "onCreate: " + jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
            Log.d("JSON", "onCreate: Failed");
        });
        return jsonArrayRequest;
    }

    private void getString(RequestQueue queue) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            Log.d("Main", "onCreate: " + response.substring(0, 500));
        },
                error -> {
                    Log.d("Main", "Failed to get info! ");
                });
        queue.add(stringRequest);
    }
}
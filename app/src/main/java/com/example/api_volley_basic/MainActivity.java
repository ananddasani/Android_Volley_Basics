package com.example.api_volley_basic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button button;
    LinearLayout linearLayout;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        linearLayout = findViewById(R.id.linearLayout);

        //whenever you want to fetch data from the API you create a request queue
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //url of API
                String url = "https://mocki.io/v1/db6249d4-0ee1-4bae-a948-82d92c7d1ebc";

                //if you see it is first object then array so...
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            //we want array (from the object) so...
                            JSONArray jsonArray = response.getJSONArray("student");

                            //now we have 3 items in array students[] (just we got)
                            for (int i = 0; i < jsonArray.length(); i++) {

                                //we are having objects (name, email, age) so...
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String name = jsonObject.getString("name");
                                String email = jsonObject.getString("email");
                                int age = jsonObject.getInt("age");

                                //Just Appending the data to already existing TextView
                                textView.append(name + " " + email + " " + String.valueOf(age) + "\n");

                                //Creating TextView and Adding in the Layout
                                TextView textView2 = new TextView(MainActivity.this);
                                textView2.setText(name + " " + email + " " + String.valueOf(age) + "\n");
                                linearLayout.addView(textView2);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                //add this request to request queue
                requestQueue.add(jsonObjectRequest);
            }
        });
    }
}

/*
If Multiple Request to do then put in request queue
and queue will see them one by one
 */
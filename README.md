# Android_Volley_Basics
Using Volley Library to fetch data from the API

This topic is a part of [My Complete Andorid Course](https://github.com/ananddasani/Android_Apps)

# Dependency
```
implementation 'com.android.volley:volley:1.2.1'
```

- If String Request
```
implementation 'com.google.code.gson:gson:2.8.9'
```

# Code

#### 1st Activity 
```
TextView textView;
Button button;
LinearLayout linearLayout;

RequestQueue requestQueue;

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
```

# App Highlight
![Volley API Basic App](https://user-images.githubusercontent.com/74413402/192093519-8204a30f-1e44-4eed-8187-a3efb49c747d.png)
![Volley API Basic Code](https://user-images.githubusercontent.com/74413402/192093521-f440ebf5-4237-4a42-9870-aafaab628040.png)


package com.nurde.getjson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String id,name,email;
    private RecyclerView recyclerView;
    private UsersAdapter adapter;
    private ArrayList<Users> usersArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usersArrayList = new ArrayList<>();

            try {
                //get JSONObject from JSON file
                JSONObject obj = new JSONObject(loadJSONSFromAsset());

                //fetch JSONArray named users
                JSONArray userArray = obj.getJSONArray("users");
                for (int i = 0; i < userArray.length(); i++) {
                    //create a JSONObject for  fetching single user data
                    JSONObject userDetail = userArray.getJSONObject(i);

                    id = userDetail.getString("id");
                    name = userDetail.getString("name");
                    email = userDetail.getString("email");

                    usersArrayList.add(new Users(id,name,email));
                }
        } catch(JSONException e){
            e.printStackTrace();
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        adapter = new UsersAdapter(usersArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }
    public String loadJSONSFromAsset(){
        String json = null;
        try {
            InputStream is = getAssets().open("users.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer,"UTF-8");
        } catch (IOException ex){
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}

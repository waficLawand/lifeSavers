package com.lifeSavers.lifeSavers;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class UserInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final MapFragment map = new MapFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction FT = fm.beginTransaction();
        FT.add(R.id.userMapLayout,map);
        FT.commit();
        TextView textView = (TextView)findViewById(R.id.fullName);
        Intent i = new Intent(UserInfo.this,Dashboard.class);
        i.putExtra("userInfo",getIntent().getStringExtra("userData"));
        try {
            JSONObject data = new JSONObject(getIntent().getStringExtra("userInfo"));
            textView.setText(data.getString("fullName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

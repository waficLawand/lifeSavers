package com.lifeSavers.lifeSavers;

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
        TextView textView = (TextView)findViewById(R.id.text);
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

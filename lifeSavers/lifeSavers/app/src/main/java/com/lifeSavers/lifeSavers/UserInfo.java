package com.lifeSavers.lifeSavers;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class UserInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        TextView fullName = (TextView)findViewById(R.id.fullName);
        TextView donarEmail = (TextView) findViewById(R.id.donarEmail);
        TextView donarPhone = (TextView) findViewById(R.id.donarPhone);

        ImageView userBloodType = (ImageView) findViewById(R.id.userPhoto);





        Intent i = new Intent(UserInfo.this,Dashboard.class);
        i.putExtra("userInfo",getIntent().getStringExtra("userData"));
        try {

            JSONObject data = new JSONObject(getIntent().getStringExtra("userInfo"));

            UserInfoMap map = new UserInfoMap(data.getDouble("longitude"),data.getDouble("latitude"));
            FragmentManager fm = getFragmentManager();
            FragmentTransaction FT = fm.beginTransaction();
            FT.add(R.id.userMapLayout,map);
            FT.commit();

            String bloodType = data.getString("bloodType");
            switch(bloodType) {
                case "A+":
                    userBloodType.setImageResource(R.drawable.apositive);
                    break;
                case "B+":
                    userBloodType.setImageResource(R.drawable.bpositive);
                    break;
                case "A-":
                    userBloodType.setImageResource(R.drawable.anegative);
                    break;
                case "B-":
                    userBloodType.setImageResource(R.drawable.bnegative);
                    break;
                case "AB+":
                    userBloodType.setImageResource(R.drawable.abpositive);
                    break;
                case "AB-":
                    userBloodType.setImageResource(R.drawable.abnegative);
                    break;
                case "O+":
                    userBloodType.setImageResource(R.drawable.opositive);
                    break;
                case "O-":
                    userBloodType.setImageResource(R.drawable.onegative);
                    break;
            }


            fullName.setText(data.getString("fullName"));
            donarPhone.setText(data.getString("mobileNumber"));
            donarEmail.setText(data.getString("email"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

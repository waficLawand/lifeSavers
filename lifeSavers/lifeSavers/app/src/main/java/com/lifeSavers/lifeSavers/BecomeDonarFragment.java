package com.lifeSavers.lifeSavers;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

/**
 * Created by hassan on 1/29/2019.
 */

public class BecomeDonarFragment extends Fragment {
    DatePickerDialog.OnDateSetListener mDatePicker;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_become_donar,container,false);
        final Button becomeDonar = v.findViewById(R.id.becomeDonar);
        final Constants constants = new Constants();

        final EditText donationDate = (EditText)v.findViewById(R.id.donationDate2);
        donationDate.setFocusable(false);
        donationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal =  Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getContext(),android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDatePicker,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;


                String date = String.format("%02d",month) + "/" + String.format("%02d",day) + "/" + year;
                donationDate.setText(date);
            }
        };

        final ProgressDialog progressDialog = new ProgressDialog(getContext(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait...");


        try {
            final JSONObject userData = new JSONObject(SignupActivity.userInfo);


            final String username = userData.getString("username");
            if(userData.getBoolean("doner"))
            {
                becomeDonar.setEnabled(false);
                becomeDonar.setText("You're already a Donor!");
            }
            else
            {
                becomeDonar.setEnabled(true);
            }

            becomeDonar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Integer actualMonth = Integer.parseInt(userData.getString("lastDateOfDonation").substring(0,2));
                        Integer year = Integer.parseInt(donationDate.getText().toString().substring(6,10));

                        Integer currentMonth = Calendar.getInstance().get(Calendar.MONTH);
                        if (actualMonth-currentMonth < 4 && (year == Calendar.getInstance().get(Calendar.YEAR) ))
                        {
                            new AlertDialog.Builder(getContext())
                                    .setTitle("You Cannot Donate Blood!")
                                    .setMessage("We're sorry to inform you that you cannot be a blood donor. There should be a minimum of 4 months between your last donation and this one.")


                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Continue with delete operation
                                        }
                                    })

                                    // A null listener allows the button to dismiss the dialog and take no further action.
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                        else
                        {
                            progressDialog.show();

                            String URL = "http://"+constants.getIP()+":3000/api/becomeDonar";
                            RequestQueue MyRequestQueue = Volley.newRequestQueue(getContext());
                            StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                                    URL,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                final JSONObject json = new JSONObject(response);
                                                if(json.getBoolean("error"))
                                                {

                                                }else
                                                {
                                                    new android.os.Handler().postDelayed(
                                                            new Runnable() {
                                                                public void run() {

                                                                    progressDialog.dismiss();
                                                                    becomeDonar.setText("You're already a Donor!");
                                                                    becomeDonar.setEnabled(false);

                                                                }
                                                            }, 1000);

                                                }






                                                //Toast.makeText(LoginActivity.this,json.toString(),LENGTH_LONG).show();
                                            }  catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error)
                                {

                                }
                            }) {

                                @Override
                                public String getBodyContentType() {
                                    return "application/x-www-form-urlencoded; charset=UTF-8";
                                }

                                @Override
                                protected Map<String, String> getParams()  {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("username", username);
                                    params.put("lastDateOfDonation",donationDate.getText().toString());
                                    return params;
                                }

                            };



                            MyRequestQueue.add(jsonObjRequest);
                        }
                        //Toast.makeText(getContext(),actualMonth.toString(),LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }




                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }







        return v;
    }
}

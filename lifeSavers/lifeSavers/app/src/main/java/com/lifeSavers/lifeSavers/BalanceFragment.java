package com.lifeSavers.lifeSavers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by hassan on 1/29/2019.
 */

public class BalanceFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_balance,container,false);
        Button viewDoners = (Button) v.findViewById(R.id.doners);
        final TextView text = (TextView) v.findViewById(R.id.textView2);


        viewDoners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"fefiejifijefije",Toast.LENGTH_SHORT).show();


                RequestQueue queue = Volley.newRequestQueue(getContext());  // this = context
                Constants c = new Constants();
                final String url = "http://"+c.getIP()+":3000/api/doners";


// prepare the Request
                final JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>()
                        {
                            @Override
                            public void onResponse(JSONObject response) {
                                // display response
                                Log.d("Response", response.toString());
                                Toast.makeText(getContext(),response.toString(),Toast.LENGTH_SHORT).show();
                                text.setText(response.toString());

                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                );

// add it to the RequestQueue
                queue.add(getRequest);
            }
        });




        return v;


    }
}

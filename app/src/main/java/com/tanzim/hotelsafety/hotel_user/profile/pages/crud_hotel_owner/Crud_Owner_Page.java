package com.tanzim.hotelsafety.hotel_user.profile.pages.crud_hotel_owner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.tanzim.hotelsafety.R;
import com.tanzim.hotelsafety.app_static.Constraint;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Crud_Owner_Page extends AppCompatActivity {
    TextInputEditText hotel_owner_name, hotel_owner_address, hotel_owner_nid, hotel_owner_mobile, hotel_owner_politics;
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud__ower__page);

        hotel_owner_name = findViewById(R.id.hotel_owner_name);
        hotel_owner_address = findViewById(R.id.hotel_owner_address);
        hotel_owner_nid = findViewById(R.id.hotel_owner_nid);
        hotel_owner_mobile = findViewById(R.id.hotel_owner_mobile);
        hotel_owner_politics = findViewById(R.id.hotel_owner_politics);

        btn_submit = findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = hotel_owner_name.getText().toString();
                String address = hotel_owner_address.getText().toString();
                String nid = hotel_owner_nid.getText().toString();
                String mobile = hotel_owner_mobile.getText().toString();
                String politics = hotel_owner_politics.getText().toString();


                if(name.isEmpty()){
                    hotel_owner_name.setError(getString(R.string.required));
                    return;
                }

                if(address.isEmpty()){
                    hotel_owner_address.setError(getString(R.string.required));
                    return;
                }

                if(nid.isEmpty()){
                    hotel_owner_nid.setError(getString(R.string.required));
                    return;
                }

                if(mobile.isEmpty()){
                    hotel_owner_mobile.setError(getString(R.string.required));
                    return;
                }

                if(politics.isEmpty()){
                    hotel_owner_politics.setError(getString(R.string.required));
                    return;
                }


                submit_hotel_owner(name, address, nid, mobile, politics);
            }
        });



    }

    private void submit_hotel_owner(String name, String address, String nid, String mobile, String politics) {
        ProgressDialog dialog = new ProgressDialog(Crud_Owner_Page.this);
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Constraint.HOTEL_SET_OWNER_INFORMATION;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    hotel_owner_name.getText().clear();
                    hotel_owner_address.getText().clear();
                    hotel_owner_nid.getText().clear();
                    hotel_owner_mobile.getText().clear();
                    hotel_owner_politics.getText().clear();
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                String hotel_id = Constraint.HOTEL_ID;
                if(hotel_id.isEmpty()){
                   Toast.makeText(getApplicationContext(),"Something went to wrong!",Toast.LENGTH_SHORT).show();
                }else {

                    params.put("name", name);
                    params.put("nid", nid);
                    params.put("mobile", mobile);
                    params.put("address", address);
                    params.put("politics", politics);
                    params.put("hotel_id", hotel_id);
                    params.put("PERFORM", "OWNER_CEO_INSERT");

                    Log.e("data",name);
                    Log.e("data",nid);
                    Log.e("data",mobile);
                    Log.e("data",address);
                    Log.e("data",politics);
                }

                return params;
            }
        };

        requestQueue.add(request);


    }


}
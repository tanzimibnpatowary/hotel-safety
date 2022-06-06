package com.tanzim.hotelsafety.hotel_user.profile.pages.crud_hotel_owner;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class Edit_Owner_Info extends AppCompatActivity {
    TextInputEditText hotel_owner_name, hotel_owner_address, hotel_owner_nid, hotel_owner_mobile, hotel_owner_politics;
    Button btn_submit;
    String in_hotel_id, in_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud__ower__page);
        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setText(getString(R.string.update));


        hotel_owner_name = findViewById(R.id.hotel_owner_name);
        hotel_owner_address = findViewById(R.id.hotel_owner_address);
        hotel_owner_nid = findViewById(R.id.hotel_owner_nid);
        hotel_owner_mobile = findViewById(R.id.hotel_owner_mobile);
        hotel_owner_politics = findViewById(R.id.hotel_owner_politics);

        String in_name = getIntent().getStringExtra("name");
        String in_nid = getIntent().getStringExtra("nid");
        String in_mobile = getIntent().getStringExtra("mobile");
        String in_address = getIntent().getStringExtra("address");
        String in_politics= getIntent().getStringExtra("politics");
        in_hotel_id = getIntent().getStringExtra("hotel_id");
        in_id = getIntent().getStringExtra("id");

        hotel_owner_name.setText(in_name);
        hotel_owner_address.setText(in_address);
        hotel_owner_nid.setText(in_nid);
        hotel_owner_mobile.setText(in_mobile);
        hotel_owner_politics.setText(in_politics);




        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = hotel_owner_name.getText().toString();
                String address = hotel_owner_address.getText().toString();
                String nid = hotel_owner_nid.getText().toString();
                String mobile = hotel_owner_mobile.getText().toString();
                String politics = hotel_owner_politics.getText().toString();


                if (name.isEmpty()) {
                    hotel_owner_name.setError(getString(R.string.select_required));
                    return;
                }

                if (address.isEmpty()) {
                    hotel_owner_address.setError(getString(R.string.select_required));
                    return;
                }

                if (nid.isEmpty()) {
                    hotel_owner_nid.setError(getString(R.string.select_required));
                    return;
                }

                if (mobile.isEmpty()) {
                    hotel_owner_mobile.setError(getString(R.string.select_required));
                    return;
                }

                if (politics.isEmpty()) {
                    hotel_owner_politics.setError(getString(R.string.select_required));
                    return;
                }


                update_hotel_owner(name, address, nid, mobile, politics);
            }
        });


    }

    private void update_hotel_owner(String name, String address, String nid, String mobile, String politics) {

        ProgressDialog dialog = new ProgressDialog(Edit_Owner_Info.this);
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Constraint.HOTEL_UPDATE_OWNER_INFORMATION;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    hotel_owner_name.getText().clear();
                    hotel_owner_address.getText().clear();
                    hotel_owner_nid.getText().clear();
                    hotel_owner_mobile.getText().clear();
                    hotel_owner_politics.getText().clear();
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                //String hotel_id = Constraint.HOTEL_ID;
                if (in_hotel_id.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Something went to wrong!", Toast.LENGTH_SHORT).show();
                } else {

                    params.put("name", name);
                    params.put("nid", nid);
                    params.put("mobile", mobile);
                    params.put("address", address);
                    params.put("politics", politics);
                    params.put("hotel_id", in_hotel_id);
                    params.put("id", in_id);

                    Log.e("data", name);
                    Log.e("data", nid);
                    Log.e("data", mobile);
                    Log.e("data", address);
                    Log.e("data", politics);
                }

                return params;
            }
        };

        requestQueue.add(request);


    }
}

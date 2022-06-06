package com.tanzim.hotelsafety.hotel_user.profile.pages;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Hotel_Profile_License extends Fragment {
    TextInputEditText hotel_license_no, hotel_license_date, hotel_trade_license, hotel_TIN,
            hotel_VAT, hotel_BIN, hotel_environment_li_no, hotel_fire_li_no;
    Button btn_submit;
    View view;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_hotel__profile__license, container, false);

        hotel_license_no = view.findViewById(R.id.hotel_license_no);
        hotel_license_date = view.findViewById(R.id.hotel_license_date);
        hotel_trade_license = view.findViewById(R.id.hotel_trade_license);
        hotel_TIN = view.findViewById(R.id.hotel_TIN);
        hotel_VAT = view.findViewById(R.id.hotel_VAT);
        hotel_BIN = view.findViewById(R.id.hotel_BIN);
        hotel_environment_li_no = view.findViewById(R.id.hotel_environment_li_no);
        hotel_fire_li_no = view.findViewById(R.id.hotel_fire_li_no);

        btn_submit = view.findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_hotel_license_no = hotel_license_no.getText().toString();
                String str_hotel_license_date = hotel_license_date.getText().toString();
                String str_hotel_trade_license = hotel_trade_license.getText().toString();
                String str_hotel_TIN = hotel_TIN.getText().toString();
                String str_hotel_VAT = hotel_VAT.getText().toString();
                String str_hotel_BIN = hotel_BIN.getText().toString();
                String str_hotel_environment_li_no = hotel_environment_li_no.getText().toString();
                String str_hotel_fire_li_no = hotel_fire_li_no.getText().toString();




                if(str_hotel_license_no.isEmpty()){
                    hotel_license_no.setError(getString(R.string.required));
                    return;
                }

                if(str_hotel_license_date.isEmpty()){
                    hotel_license_date.setError(getString(R.string.required));
                    return;
                }

                if(str_hotel_trade_license.isEmpty()){
                    hotel_trade_license.setError(getString(R.string.required));
                    return;
                }

                if(str_hotel_TIN.isEmpty()){
                    hotel_TIN.setError(getString(R.string.required));
                    return;
                }

                if(str_hotel_VAT.isEmpty()){
                    hotel_VAT.setError(getString(R.string.required));
                    return;
                }

                if(str_hotel_BIN.isEmpty()){
                    hotel_BIN.setError(getString(R.string.required));
                    return;
                }

                if(str_hotel_environment_li_no.isEmpty()){
                    hotel_environment_li_no.setError(getString(R.string.required));
                    return;
                }

                if(str_hotel_fire_li_no.isEmpty()){
                    hotel_fire_li_no.setError(getString(R.string.required));
                    return;
                }


                submit_to_server(str_hotel_license_no,str_hotel_license_date, str_hotel_trade_license, str_hotel_TIN,
                        str_hotel_VAT, str_hotel_BIN,str_hotel_environment_li_no, str_hotel_fire_li_no  );


            }
        });

        return view;
    }

    private void submit_to_server(String str_hotel_license_no, String str_hotel_license_date, String str_hotel_trade_license, String str_hotel_tin, String str_hotel_vat, String str_hotel_bin, String str_hotel_environment_li_no, String str_hotel_fire_li_no) {

        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = Constraint.HOTEL_SET_LICENSE_ALL_INFORMATION;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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

                String hotel_id = Constraint.HOTEL_ID;
                if (hotel_id.isEmpty()) {
                    Toast.makeText(getContext(), "Something went to wrong!", Toast.LENGTH_SHORT).show();
                } else {

                    params.put("str_hotel_license_no", str_hotel_license_no);
                    params.put("str_hotel_license_date", str_hotel_license_date);
                    params.put("str_hotel_trade_license", str_hotel_trade_license);
                    params.put("str_hotel_TIN", str_hotel_tin);
                    params.put("str_hotel_VAT", str_hotel_vat);
                    params.put("str_hotel_BIN", str_hotel_bin);
                    params.put("str_hotel_environment_li_no", str_hotel_environment_li_no);
                    params.put("str_hotel_fire_li_no", str_hotel_fire_li_no);
                    params.put("hotel_id", hotel_id);

                }

                return params;
            }
        };

        requestQueue.add(request);

    }
    private void get_activity_data() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = Constraint.HOTEL_GET_LICENSE_ALL_INFORMATION;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray certificates_information = jsonObject.getJSONArray("certificates_information");



                    String ser_register_no = certificates_information.getJSONObject(0).getString("register_no");
                    String ser_register_date = certificates_information.getJSONObject(0).getString("register_date");
                    String ser_trade_no = certificates_information.getJSONObject(0).getString("trade_no");
                    String ser_tin_no = certificates_information.getJSONObject(0).getString("tin_no");
                    String ser_vat_no = certificates_information.getJSONObject(0).getString("vat_no");
                    String ser_bin_no = certificates_information.getJSONObject(0).getString("bin_no");
                    String ser_social_no= certificates_information.getJSONObject(0).getString("social_no");
                    String ser_fire_no = certificates_information.getJSONObject(0).getString("fire_no");


                    hotel_license_no.setText(ser_register_no);
                    hotel_license_date.setText(ser_register_date);
                    hotel_trade_license.setText(ser_trade_no);
                    hotel_TIN.setText(ser_tin_no);
                    hotel_VAT.setText(ser_vat_no);
                    hotel_BIN.setText(ser_bin_no);
                    hotel_environment_li_no.setText(ser_social_no);
                    hotel_fire_li_no.setText(ser_fire_no);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

                String hotel_id = Constraint.HOTEL_ID;
                if (hotel_id.isEmpty()) {
                    Toast.makeText(getContext(), "Something went to wrong!", Toast.LENGTH_SHORT).show();
                } else {
                    params.put("hotel_id", hotel_id);
                }

                return params;
            }
        };

        requestQueue.add(request);
    }

    @Override
    public void onResume() {
        get_activity_data();
        super.onResume();
    }
}
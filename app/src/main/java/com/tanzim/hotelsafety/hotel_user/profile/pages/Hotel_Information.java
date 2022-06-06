package com.tanzim.hotelsafety.hotel_user.profile.pages;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Hotel_Information extends Fragment {
    View view;

    TextInputEditText hotel_name_bn, hotel_name_en, hotel_star, police_station,
            hotel_address, hotel_mobile, hotel_email, hotel_website, hotel_established,
            hotel_social_media, hotel_others_social_media;
    Button btn_submit;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_hotel__profile_information, container, false);

        hotel_name_bn = view.findViewById(R.id.hotel_name_bn);
        hotel_name_en = view.findViewById(R.id.hotel_name_en);
        hotel_star = view.findViewById(R.id.hotel_star);
        police_station = view.findViewById(R.id.police_station);
        hotel_address = view.findViewById(R.id.hotel_address);
        hotel_mobile = view.findViewById(R.id.hotel_mobile);
        hotel_email = view.findViewById(R.id.hotel_email);
        hotel_website = view.findViewById(R.id.hotel_website);
        hotel_established = view.findViewById(R.id.hotel_established);
        hotel_social_media = view.findViewById(R.id.hotel_social_media);
        hotel_others_social_media = view.findViewById(R.id.hotel_others_social_media);

        btn_submit = view.findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_hotel_star = hotel_star.getText().toString();
                String str_police_station = police_station.getText().toString();
                String str_hotel_email = hotel_email.getText().toString();
                String str_hotel_website = hotel_website.getText().toString();
                String str_hotel_established = hotel_established.getText().toString();
                String str_hotel_social_media = hotel_social_media.getText().toString();
                String str_hotel_others_social_media = hotel_others_social_media.getText().toString();

                if(str_hotel_star.isEmpty()){
                    hotel_star.setError(getString(R.string.required));
                    return;
                }

                if(str_police_station.isEmpty()){
                    police_station.setError(getString(R.string.required));
                    return;
                }
                if(str_hotel_email.isEmpty()){
                    hotel_email.setError(getString(R.string.required));
                    return;
                }
                if(str_hotel_website.isEmpty()){
                    hotel_website.setError(getString(R.string.required));
                    return;
                }
                if(str_hotel_established.isEmpty()){
                    hotel_established.setError(getString(R.string.required));
                    return;
                }
                if(str_hotel_social_media.isEmpty()){
                    hotel_social_media.setError(getString(R.string.required));
                    return;
                }
                if(str_hotel_others_social_media.isEmpty()){
                    str_hotel_others_social_media = "দেওয়া হয়নি";
                }


                submit_to_server(str_hotel_star, str_police_station, str_hotel_email, str_hotel_website, str_hotel_established,str_hotel_social_media,str_hotel_others_social_media);

            }
        });


        return view;
    }

    private void submit_to_server(String str_hotel_star, String str_police_station, String str_hotel_email, String str_hotel_website, String str_hotel_established, String str_hotel_social_media, String str_hotel_others_social_media) {
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = Constraint.HOTEL_UPDATE_BASIC_INFORMATION;
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

                    params.put("star", str_hotel_star);
                    params.put("thana_name",str_police_station );
                    params.put("email", str_hotel_email);
                    params.put("website", str_hotel_website);
                    params.put("fb_page", str_hotel_social_media);
                    params.put("other_link", str_hotel_others_social_media);
                    params.put("established", str_hotel_established);
                    params.put("hotel_id", hotel_id);
                }

                return params;
            }
        };

        requestQueue.add(request);
    }
    private void get_activity_data() {
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = Constraint.HOTEL_GET_BASIC_INFORMATION;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray certificates_information = jsonObject.getJSONArray("basic_information");



                    String name_bn = certificates_information.getJSONObject(0).getString("name_bn");
                    String name_en = certificates_information.getJSONObject(0).getString("name_en");
                    String star = certificates_information.getJSONObject(0).getString("star");
                    String thana_name = certificates_information.getJSONObject(0).getString("thana_name");
                    String address = certificates_information.getJSONObject(0).getString("address");
                    String mobile = certificates_information.getJSONObject(0).getString("mobile");
                    String email= certificates_information.getJSONObject(0).getString("email");
                    String website = certificates_information.getJSONObject(0).getString("website");
                    String fb_page = certificates_information.getJSONObject(0).getString("fb_page");
                    String other_link = certificates_information.getJSONObject(0).getString("other_link");
                    String established = certificates_information.getJSONObject(0).getString("established");




                    hotel_name_bn.setText(name_bn);
                    hotel_name_en.setText(name_en);
                    hotel_star.setText(star);
                    police_station.setText(thana_name);
                    hotel_address.setText(address);
                    hotel_mobile.setText(mobile);
                    hotel_email.setText(email);
                    hotel_website.setText(website);
                    hotel_established.setText(established);
                    hotel_social_media.setText(fb_page);
                    hotel_others_social_media.setText(other_link);



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
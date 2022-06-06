package com.tanzim.hotelsafety.hotel_user.profile.pages;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.drm.ProcessedData;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import com.tanzim.hotelsafety.hotel_user.profile.pages.crud_hotel_director.Edit_Director_Info;
import com.tanzim.hotelsafety.hotel_user.profile.pages.crud_hotel_director.HotelDirectorList;
import com.tanzim.hotelsafety.hotel_user.profile.pages.crud_hotel_owner.HotelOwnerList;
import com.tanzim.hotelsafety.hotel_user.profile.pages.crud_hotel_owner.Owner_Adapter;
import com.tanzim.hotelsafety.hotel_user.profile.pages.crud_hotel_owner.Owner_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Hotel_Profile_Owner extends Fragment {

    View view;

    Button crud_owner, director_owner;
    TextView owner_number, director_number;

    RadioGroup accounts_country;
    String str_accounts_country = "";
    TextInputEditText hotel_owner_foreigners_invest;

    int owners_count, director_count = 0;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_hotel__profile__owner, container, false);

        owner_number = view.findViewById(R.id.owner_number);
        director_number = view.findViewById(R.id.director_number);
        accounts_country = view.findViewById(R.id.accounts_country);
        hotel_owner_foreigners_invest = view.findViewById(R.id.hotel_owner_foreigners_invest);





        accounts_country.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                str_accounts_country = String.valueOf(radioButton.getTag());
            }
        });


        crud_owner = view.findViewById(R.id.crud_owner);
        crud_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), HotelOwnerList.class));
            }
        });

        director_owner = view.findViewById(R.id.director_owner);
        director_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), HotelDirectorList.class));
            }
        });





        view.findViewById(R.id.save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(owners_count==0){
                    Toast.makeText(getContext(),"হোটেল মালিক সংযুক্ত করুন",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(director_count==0){
                    Toast.makeText(getContext(),"হোটেল ম্যানেজিং ডাইরেক্টর/সিইও সংযুক্ত করুন",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(str_accounts_country.isEmpty()){
                    Toast.makeText(getContext(),"ব্যবস্থাপক দেশি নাকি বিদেশি সিলেক্ট করুন",Toast.LENGTH_SHORT).show();
                    return;
                }


                String str_hotel_owner_foreigners_invest = hotel_owner_foreigners_invest.getText().toString();
                if(str_hotel_owner_foreigners_invest.isEmpty()){
                    str_hotel_owner_foreigners_invest = "দেওয়া হয়নি";
                }
                submit_total_information(str_accounts_country, str_hotel_owner_foreigners_invest);



            }
        });



        return view;
    }

    private void get_activity_data() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = Constraint.HOTEL_GET_OWNER_ALL_INFORMATION;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray room_list = jsonObject.getJSONArray("owner_all_information");

                    if(room_list.length()==1){
                        JSONObject arr = room_list.getJSONObject(0);
                        String ser_accounts_country = arr.getString("accounts_country");
                        String ser_foreign_invest = arr.getString("foreign_invest");

                        if(ser_accounts_country.equals("0")){
                            RadioGroup radioGroup = view.findViewById(R.id.accounts_country);
                            radioGroup.check(R.id.accountants_local);
                        }else if(ser_accounts_country.equals("1")){
                            RadioGroup radioGroup = view.findViewById(R.id.accounts_country);
                            radioGroup.check(R.id.accountants_foreigners);
                        }

                        hotel_owner_foreigners_invest.setText(ser_foreign_invest);
                    }


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

    private void submit_total_information(String str_accounts_country, String str_hotel_owner_foreigners_invest) {
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = Constraint.HOTEL_SET_OWNER_ALL_INFORMATION;
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

                    params.put("accounts_country", str_accounts_country);
                    params.put("foreign_invest", str_hotel_owner_foreigners_invest);
                    params.put("hotel_id", hotel_id);

                    Log.e("data", str_accounts_country);
                    Log.e("data", str_hotel_owner_foreigners_invest);
                }

                return params;
            }
        };

        requestQueue.add(request);
    }


    public void volley_run_director(){

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = Constraint.HOTEL_DIRECTOR_INFORMATION;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJsonDataDirector(response);
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

    private void parseJsonDataDirector(String string) {
        try {
            JSONObject object = new JSONObject(string);
            JSONArray room_list = object.getJSONArray("director_list");

            if(room_list.length()==0){
                director_number.setText("এখনো কোন তথ্য নেই।");
            }else{
                director_count = room_list.length();
                director_number.setText(room_list.length() +" জন");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    public void volley_run(){

        String url = Constraint.HOTEL_OWNER_INFORMATION;
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJsonData(response);
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

    private void parseJsonData(String string) {
        try {
            JSONObject object = new JSONObject(string);
            JSONArray room_list = object.getJSONArray("owner_list");

            if(room_list.length()==0){
                owner_number.setText("এখনো কোন তথ্য নেই।");
            }else{
                owners_count = room_list.length();
                owner_number.setText(room_list.length() +" জন");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        volley_run();
        volley_run_director();
        get_activity_data();
        super.onResume();
    }
}
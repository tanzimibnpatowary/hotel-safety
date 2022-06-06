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
import android.widget.RadioButton;
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
import com.tanzim.hotelsafety.hotel_user.hotel_home.Model_Room_List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Hotel_Profile_Facilities extends Fragment {

    View view;
    RadioGroup restaurant, bar, jim, pool, conference, spa;
    RadioButton res_positive, res_negative, bar_positive, bar_negative, jim_positive,
            jim_negative, pool_positive, pool_negative, con_positive, con_negative, spa_positive,
            spa_negative;
    Button save_btn;
    String str_restaurant ="", str_bar="", str_jim="", str_pool="", str_conference="", str_spa = "";

    TextInputEditText hotel_single_room_non_ac, hotel_single_room_ac, hotel_double_room_non_ac, hotel_double_room_ac, hotel_triple_room_non_ac, hotel_triple_room_ac;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_hotel__profile__facilities, container, false);

        save_btn = view.findViewById(R.id.save_btn);

        restaurant = view.findViewById(R.id.restaurant);
        bar = view.findViewById(R.id.bar);
        jim = view.findViewById(R.id.jim);
        pool = view.findViewById(R.id.pool);
        conference = view.findViewById(R.id.conference);
        spa = view.findViewById(R.id.spa);

        hotel_single_room_non_ac = view.findViewById(R.id.hotel_single_room_non_ac);
        hotel_single_room_ac = view.findViewById(R.id.hotel_single_room_ac);
        hotel_double_room_non_ac = view.findViewById(R.id.hotel_double_room_non_ac);
        hotel_double_room_ac = view.findViewById(R.id.hotel_double_room_ac);
        hotel_triple_room_non_ac = view.findViewById(R.id.hotel_triple_room_non_ac);
        hotel_triple_room_ac = view.findViewById(R.id.hotel_triple_room_ac);

        restaurant.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                str_restaurant = String.valueOf(radioButton.getTag());
            }
        });
        bar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                str_bar = String.valueOf(radioButton.getTag());
            }
        });
        jim.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                str_jim = String.valueOf(radioButton.getTag());
            }
        });
        pool.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                str_pool = String.valueOf(radioButton.getTag());
            }
        });
        conference.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                str_conference = String.valueOf(radioButton.getTag());
            }
        });
        spa.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                str_spa = String.valueOf(radioButton.getTag());
            }
        });


        res_positive = view.findViewById(R.id.res_positive);
        res_negative = view.findViewById(R.id.res_negative);
        bar_positive = view.findViewById(R.id.bar_positive);
        bar_negative = view.findViewById(R.id.bar_negative);
        jim_positive = view.findViewById(R.id.jim_positive);
        jim_negative = view.findViewById(R.id.jim_negative);
        pool_positive = view.findViewById(R.id.pool_positive);
        pool_negative = view.findViewById(R.id.pool_negative);
        con_positive = view.findViewById(R.id.con_positive);
        con_negative = view.findViewById(R.id.con_negative);
        spa_positive = view.findViewById(R.id.spa_positive);
        spa_negative = view.findViewById(R.id.spa_negative);




        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(str_restaurant.isEmpty()){
                    Toast.makeText(getContext(),"রেষ্টুরেন্টের সুবিধা আছে কি?",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(str_bar.isEmpty()){
                    Toast.makeText(getContext(),"বার সুবিধা আছে কি?",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(str_jim.isEmpty()){
                    Toast.makeText(getContext(),"জিম সুবিধা আছে কি?",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(str_pool.isEmpty()){
                    Toast.makeText(getContext(),"পুল সুবিধা আছে কি?",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(str_conference.isEmpty()){
                    Toast.makeText(getContext(),"কনফারেন্স সুবিধা আছে কি?",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(str_spa.isEmpty()){
                    Toast.makeText(getContext(),"স্পা বা বডি মেসেজিং সুবিধা আছে কি?",Toast.LENGTH_SHORT).show();
                    return;
                }

                submit_to_server(str_restaurant, str_bar, str_jim, str_pool, str_conference, str_spa);
            }
        });


        return view;
    }

    private void submit_to_server(String str_restaurant, String str_bar, String str_jim, String str_pool, String str_conference, String str_spa) {
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = Constraint.HOTEL_SET_FACILITIES_ALL_INFORMATION;
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

                    params.put("str_restaurant", str_restaurant);
                    params.put("str_bar", str_bar);
                    params.put("str_jim", str_jim);
                    params.put("str_pool", str_pool);
                    params.put("str_conference", str_conference);
                    params.put("str_spa", str_spa);
                    params.put("hotel_id", hotel_id);

                }

                return params;
            }
        };

        requestQueue.add(request);
    }
    private void get_activity_data() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = Constraint.HOTEL_GET_FACILITIES_ALL_INFORMATION;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray room_list = jsonObject.getJSONArray("facilities_information");

                    if(room_list.length()==1){
                        JSONObject arr = room_list.getJSONObject(0);
                        String ser_restuarnt= arr.getString("restuarnt");
                        String ser_bar= arr.getString("bar");
                        String ser_jim= arr.getString("jim");
                        String ser_pool = arr.getString("pool");
                        String ser_conference= arr.getString("conference");
                        String ser_spa= arr.getString("spa");

                        if(ser_restuarnt.equals("0")){
                            restaurant.check(R.id.res_negative);
                        }else if(ser_restuarnt.equals("1")){
                            restaurant.check(R.id.res_positive);
                        }

                        if(ser_bar.equals("0")){
                            bar.check(R.id.bar_negative);
                        }else if(ser_bar.equals("1")){
                            bar.check(R.id.bar_positive);
                        }
                        if(ser_jim.equals("0")){
                            jim.check(R.id.jim_negative);
                        }else if(ser_jim.equals("1")){
                            jim.check(R.id.jim_positive);
                        }
                        if(ser_pool.equals("0")){
                            pool.check(R.id.pool_negative);
                        }else if(ser_pool.equals("1")){
                            pool.check(R.id.pool_positive);
                        }

                        if(ser_conference.equals("0")){
                            conference.check(R.id.con_negative);
                        }else if(ser_conference.equals("1")){
                            conference.check(R.id.con_positive);
                        }

                        if(ser_spa.equals("0")){
                            spa.check(R.id.spa_negative);
                        }else if(ser_spa.equals("1")){
                            spa.check(R.id.spa_positive);
                        }


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
    private void get_room_count(){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = Constraint.HOTEL_GET_ROOMS_COUNT_INFORMATION;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray room_list = jsonObject.getJSONArray("room_list");
                    String single_ac= room_list.getJSONObject(0).getString("single_ac");
                    String single_non_ac= room_list.getJSONObject(1).getString("single_non_ac");
                    String double_ac= room_list.getJSONObject(2).getString("double_ac");
                    String double_non_ac= room_list.getJSONObject(3).getString("double_non_ac");
                    String triple_ac= room_list.getJSONObject(4).getString("triple_ac");
                    String triple_non_ac= room_list.getJSONObject(5).getString("triple_non_ac");

                    hotel_single_room_non_ac.setText(single_non_ac);
                    hotel_single_room_ac.setText(single_ac);
                    hotel_double_room_non_ac.setText(double_non_ac);
                    hotel_double_room_ac.setText(double_ac);
                    hotel_triple_room_non_ac.setText(triple_non_ac);
                    hotel_triple_room_ac.setText(triple_ac);




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
        get_room_count();
        super.onResume();
    }
}
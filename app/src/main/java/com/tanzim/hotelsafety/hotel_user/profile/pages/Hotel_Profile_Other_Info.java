package com.tanzim.hotelsafety.hotel_user.profile.pages;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Hotel_Profile_Other_Info extends Fragment {

    View view;
    RadioGroup parking, emergency, fire, own_electricity, member_hotel_society;
    RadioButton parking_positive, parking_negative, emergency_positive, emergency_negative, fire_positive, fire_negative,
    own_electricity_positive, own_electricity_negative, member_hotel_society_positive,
    member_hotel_society_negative;
    TextInputEditText hotel_others_cc_camera, hotel_others_reputation, hotel_others_information_data, hotel_others_comment;
    String str_parking = "", str_emergency = "", str_fire = "", str_own_electricity= "", str_member_hotel_society = "";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_hotel__profile__other__info, container, false);

        hotel_others_cc_camera = view.findViewById(R.id.hotel_others_cc_camera);
        hotel_others_reputation = view.findViewById(R.id.hotel_others_reputation);
        hotel_others_information_data = view.findViewById(R.id.hotel_others_information_data);
        hotel_others_comment = view.findViewById(R.id.hotel_others_comment);

        parking = view.findViewById(R.id.parking);
        emergency = view.findViewById(R.id.emergency);
        fire = view.findViewById(R.id.fire);
        own_electricity = view.findViewById(R.id.own_electricity);
        member_hotel_society = view.findViewById(R.id.member_hotel_society);

        parking.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                str_parking = String.valueOf(radioButton.getTag());
            }
        });
        emergency.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                str_emergency = String.valueOf(radioButton.getTag());
            }
        });
        fire.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                str_fire = String.valueOf(radioButton.getTag());
            }
        });
        own_electricity.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                str_own_electricity = String.valueOf(radioButton.getTag());
            }
        });
        member_hotel_society.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                str_member_hotel_society = String.valueOf(radioButton.getTag());
            }
        });


        parking_positive = view.findViewById(R.id.parking_positive);
        parking_negative = view.findViewById(R.id.parking_negative);

        emergency_positive = view.findViewById(R.id.emergency_positive);
        emergency_negative = view.findViewById(R.id.emergency_negative);

        fire_positive = view.findViewById(R.id.fire_positive);
        fire_negative = view.findViewById(R.id.fire_negative);

        own_electricity_positive = view.findViewById(R.id.own_electricity_positive);
        own_electricity_negative = view.findViewById(R.id.own_electricity_negative);

        member_hotel_society_positive = view.findViewById(R.id.member_hotel_society_positive);
        member_hotel_society_negative = view.findViewById(R.id.member_hotel_society_negative);


        view.findViewById(R.id.save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String str_hotel_others_cc_camera = hotel_others_cc_camera.getText().toString();
                String str_hotel_others_reputation = hotel_others_reputation.getText().toString();
                String str_hotel_others_information_data = hotel_others_information_data.getText().toString();
                String str_hotel_others_comment = hotel_others_comment.getText().toString();


                if(str_parking.isEmpty()){
                    Toast.makeText(getContext(),"পাকিং সুবিধা আছে কি?",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(str_emergency.isEmpty()){
                    Toast.makeText(getContext(),"ইমারজেন্সি বাহির সুবিধা আছে কি?",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(str_fire.isEmpty()){
                    Toast.makeText(getContext(),"আগুন নির্বাপক সুবিধা আছে কি?",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(str_own_electricity.isEmpty()){
                    Toast.makeText(getContext(),"নিজস্ব জেনেরেটর সুবিধা আছে কি?",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(str_member_hotel_society.isEmpty()){
                    Toast.makeText(getContext(),"হোটেল মালিক সমিতির সদস্য কি না?",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(str_hotel_others_cc_camera.isEmpty()){
                    hotel_others_cc_camera.setError(getString(R.string.required));
                    return;
                }
                if(str_hotel_others_reputation.isEmpty()){
                    str_hotel_others_reputation = "পূরণ হয়নি";
                }
                if(str_hotel_others_information_data.isEmpty()){
                    str_hotel_others_information_data = "পূরণ হয়নি";
                }
                if(str_hotel_others_comment.isEmpty()){
                    str_hotel_others_comment = "পূরণ হয়নি";
                }

                submit_to_server(str_parking, str_emergency, str_fire, str_own_electricity, str_member_hotel_society,
                        str_hotel_others_cc_camera,str_hotel_others_reputation,str_hotel_others_information_data,str_hotel_others_comment);

            }
        });


        return view;
    }

    private void submit_to_server(String str_parking, String str_emergency, String str_fire, String str_own_electricity,
                                  String str_member_hotel_society, String str_hotel_others_cc_camera,
                                  String str_hotel_others_reputation, String str_hotel_others_information_data,
                                  String str_hotel_others_comment) {



        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = Constraint.HOTEL_SET_OTHERS_ALL_INFORMATION;
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

                    params.put("str_parking", str_parking);
                    params.put("str_emergency", str_emergency);
                    params.put("str_fire", str_fire);
                    params.put("str_own_electricity", str_own_electricity);
                    params.put("str_member_hotel_society", str_member_hotel_society);
                    params.put("str_hotel_others_cc_camera", str_hotel_others_cc_camera);
                    params.put("str_hotel_others_reputation", str_hotel_others_reputation);
                    params.put("str_hotel_others_information_data", str_hotel_others_information_data);
                    params.put("str_hotel_others_comment", str_hotel_others_comment);
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
        String url = Constraint.HOTEL_GET_OTHERS_ALL_INFORMATION;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray room_list = jsonObject.getJSONArray("others_information");

                    if(room_list.length()==1){
                        JSONObject arr = room_list.getJSONObject(0);
                        String ser_parking= arr.getString("parking");
                        String ser_emergency= arr.getString("emergency");
                        String ser_fire = arr.getString("fire");
                        String ser_electricity = arr.getString("electricity");
                        String ser_member= arr.getString("member");
                        String ser_cc_camera= arr.getString("cc_camera");
                        String ser_reputation= arr.getString("reputation");
                        String ser_others_info= arr.getString("others_info");
                        String ser_comment= arr.getString("comment");

                        if(ser_parking.equals("0")){
                            parking.check(R.id.parking_negative);
                        }else if(ser_parking.equals("1")){
                            parking.check(R.id.parking_positive);
                        }

                        if(ser_emergency.equals("0")){
                            emergency.check(R.id.emergency_negative);
                        }else if(ser_emergency.equals("1")){
                            emergency.check(R.id.emergency_positive);
                        }
                        if(ser_fire.equals("0")){
                            fire.check(R.id.fire_negative);
                        }else if(ser_fire.equals("1")){
                            fire.check(R.id.fire_positive);
                        }
                        if(ser_electricity.equals("0")){
                            own_electricity.check(R.id.own_electricity_negative);
                        }else if(ser_electricity.equals("1")){
                            own_electricity.check(R.id.own_electricity_positive);
                        }

                        if(ser_member.equals("0")){
                            member_hotel_society.check(R.id.member_hotel_society_negative);
                        }else if(ser_member.equals("1")){
                            member_hotel_society.check(R.id.member_hotel_society_positive);
                        }


                        hotel_others_cc_camera.setText(ser_cc_camera);
                        hotel_others_reputation.setText(ser_reputation);
                        hotel_others_information_data.setText(ser_others_info);
                        hotel_others_comment.setText(ser_comment);



                    }


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
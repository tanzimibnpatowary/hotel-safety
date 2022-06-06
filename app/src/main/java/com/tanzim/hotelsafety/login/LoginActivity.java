package com.tanzim.hotelsafety.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.tanzim.hotelsafety.DashboardActivity;
import com.tanzim.hotelsafety.R;
import com.tanzim.hotelsafety.app_static.Constraint;
import com.tanzim.hotelsafety.hotel_user.HotelDashboard;
import com.tanzim.hotelsafety.hotel_user.guest.Adapter_Guest_Booked_History;
import com.tanzim.hotelsafety.hotel_user.guest.Guest_History;
import com.tanzim.hotelsafety.hotel_user.guest.Model_History;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static java.security.AccessController.getContext;

public class LoginActivity extends AppCompatActivity {
    private String[] role_arr = {"হোটেল","পুলিশ"};
    int status = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputEditText hotel_id = findViewById(R.id.user_id);
        TextInputEditText user_password = findViewById(R.id.user_password);
        AutoCompleteTextView role = findViewById(R.id.role);
        role.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.dropdown_menu_popup_item,role_arr));
        Button login_btn = findViewById(R.id.login_btn);



        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_hotel_id = hotel_id.getText().toString();
                String str_user_password = user_password.getText().toString();
                String str_role = role.getText().toString();

                if(str_role.isEmpty()){
                    Toast.makeText(getApplicationContext(),"আপনার Role পছন্দ করুন",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(str_hotel_id.isEmpty()){
                    hotel_id.setError(getString(R.string.required));
                    return;
                }
                if(str_user_password.isEmpty()){
                    user_password.setError(getString(R.string.required));
                    return;
                }

                if(str_role.equals("হোটেল")){
                    is_match_password_hotel(str_hotel_id, str_user_password);
                }

                if(str_role.equals("পুলিশ")){
                    is_match_password_police(str_hotel_id,str_user_password);
                }



            }
        });


    }

    private void is_match_password_police(String str_hotel_id, String str_user_password) {
        RequestQueue request = Volley.newRequestQueue(getApplicationContext());
        ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();
        String url = Constraint.POLICE_CHECK_AUTH;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray room_list = jsonObject.getJSONArray("hotel_police_information");

                    if(room_list.length()==0){
                        Toast.makeText(getApplicationContext(),"ইউজার আইডি/পাসওয়ার্ড সঠিক নয়",Toast.LENGTH_SHORT).show();
                    }else {
                        JSONObject data_object = room_list.getJSONObject(0);
                        String ser_hotel_id = data_object.getString("user_id");
                        String ser_user_password = data_object.getString("user_password");

                        if (ser_hotel_id.equals(str_hotel_id) && ser_user_password.equals(str_user_password)) {
                            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));

                        } else {
                            Toast.makeText(getApplicationContext(), "Something went to wrong", Toast.LENGTH_SHORT).show();
                        }
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
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", str_hotel_id);
                params.put("user_password", str_user_password);

                return params;
            }
        };

        request.add(stringRequest);
    }

    void is_match_password_hotel(String hotel_id, String user_password){
        RequestQueue request = Volley.newRequestQueue(getApplicationContext());
        ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();
        String url = Constraint.HOTEL_CHECK_AUTH;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray room_list = jsonObject.getJSONArray("hotel_panel_information");

                    if(room_list.length()==0){
                        Toast.makeText(getApplicationContext(),"ইউজার আইডি/পাসওয়ার্ড সঠিক নয়",Toast.LENGTH_SHORT).show();
                    }else {
                        JSONObject data_object = room_list.getJSONObject(0);
                        String ser_today_date = data_object.getString("date");
                        String ser_time = data_object.getString("time");
                        String ser_hotel_id = data_object.getString("hotel_id");
                        String ser_user_password = data_object.getString("user_password");

                        if (ser_hotel_id.equals(hotel_id) && ser_user_password.equals(user_password)) {
                            status = 1;
                            Constraint.TODAY_DATE = ser_today_date;
                            Constraint.HOTEL_ID = ser_hotel_id;
                            Intent intent = new Intent(LoginActivity.this, HotelDashboard.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(getApplicationContext(), "Something went to wrong", Toast.LENGTH_SHORT).show();
                        }
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
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("hotel_id", hotel_id);
                params.put("user_password", user_password);

                return params;
            }
        };

        request.add(stringRequest);

    }
}
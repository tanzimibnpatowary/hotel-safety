package com.tanzim.hotelsafety.hotel_user.hotel_home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tanzim.hotelsafety.R;
import com.tanzim.hotelsafety.app_static.Constraint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class All_Room_List extends AppCompatActivity {
    ProgressDialog dialog;

    ArrayList<Model_Room_List> model_room_lists = new ArrayList<>();
    Adapter_Room_List adapter_room_list;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__room__list);


    }


    private void get_activity_data() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();
        dialog.setCancelable(false);
        String url = Constraint.HOTEL_GET_ALL_ROOM_INFORMATION;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray room_list = object.getJSONArray("room_list");
                    model_room_lists.clear();
                    for (int i = 0; i < room_list.length(); ++i) {
                        JSONObject data_object = room_list.getJSONObject(i);

                        String room_name = data_object.isNull("room_no") ? null : data_object.getString("room_no");
                        String room_type = data_object.isNull("room_type") ? null : data_object.getString("room_type");
                        String room_facilities = data_object.isNull("room_facilities") ? null : data_object.getString("room_facilities");
                        String hotel_id = data_object.isNull("hotel_id") ? null : data_object.getString("hotel_id");
                        String leave_date = data_object.isNull("leave_date") ? null : data_object.getString("leave_date");

//                        String room_name = data_object.getString("room_no");
//                        String room_type = data_object.getString("room_type");
//                        String room_facilities = data_object.getString("room_facilities");
//                        String hotel_id = data_object.getString("hotel_id");
//                        String leave_date = "";
//                        try {
//                            leave_date = data_object.getString("leave_date");
//                        }catch (Exception e){
//                            leave_date = "";
//                        }



                        String is_booked = "2";
                        if(String.valueOf(leave_date).equals("null")){
                            is_booked = "2";
                        }else{
                            Date date2 =new SimpleDateFormat("dd/MM/yyyy").parse(Constraint.TODAY_DATE);
                            Date date1 =new SimpleDateFormat("dd/MM/yyyy").parse(leave_date);
                            long duration = date1.getTime() - date2.getTime();
                            long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
                            is_booked =  diffInSeconds>0?"1":"2";
                        }


                        if (hotel_id.equals(Constraint.HOTEL_ID)) {
                            Model_Room_List roomList = new Model_Room_List(room_name, room_type, room_facilities, is_booked);
                            model_room_lists.add(0,roomList);
                        }
                    }
                    recyclerView = findViewById(R.id.recyclerView);
                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    adapter_room_list = new Adapter_Room_List(model_room_lists);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter_room_list);

                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                    Log.e("data",e.toString());
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
                    Toast.makeText(getApplicationContext(), "Something went to wrong!", Toast.LENGTH_SHORT).show();
                } else {
                    params.put("hotel_id", hotel_id);
                }

                return params;
            }
        };

        requestQueue.add(request);
    }

    @Override
    protected void onResume() {
        get_activity_data();
        super.onResume();
    }
}
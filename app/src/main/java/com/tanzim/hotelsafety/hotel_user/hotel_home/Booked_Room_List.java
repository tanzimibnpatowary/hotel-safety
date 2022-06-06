package com.tanzim.hotelsafety.hotel_user.hotel_home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Booked_Room_List extends AppCompatActivity {


    ArrayList<Model_Room_List> model_room_lists = new ArrayList<>();
    Adapter_Booked_Room_List adapter_room_list;
    RecyclerView recyclerView;
    SearchView search_view;
    LinearLayout main_section;
    View no_data_msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked__room__list);

        search_view = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.recyclerView);
        main_section = findViewById(R.id.main_section);
        no_data_msg = findViewById(R.id.no_data_msg);


        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter_room_list.getFilter().filter(newText);

                return false;
            }
        });



    }


    private void get_activity_data() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();
        dialog.setCancelable(false);
        String url = Constraint.HOTEL_GET_BOOKED_ROOM_INFORMATION;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray room_list = object.getJSONArray("room_list");
                    if(room_list.length()==0){
                        main_section.setVisibility(View.GONE);
                        no_data_msg.setVisibility(View.VISIBLE);
                    }else {
                        main_section.setVisibility(View.VISIBLE);
                        no_data_msg.setVisibility(View.GONE);
                        model_room_lists.clear();
                        for (int i = 0; i < room_list.length(); ++i) {
                            JSONObject data_object = room_list.getJSONObject(i);
                            String room_name = data_object.getString("room_no");
                            String room_type = data_object.getString("room_type");
                            String room_facilities = data_object.getString("room_facilities");
                            String hotel_id = data_object.getString("hotel_id");
                            String c_id = data_object.getString("c_id");
                            String c_name = data_object.getString("c_name");
                            String c_mobile = data_object.getString("c_mobile");

                            if (hotel_id.equals(Constraint.HOTEL_ID)) {
                                Model_Room_List roomList = new Model_Room_List(room_name, room_type, room_facilities, hotel_id,c_id,c_name,c_mobile);
                                model_room_lists.add(0,roomList);
                            }
                        }

                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        adapter_room_list = new Adapter_Booked_Room_List(model_room_lists);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter_room_list);
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
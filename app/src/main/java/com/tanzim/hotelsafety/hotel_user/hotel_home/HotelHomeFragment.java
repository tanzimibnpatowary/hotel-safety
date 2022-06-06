package com.tanzim.hotelsafety.hotel_user.hotel_home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tanzim.hotelsafety.R;
import com.tanzim.hotelsafety.app_static.Constraint;
import com.tanzim.hotelsafety.dialog.Add_Hotel_Room;

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

public class HotelHomeFragment extends Fragment {

    private String[] ac_non_ac_array = {"এসি","নন এসি"};
    private String[] bed_type_array = {"সিঙ্গেল","ডাবল","ট্রিপল"};

    AutoCompleteTextView hotel_bed_category_ac_non_ac, hotel_bed_category_double;
    RecyclerView recyclerView;
    private ArrayList<Model_Room_List> model_room_lists = new ArrayList<>();
    Adapter_Room_List adapter_room_list;
    ProgressDialog dialog;

    CardView btn_add_room, btn_all_room, btn_booked_room;
    View view;

    String category_val, facilities_val = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hotel_home, container, false);
        //set adapter for ac no ac hotel
        hotel_bed_category_ac_non_ac = view.findViewById(R.id.hotel_bed_category_ac_non_ac);
        hotel_bed_category_double = view.findViewById(R.id.hotel_bed_category_double);




        hotel_bed_category_ac_non_ac.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.dropdown_menu_popup_item,ac_non_ac_array));

        hotel_bed_category_double.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.dropdown_menu_popup_item,bed_type_array));

        btn_add_room = view.findViewById(R.id.btn_add_room);
        btn_all_room = view.findViewById(R.id.btn_all_room);
        btn_booked_room = view.findViewById(R.id.btn_booked_room);
        recyclerView = view.findViewById(R.id.room_list_recyclerView);

        hotel_bed_category_double.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                category_val = String.valueOf(position+1);
                hotel_bed_category_ac_non_ac.getText().clear();
                filter_only_category_way(category_val);
            }
        });

        hotel_bed_category_ac_non_ac.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                facilities_val = String.valueOf(position+1);
                if(!facilities_val.isEmpty() && !category_val.isEmpty()) {
                    filter_only_filer_both_way(category_val,facilities_val);
                }
            }
        });



        btn_booked_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Booked_Room_List.class));
            }
        });

        btn_all_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), All_Room_List.class));
            }
        });
        btn_add_room.setOnClickListener(v -> add_new_room());


        return view;
    }

    private void add_new_room() {
        Add_Hotel_Room add_guest = new Add_Hotel_Room();
        add_guest.show(getActivity().getSupportFragmentManager(),Add_Hotel_Room.HOTEL_ROOM_ADD);
        add_guest.setListener(this::Insert);
    }

    private void Insert(String s, String s1) {
    }

    void filter_only_filer_both_way(String room_type, String room_facilities){
        dialog = new ProgressDialog(view.getContext());
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();
        String url = Constraint.HOTEL_ROOM_INFORMATION_BOTH;
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                if(response.isEmpty()){
                    Toast.makeText(getContext(),"Not Found Any Data",Toast.LENGTH_SHORT).show();
                }else{
                    parseJsonData(response);
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
                params.put("room_type", room_type);
                params.put("room_facilities", room_facilities);
                params.put("hotel_id", Constraint.HOTEL_ID);
                return params;
            }
        };

        requestQueue.add(request);
    }

    void filter_only_category_way(String value){
        dialog = new ProgressDialog(view.getContext());
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();
        String url = Constraint.HOTEL_ROOM_INFORMATION_CATEGORY;
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("data",response);
                dialog.dismiss();
                if(response.isEmpty()){
                    Toast.makeText(getContext(),"Not Found Any Data",Toast.LENGTH_SHORT).show();
                }else{
                    parseJsonData(response);
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

                params.put("room_type", value);
                params.put("hotel_id", Constraint.HOTEL_ID);
                return params;
            }
        };

        requestQueue.add(request);
    }


    private void parseJsonData(String string) {
        dialog = new ProgressDialog(view.getContext());
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();
        try {
            JSONObject object = new JSONObject(string);
            JSONArray room_list = object.getJSONArray("room_list");

            if(room_list.length()!=0) {
                recyclerView.setVisibility(View.VISIBLE);
                View myView = view.findViewById(R.id.no_data_msg);
                myView.setVisibility(View.GONE);
                model_room_lists.clear();

                for (int i = 0; i < room_list.length(); ++i) {
                    JSONObject data_object = room_list.getJSONObject(i);
                    String room_name = data_object.getString("room_no");
                    String room_type = data_object.getString("room_type");
                    String room_facilities = data_object.getString("room_facilities");
                    String hotel_id = data_object.getString("hotel_id");


                    if (hotel_id.equals(Constraint.HOTEL_ID)) {
                        Model_Room_List roomList = new Model_Room_List(room_name, room_type, room_facilities, hotel_id);
                        model_room_lists.add(0,roomList);
                    }
                }

                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                adapter_room_list = new Adapter_Room_List(model_room_lists);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter_room_list);
            }else{
                recyclerView.setVisibility(View.GONE);
                View myView = view.findViewById(R.id.no_data_msg);
                myView.setVisibility(View.VISIBLE);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        dialog.dismiss();
    }

    private void get_activity_data() {
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();
        dialog.setCancelable(true);
        String url = Constraint.HOTEL_GET_ALL_ROOM_INFORMATION;
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray room_list = object.getJSONArray("room_list");

                    if(room_list.length()!=0) {
                        model_room_lists.clear();
                        recyclerView.setVisibility(View.VISIBLE);
                        View myView = view.findViewById(R.id.no_data_msg);
                        myView.setVisibility(View.GONE);
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
                            if (String.valueOf(leave_date).equals("null")) {
                                is_booked = "2";
                            } else {
                                Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(Constraint.TODAY_DATE);
                                Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(leave_date);
                                long duration = date1.getTime() - date2.getTime();
                                long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
                                is_booked = diffInSeconds > 0 ? "1" : "2";
                            }


                            if (hotel_id.equals(Constraint.HOTEL_ID) && is_booked.equals("2")) {
                                Model_Room_List roomList = new Model_Room_List(room_name, room_type, room_facilities, is_booked);
                                model_room_lists.add(roomList);
                            }
                        }
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                        adapter_room_list = new Adapter_Room_List(model_room_lists);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter_room_list);
                    }else{
                        recyclerView.setVisibility(View.GONE);
                        View myView = view.findViewById(R.id.no_data_msg);
                        myView.setVisibility(View.VISIBLE);
                    }

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
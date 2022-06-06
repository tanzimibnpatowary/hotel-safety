package com.tanzim.hotelsafety.police.booking;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.tanzim.hotelsafety.R;
import com.tanzim.hotelsafety.app_static.Constraint;
import com.tanzim.hotelsafety.hotel_user.hotel_home.Adapter_Booked_Room_List;
import com.tanzim.hotelsafety.hotel_user.hotel_home.Model_Room_List;
import com.tanzim.hotelsafety.police.hotel_list.Adapter_Hotel_List;
import com.tanzim.hotelsafety.police.hotel_list.Model_Hotel_List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class BookingFragment extends Fragment {

    final Calendar myCalendar= Calendar.getInstance();
    TextInputEditText starting_date, ending_date;

    ArrayList<Model_Room_List> model_room_lists = new ArrayList<>();
    Adapter_All_Booked_List adapter_room_list;
    RecyclerView recyclerView;
    SearchView search_view;
    View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_booking_information, container, false);
//        starting_date = view.findViewById(R.id.stating_date);
//        ending_date = view.findViewById(R.id.ending_date);

        search_view = view.findViewById(R.id.search_view);
        recyclerView = view.findViewById(R.id.recyclerView);


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
        get_activity_data();
//        DatePickerDialog.OnDateSetListener star =new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int day) {
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH,month);
//                myCalendar.set(Calendar.DAY_OF_MONTH,day);
//                starting_date.setText(updateLabel());
//            }
//        };
//
//        starting_date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new DatePickerDialog(getActivity(),star,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
//
//
//        DatePickerDialog.OnDateSetListener end =new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int day) {
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH,month);
//                myCalendar.set(Calendar.DAY_OF_MONTH,day);
//                ending_date.setText(updateLabel());
//            }
//        };
//        ending_date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new DatePickerDialog(getActivity(),end,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
        return view;
    }

    private String updateLabel(){
        String myFormat="dd/MM/yy";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Dhaka"));
        return dateFormat.format(myCalendar.getTime());
    }




    private void get_activity_data() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();
        String url = Constraint.HOTEL_GET_ALL_BOOKED_ROOM_INFORMATION;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray room_list = object.getJSONArray("room_list");
                    if(room_list.length()==0){
                        recyclerView.setVisibility(View.GONE);
                        View myView = view.findViewById(R.id.no_data_msg);
                        myView.setVisibility(View.VISIBLE);
                    }else {
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
                            String c_id = data_object.getString("c_id");
                            String c_name = data_object.getString("c_name");
                            String c_mobile = data_object.getString("c_mobile");

                            Model_Room_List roomList = new Model_Room_List(room_name, room_type, room_facilities, hotel_id,c_id,c_name,c_mobile);
                            model_room_lists.add(0,roomList);
                        }

                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                        adapter_room_list = new Adapter_All_Booked_List(model_room_lists);
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
                dialog.dismiss();
            }
        });

        requestQueue.add(request);
    }

    @Override
    public void onResume() {

        super.onResume();
    }
}
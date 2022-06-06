package com.tanzim.hotelsafety.police.hotel_list;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.tanzim.hotelsafety.R;
import com.tanzim.hotelsafety.app_static.Constraint;
import com.tanzim.hotelsafety.dialog.Add_Hotel;
import com.tanzim.hotelsafety.hotel_user.guest.Adapter_Guest_List;
import com.tanzim.hotelsafety.hotel_user.guest.Model_Guest_List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HotelListFragment extends Fragment {

    RecyclerView recyclerView;
    View view;

    ArrayList<Model_Hotel_List> model_hotel_listArrayList = new ArrayList<>();
    Adapter_Hotel_List adapter_hotel_list;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hotel_list, container, false);


        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> add_Hotel_Dialog());

        recyclerView = view.findViewById(R.id.recyclerView);



        return view;
    }

    private void add_Hotel_Dialog() {
        Add_Hotel dialog = new Add_Hotel();
        dialog.show(getActivity().getSupportFragmentManager(), Add_Hotel.HOTEL_ADD);
        dialog.setListener(this::Insert);
    }

    private void Insert(String s, String s1) {

    }


    private void get_activity_data() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();
        String url = Constraint.ADMIN_GET_HOTEL_INFORMATION;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray room_list = jsonObject.getJSONArray("hotel_list");
                    if(room_list.length()!=0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        View myView = view.findViewById(R.id.no_data_msg);
                        myView.setVisibility(View.GONE);
                        model_hotel_listArrayList.clear();
                        for (int i = 0; i < room_list.length(); ++i) {
                            JSONObject data_object = room_list.getJSONObject(i);
                            String hotel_name_bn = data_object.getString("name_bn");
                            String hotel_id = data_object.getString("hotel_id");
                            String name_en = data_object.getString("name_en");
                            String address = data_object.getString("address");
                            String mobile = data_object.getString("mobile");
                            Model_Hotel_List model_guest_list = new Model_Hotel_List(name_en,hotel_name_bn,address,mobile,hotel_id);
                            model_hotel_listArrayList.add(0,model_guest_list);
                        }

                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                        adapter_hotel_list = new Adapter_Hotel_List(model_hotel_listArrayList);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter_hotel_list);
                    }else{
                        recyclerView.setVisibility(View.GONE);
                        View myView = view.findViewById(R.id.no_data_msg);
                        myView.setVisibility(View.VISIBLE);
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
        get_activity_data();
        super.onResume();
    }
}

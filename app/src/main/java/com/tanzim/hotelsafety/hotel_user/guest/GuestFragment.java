package com.tanzim.hotelsafety.hotel_user.guest;

import android.app.ProgressDialog;
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
import com.tanzim.hotelsafety.R;
import com.tanzim.hotelsafety.app_static.Constraint;
import com.tanzim.hotelsafety.dialog.Add_Guest;
import com.tanzim.hotelsafety.hotel_user.hotel_home.Adapter_Room_List;
import com.tanzim.hotelsafety.hotel_user.hotel_home.Model_Room_List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GuestFragment extends Fragment {

    private ArrayList<Model_Guest_List> model_guest_lists = new ArrayList<>();
    private Adapter_Guest_List adapter_guest_list;

    RecyclerView guest_list_recyclerView;
    View view;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_guest, container, false);
        guest_list_recyclerView = view.findViewById(R.id.recyclerView);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> add_Guest());


        return view;
    }


    private void get_activity_data() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        ProgressDialog dialog = new ProgressDialog(view.getContext());
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();
        String url = Constraint.HOTEL_GET_CUSTOMER_INFORMATION;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray room_list = jsonObject.getJSONArray("guest_information");
                    if(room_list.length()!=0) {
                        guest_list_recyclerView.setVisibility(View.VISIBLE);
                        View myView = view.findViewById(R.id.no_data_msg);
                        myView.setVisibility(View.GONE);
                        model_guest_lists.clear();
                        for (int i = 0; i < room_list.length(); ++i) {
                            JSONObject data_object = room_list.getJSONObject(i);
                            String _id = data_object.getString("id");
                            String customer_id = data_object.getString("customer_id");
                            String name = data_object.getString("name");
                            String mobile = data_object.getString("mobile");
                            String email = data_object.getString("email");
                            String dob = data_object.getString("dob");
                            String nationality = data_object.getString("nationality");
                            String address = data_object.getString("address");
                            String profile = data_object.getString("profile");
                            String nid = data_object.getString("nid");
                            String hotel_id = data_object.getString("hotel_id");
                            Model_Guest_List roomList = new Model_Guest_List(_id, customer_id, name, mobile, email, dob, nationality, address, profile, nid, hotel_id);
                            model_guest_lists.add(0,roomList);
                        }

                        guest_list_recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                        adapter_guest_list = new Adapter_Guest_List(model_guest_lists);
                        guest_list_recyclerView.setLayoutManager(layoutManager);
                        guest_list_recyclerView.setAdapter(adapter_guest_list);
                    }else{
                        guest_list_recyclerView.setVisibility(View.GONE);
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


    private void add_Guest() {
        Add_Guest add_guest = new Add_Guest();
        add_guest.show(getActivity().getSupportFragmentManager(),Add_Guest.GUEST_ADD);
        add_guest.setListener(this::Insert);
    }

    private void Insert(String s, String s1) {
    }

    @Override
    public void onResume() {
        get_activity_data();
        super.onResume();
    }
}
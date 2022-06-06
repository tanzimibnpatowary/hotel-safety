package com.tanzim.hotelsafety.hotel_user.guest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tanzim.hotelsafety.R;
import com.tanzim.hotelsafety.app_static.Constraint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Guest_History extends AppCompatActivity {

    ImageView nid;
    TextView address, nationality, dob, email, mobile, customer_id, name;
    CircleImageView profile;
    private ArrayList<Model_History> model_historyArrayList = new ArrayList<>();
    private Adapter_Guest_Booked_History adapter_guest_booked_history;
    String customer_id_ser = "";
    RecyclerView guest_list_recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest__history);


        nid = findViewById(R.id.nid);
        address = findViewById(R.id.address);
        nationality = findViewById(R.id.nationality);
        dob = findViewById(R.id.dob);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        customer_id = findViewById(R.id.customer_id);
        profile = findViewById(R.id.profile);
        name = findViewById(R.id.name);
        guest_list_recyclerView = findViewById(R.id.guest_list_recyclerView);

        String _id = getIntent().getStringExtra("_id");
        String str_customer_id = getIntent().getStringExtra("customer_id");
        customer_id_ser = getIntent().getStringExtra("customer_id");
        String str_name = getIntent().getStringExtra("name");
        String str_mobile = getIntent().getStringExtra("mobile");
        String str_email = getIntent().getStringExtra("email");
        String str_dob = getIntent().getStringExtra("dob");
        String str_nationality = getIntent().getStringExtra("nationality");
        String str_address = getIntent().getStringExtra("address");
        String str_profile = getIntent().getStringExtra("profile");
        String str_nid = getIntent().getStringExtra("nid");
        String str_hotel_id = getIntent().getStringExtra("hotel_id");

        get_activity_data();

        address.setText("ঠিকানাঃ "+str_address);
        nationality.setText("জাতীয়তাঃ "+str_nationality);
        dob.setText("জন্ম তারিখঃ "+str_dob);
        email.setText("ই-মেইলঃ "+str_email);
        mobile.setText("মোবাইলঃ "+str_mobile);
        customer_id.setText("আইডিঃ #"+str_customer_id);
        name.setText("নামঃ "+str_name);


        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.guest_icon)
                .error(R.drawable.guest_icon);
        Glide.with(getApplicationContext()).load(Constraint.ROOT_URL + str_profile).apply(options).into(profile);
        RequestOptions options2 = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.guest_id_icon)
                .error(R.drawable.guest_id_icon);
        Glide.with(getApplicationContext()).load(Constraint.ROOT_URL + str_nid).apply(options2).into(nid);


    }

    private void get_activity_data() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        ProgressDialog dialog = new ProgressDialog(Guest_History.this);
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();
        String url = Constraint.HOTEL_GET_CUSTOMER_HISTORY_INFORMATION;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("data",response);
                model_historyArrayList.clear();
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray room_list = jsonObject.getJSONArray("guest_booked_history");

                    if(room_list.length()!=0) {
                        View no_data_msg = findViewById(R.id.no_data_msg);
                        no_data_msg.setVisibility(View.GONE);
                        guest_list_recyclerView.setVisibility(View.VISIBLE);
                        for (int i = 0; i < room_list.length(); ++i) {
                            JSONObject data_object = room_list.getJSONObject(i);
                            String room_no = data_object.getString("room_no");
                            String entry_date = data_object.getString("entry_date");
                            String leave_date = data_object.getString("leave_date");
                            String room_type = data_object.getString("room_type");
                            String room_facilities = data_object.getString("room_facilities");

                            Model_History roomList = new Model_History(room_no, room_type, room_facilities, entry_date, leave_date);
                            model_historyArrayList.add(0,roomList);
                        }

                        guest_list_recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        adapter_guest_booked_history = new Adapter_Guest_Booked_History(model_historyArrayList);
                        guest_list_recyclerView.setLayoutManager(layoutManager);
                        guest_list_recyclerView.setAdapter(adapter_guest_booked_history);
                    }else{
                        View no_data_msg = findViewById(R.id.no_data_msg);
                        no_data_msg.setVisibility(View.VISIBLE);
                        guest_list_recyclerView.setVisibility(View.GONE);
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
                if (hotel_id.isEmpty() && customer_id_ser.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Something went to wrong!", Toast.LENGTH_SHORT).show();
                } else {
                    params.put("hotel_id", hotel_id);
                    params.put("customer_id", customer_id_ser);
                }

                return params;
            }
        };

        requestQueue.add(request);
    }

    @Override
    protected void onResume() {

        super.onResume();
    }
}
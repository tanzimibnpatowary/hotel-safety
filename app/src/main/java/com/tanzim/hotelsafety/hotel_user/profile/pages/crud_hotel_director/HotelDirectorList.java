package com.tanzim.hotelsafety.hotel_user.profile.pages.crud_hotel_director;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HotelDirectorList extends AppCompatActivity {


    ArrayList<Owner_Model> owner_models = new ArrayList<>();
    Owner_Adapter owner_adapter;
    ProgressDialog dialog;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_owner_list);


    }

    public void volley_run(){
        dialog = new ProgressDialog(this);
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();
        String url = Constraint.HOTEL_DIRECTOR_INFORMATION;
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                dialog.dismiss();
                try {
                    JSONObject object = new JSONObject(string);
                    JSONArray room_list = object.getJSONArray("director_list");
                    if(room_list.length()==0){
                        View view = findViewById(R.id.no_data_msg);
                        view.setVisibility(View.VISIBLE);
                    }else {
                        owner_models.clear();
                        View view = findViewById(R.id.no_data_msg);
                        view.setVisibility(View.GONE);
                        for (int i = 0; i < room_list.length(); ++i) {
                            JSONObject data_object = room_list.getJSONObject(i);
                            String _id = data_object.getString("id");
                            String name = data_object.getString("name");
                            String address = data_object.getString("address");
                            String nid = data_object.getString("nid");
                            String mobile = data_object.getString("mobile");
                            String politics = data_object.getString("politics");
                            String hotel_id = data_object.getString("hotel_id");
                            if (hotel_id.equals(Constraint.HOTEL_ID)) {
                                Owner_Model owner_model = new Owner_Model(_id, name, address, nid, mobile, politics, hotel_id);
                                owner_models.add(0,owner_model);
                            }
                        }
                        recyclerView = findViewById(R.id.recyclerView);
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        owner_adapter = new Owner_Adapter(owner_models);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(owner_adapter);
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


        queue.add(request);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.owner_add,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_owner:
                startActivity(new Intent(this, Crud_Director_Page.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onResume() {
        volley_run();
        super.onResume();
    }

}
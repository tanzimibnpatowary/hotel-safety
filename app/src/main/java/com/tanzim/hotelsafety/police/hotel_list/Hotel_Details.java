package com.tanzim.hotelsafety.police.hotel_list;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gkemon.XMLtoPDF.PdfGenerator;
import com.gkemon.XMLtoPDF.PdfGeneratorListener;
import com.gkemon.XMLtoPDF.model.FailureResponse;
import com.gkemon.XMLtoPDF.model.SuccessResponse;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.tanzim.hotelsafety.R;
import com.tanzim.hotelsafety.app_static.Constraint;
import com.tanzim.hotelsafety.hotel_user.profile.pages.crud_hotel_owner.Crud_Owner_Page;
import com.tanzim.hotelsafety.hotel_user.profile.pages.crud_hotel_owner.Owner_Adapter;
import com.tanzim.hotelsafety.hotel_user.profile.pages.crud_hotel_owner.Owner_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Hotel_Details extends AppCompatActivity {
    String hotel_id ="";
    TextView hotel_name_bn, hotel_name_en, hotel_star, police_station,
            hotel_address, hotel_mobile, hotel_email, hotel_website, hotel_established,
            hotel_social_media, hotel_others_social_media;

    TextView hotel_license_no, hotel_license_date, hotel_trade_license, hotel_TIN,
            hotel_VAT, hotel_BIN, hotel_environment_li_no, hotel_fire_li_no;

    private Bitmap bitmap;
    LinearLayout linearLayout, license_linear, facilities_linear;

    RecyclerView recyclerView_owner,recyclerView_director;

    Adapter_Get_Owner adapter_get_owner, adapter_get_director;
    ArrayList<Owner_Model> owner_models = new ArrayList<>();
    ArrayList<Owner_Model> director_models = new ArrayList<>();
    TextView restaurant, bar, jim, pool, conference, spa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel__details);
        linearLayout = findViewById(R.id.linearLayout);
        license_linear = findViewById(R.id.license_linear);
        facilities_linear = findViewById(R.id.facilities_linear);
        hotel_id = getIntent().getStringExtra("hotel_id");
        //basic information
        hotel_name_bn = findViewById(R.id.hotel_name_bn);
        hotel_name_en = findViewById(R.id.hotel_name_en);
        hotel_star = findViewById(R.id.hotel_star);
        police_station = findViewById(R.id.police_station);
        hotel_address = findViewById(R.id.hotel_address);
        hotel_mobile = findViewById(R.id.hotel_mobile);
        hotel_email = findViewById(R.id.hotel_email);
        hotel_website = findViewById(R.id.hotel_website);
        hotel_established = findViewById(R.id.hotel_established);
        hotel_social_media =findViewById(R.id.hotel_social_media);
        hotel_others_social_media = findViewById(R.id.hotel_others_social_media);

        //license information
        hotel_license_no = findViewById(R.id.hotel_license_no);
        hotel_license_date = findViewById(R.id.hotel_license_date);
        hotel_trade_license = findViewById(R.id.hotel_trade_license);
        hotel_TIN = findViewById(R.id.hotel_TIN);
        hotel_VAT = findViewById(R.id.hotel_VAT);
        hotel_BIN = findViewById(R.id.hotel_BIN);
        hotel_environment_li_no = findViewById(R.id.hotel_environment_li_no);
        hotel_fire_li_no = findViewById(R.id.hotel_fire_li_no);


        //owner information
        recyclerView_owner = findViewById(R.id.recyclerView_owner);
        //director information
        recyclerView_director = findViewById(R.id.recyclerView_director);

        //facilities
        restaurant = findViewById(R.id.restaurant);
        bar = findViewById(R.id.bar);
        jim = findViewById(R.id.jim);
        pool = findViewById(R.id.pool);
        conference = findViewById(R.id.conference);
        spa = findViewById(R.id.spa);

    }
    private void get_facilities_info() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Constraint.HOTEL_GET_FACILITIES_ALL_INFORMATION;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray room_list = jsonObject.getJSONArray("facilities_information");

                    if(room_list.length()==1){
                        facilities_linear.setVisibility(View.VISIBLE);
                        JSONObject arr = room_list.getJSONObject(0);
                        String ser_restuarnt= arr.getString("restuarnt");
                        String ser_bar= arr.getString("bar");
                        String ser_jim= arr.getString("jim");
                        String ser_pool = arr.getString("pool");
                        String ser_conference= arr.getString("conference");
                        String ser_spa= arr.getString("spa");

                        if(ser_restuarnt.equals("0")){
                            restaurant.setText("না");
                        }else if(ser_restuarnt.equals("1")){
                            restaurant.setText("হ্যাঁ");
                        }

                        if(ser_bar.equals("0")){
                            bar.setText("না");
                        }else if(ser_bar.equals("1")){
                            bar.setText("হ্যাঁ");
                        }
                        if(ser_jim.equals("0")){
                            jim.setText("না");
                        }else if(ser_jim.equals("1")){
                            jim.setText("হ্যাঁ");
                        }
                        if(ser_pool.equals("0")){
                            pool.setText("না");
                        }else if(ser_pool.equals("1")){
                            pool.setText("হ্যাঁ");
                        }

                        if(ser_conference.equals("0")){
                            conference.setText("না");
                        }else if(ser_conference.equals("1")){
                            conference.setText("হ্যাঁ");
                        }

                        if(ser_spa.equals("0")){
                            spa.setText("না");
                        }else if(ser_spa.equals("1")){
                            spa.setText("হ্যাঁ");
                        }


                    }else{
                        facilities_linear.setVisibility(View.GONE);
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

                params.put("hotel_id", hotel_id);

                return params;
            }
        };

        requestQueue.add(request);
    }
    private void get_owner_list(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = Constraint.HOTEL_OWNER_INFORMATION;
        StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                try {
                    JSONObject object = new JSONObject(string);
                    owner_models.clear();
                    JSONArray room_list = object.getJSONArray("owner_list");
                    for (int i = 0; i < room_list.length(); ++i) {
                        JSONObject data_object = room_list.getJSONObject(i);
                        String _id = data_object.getString("id");
                        String name = data_object.getString("name");
                        String address = data_object.getString("address");
                        String nid = data_object.getString("nid");
                        String mobile = data_object.getString("mobile");
                        String politics = data_object.getString("politics");
                        String hotel_id = data_object.getString("hotel_id");
                        Owner_Model owner_model = new Owner_Model(_id, name, address, nid, mobile, politics, hotel_id);
                        owner_models.add(0,owner_model);
                    }

                    recyclerView_owner.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    adapter_get_owner = new Adapter_Get_Owner(owner_models);
                    recyclerView_owner.setLayoutManager(layoutManager);
                    recyclerView_owner.setAdapter(adapter_get_owner);

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

                params.put("hotel_id", hotel_id);

                return params;
            }
        };


        queue.add(request);
    }
    private void get_director_list(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = Constraint.HOTEL_DIRECTOR_INFORMATION;
        StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                director_models.clear();
                try {
                    JSONObject object = new JSONObject(string);

                    JSONArray room_list = object.getJSONArray("director_list");
                    for (int i = 0; i < room_list.length(); ++i) {
                        JSONObject data_object = room_list.getJSONObject(i);
                        String _id = data_object.getString("id");
                        String name = data_object.getString("name");
                        String address = data_object.getString("address");
                        String nid = data_object.getString("nid");
                        String mobile = data_object.getString("mobile");
                        String politics = data_object.getString("politics");
                        String hotel_id = data_object.getString("hotel_id");
                        Owner_Model owner_model = new Owner_Model(_id, name, address, nid, mobile, politics, hotel_id);
                        director_models.add(0,owner_model);
                    }

                    recyclerView_director.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    adapter_get_director = new Adapter_Get_Owner(director_models);
                    recyclerView_director.setLayoutManager(layoutManager);
                    recyclerView_director.setAdapter(adapter_get_director);

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

                params.put("hotel_id", hotel_id);

                return params;
            }
        };


        queue.add(request);
    }
    private void get_basic_information() {
        ProgressDialog dialog = new ProgressDialog(Hotel_Details.this);
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Constraint.HOTEL_GET_BASIC_INFORMATION;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray certificates_information = jsonObject.getJSONArray("basic_information");



                    String name_bn = certificates_information.getJSONObject(0).getString("name_bn");
                    String name_en = certificates_information.getJSONObject(0).getString("name_en");
                    String star = certificates_information.getJSONObject(0).getString("star");
                    String thana_name = certificates_information.getJSONObject(0).getString("thana_name");
                    String address = certificates_information.getJSONObject(0).getString("address");
                    String mobile = certificates_information.getJSONObject(0).getString("mobile");
                    String email= certificates_information.getJSONObject(0).getString("email");
                    String website = certificates_information.getJSONObject(0).getString("website");
                    String fb_page = certificates_information.getJSONObject(0).getString("fb_page");
                    String other_link = certificates_information.getJSONObject(0).getString("other_link");
                    String established = certificates_information.getJSONObject(0).getString("established");




                    hotel_name_bn.setText("নাম (বাংলা): "+name_bn);
                    hotel_name_en.setText("নাম (ইংরেজি): "+name_en);
                    hotel_star.setText("তারকাক্রমঃ "+star);
                    police_station.setText("থানাঃ "+thana_name);
                    hotel_address.setText("ঠিকানাঃ "+address);
                    hotel_mobile.setText("মোবাইলঃ "+mobile);
                    hotel_email.setText("ই-মেইলঃ "+email);
                    hotel_website.setText("ওয়েবসাইটঃ "+website);
                    hotel_established.setText("প্রতিষ্ঠার সনঃ "+established);
                    hotel_social_media.setText("ফেসবুক পেইজঃ "+fb_page);
                    hotel_others_social_media.setText("অন্যান্য মিডিয়াঃ "+other_link);



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

                params.put("hotel_id", hotel_id);

                return params;
            }
        };

        requestQueue.add(request);
    }
    private void get_license_information() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Constraint.HOTEL_GET_LICENSE_ALL_INFORMATION;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray certificates_information = jsonObject.getJSONArray("certificates_information");



                    if(certificates_information.length()==0){
                        license_linear.setVisibility(View.GONE);
                    }else{
                        license_linear.setVisibility(View.VISIBLE);
                        String ser_register_no = certificates_information.getJSONObject(0).getString("register_no");
                        String ser_register_date = certificates_information.getJSONObject(0).getString("register_date");
                        String ser_trade_no = certificates_information.getJSONObject(0).getString("trade_no");
                        String ser_tin_no = certificates_information.getJSONObject(0).getString("tin_no");
                        String ser_vat_no = certificates_information.getJSONObject(0).getString("vat_no");
                        String ser_bin_no = certificates_information.getJSONObject(0).getString("bin_no");
                        String ser_social_no= certificates_information.getJSONObject(0).getString("social_no");
                        String ser_fire_no = certificates_information.getJSONObject(0).getString("fire_no");


                        hotel_license_no.setText("রেজি নংঃ "+ser_register_no);
                        hotel_license_date.setText("রেজি তারিখঃ "+ser_register_date);
                        hotel_trade_license.setText("ট্রেড নংঃ "+ser_trade_no);
                        hotel_TIN.setText("TIN নংঃ "+ser_tin_no);
                        hotel_VAT.setText("VAT নংঃ "+ser_vat_no);
                        hotel_BIN.setText("BIN নংঃ "+ser_bin_no);
                        hotel_environment_li_no.setText("পরিবেশ অধিদপ্তর নিবন্ধনঃ "+ser_social_no);
                        hotel_fire_li_no.setText("ফায়ার সার্ভিস নংঃ "+ser_fire_no);
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

                params.put("hotel_id", hotel_id);

                return params;
            }
        };

        requestQueue.add(request);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save_to_pdf,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_to_pdf:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onResume() {
        get_director_list();
        get_owner_list();
        get_basic_information();
        get_license_information();
        get_facilities_info();
        super.onResume();
    }

}
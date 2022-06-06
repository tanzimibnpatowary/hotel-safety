package com.tanzim.hotelsafety.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.tanzim.hotelsafety.R;
import com.tanzim.hotelsafety.app_static.Constraint;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.tanzim.hotelsafety.app_static.Constraint.HOTEL_ID;

public class Add_Hotel_Room extends DialogFragment {
    public static final String HOTEL_ROOM_ADD = "HOTEL_ROOM_ADD";
    public static final String HOTEL_ROOM_UPDATE= "HOTEL_ROOM_UPDATE";
    private Add_Hotel.OnClickListener listener;
    private String[] ac_non_ac_array = {"এসি","নন এসি"};
    private String[] bed_type_array = {"সিঙ্গেল","ডাবল","ট্রিপল"};
    TextInputEditText add_hotel_room_number;
    AutoCompleteTextView hotel_bed_category_ac_non_ac, hotel_bed_category_double;
    public interface OnClickListener {
        void onClick(String str1, String st2);
    }
    public void setListener(Add_Hotel.OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = null;
        if (getTag().equals(HOTEL_ROOM_ADD)) {
            dialog = hotel_room_Add();
        }
        if(getTag().equals(HOTEL_ROOM_UPDATE)){
            dialog = hotel_room_Update();
        }
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;
    }

    private Dialog hotel_room_Update() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_hotel_room, null);
        builder.setView(view);
        return builder.create();
    }

    private Dialog hotel_room_Add() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_hotel_room, null);

        add_hotel_room_number = view.findViewById(R.id.add_hotel_room_number);
        hotel_bed_category_ac_non_ac = view.findViewById(R.id.hotel_bed_category_ac_non_ac);
        hotel_bed_category_double = view.findViewById(R.id.hotel_bed_category_double);
        Button cancel = view.findViewById(R.id.btn_cancel);

        Button submit = view.findViewById(R.id.btn_submit);

        hotel_bed_category_ac_non_ac.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.dropdown_menu_popup_item,ac_non_ac_array));
        hotel_bed_category_double.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.dropdown_menu_popup_item,bed_type_array));
        cancel.setOnClickListener(v -> dismiss());
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_add_hotel_room_number = add_hotel_room_number.getText().toString();
                String str_hotel_bed_category_ac_non_ac = hotel_bed_category_ac_non_ac.getText().toString();
                String str_hotel_bed_category_double = hotel_bed_category_double.getText().toString();

                if(str_add_hotel_room_number.isEmpty()){
                    add_hotel_room_number.setError(getString(R.string.add_room_number_required));
                    return;
                }

                if(str_hotel_bed_category_ac_non_ac.isEmpty()){
                    hotel_bed_category_ac_non_ac.setError(getString(R.string.select_required));
                    return;
                }

                if(str_hotel_bed_category_double.isEmpty()){
                    hotel_bed_category_double.setError(getString(R.string.select_required));
                    return;
                }

                if(str_hotel_bed_category_ac_non_ac.equals(ac_non_ac_array[0])){
                    str_hotel_bed_category_ac_non_ac = "1";
                }
                if(str_hotel_bed_category_ac_non_ac.equals(ac_non_ac_array[1])){
                    str_hotel_bed_category_ac_non_ac = "2";
                }

                if(str_hotel_bed_category_double.equals(bed_type_array[0])){
                    str_hotel_bed_category_double = "1";
                }

                if(str_hotel_bed_category_double.equals(bed_type_array[1])){
                    str_hotel_bed_category_double = "2";
                }

                if(str_hotel_bed_category_double.equals(bed_type_array[2])){
                    str_hotel_bed_category_double = "3";
                }

                submit_data_to_server(str_add_hotel_room_number, str_hotel_bed_category_ac_non_ac, str_hotel_bed_category_double);


            }
        });

        builder.setView(view);
        return builder.create();
    }


    void submit_data_to_server(String str_add_hotel_room_number, String str_hotel_bed_category_ac_non_ac, String str_hotel_bed_category_double){

        final  ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = Constraint.HOTEL_SET_NEW_ROOM;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    if(!jsonObject.getString("error").equals("true")) {
                        hotel_bed_category_double.getText().clear();
                        hotel_bed_category_ac_non_ac.getText().clear();
                        add_hotel_room_number.getText().clear();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
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
                if(HOTEL_ID.isEmpty()){
                    Toast.makeText(getContext(),"Please login again! Hotel ID missing.",Toast.LENGTH_SHORT).show();
                }else{
                    params.put("str_add_hotel_room_number", str_add_hotel_room_number);
                    params.put("str_hotel_bed_category_ac_non_ac", str_hotel_bed_category_ac_non_ac);
                    params.put("str_hotel_bed_category_double", str_hotel_bed_category_double);
                    params.put("hotel_id", HOTEL_ID);

                }
                return params;
            }
        };

        requestQueue.add(request);


    }

}

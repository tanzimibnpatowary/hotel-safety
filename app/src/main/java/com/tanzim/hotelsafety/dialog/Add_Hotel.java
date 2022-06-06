package com.tanzim.hotelsafety.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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
import com.tanzim.hotelsafety.hotel_user.profile.pages.crud_hotel_employee.Crud_Employee_Page;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Add_Hotel extends DialogFragment {
    public static final String HOTEL_ADD = "HOTEL_ADD";
    public static final String HOTEL_UPDATE= "HOTEL_UPDATE";
    private OnClickListener listener;


    public interface OnClickListener {
        void onClick(String str1, String st2);
    }
    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = null;
        if (getTag().equals(HOTEL_ADD)) {
            dialog = hotel_Add();
        }
        if(getTag().equals(HOTEL_UPDATE)){
            dialog = hotel_Update();
        }
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;
    }

    private Dialog hotel_Update() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_hotel, null);

        builder.setView(view);
        return builder.create();
    }

    private void submit_to_server(String str_hotel_name_bn, String str_hotel_name_en, String str_hotel_contact, String str_hotel_location) {
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = Constraint.ADMIN_ADD_NEW_HOTEL;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    dialog.dismiss();
                    Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();;
                } catch (JSONException e) {
                    e.printStackTrace();
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

                params.put("mobile", str_hotel_contact);
                params.put("name_bn", str_hotel_name_bn);
                params.put("name_en", str_hotel_name_en);
                params.put("address", str_hotel_location);


                return params;
            }
        };

        requestQueue.add(request);
    }

    private Dialog hotel_Add() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_hotel, null);
        TextInputEditText edit_hotel_name_bn = view.findViewById(R.id.edit_hotel_name_bn);
        TextInputEditText edit_hotel_name_en = view.findViewById(R.id.edit_hotel_name_en);
        TextInputEditText edit_hotel_contact = view.findViewById(R.id.edit_hotel_contact);
        TextInputEditText edit_hotel_location = view.findViewById(R.id.edit_hotel_location);

        Button save_btn = view.findViewById(R.id.save_btn);




        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_hotel_name_bn = edit_hotel_name_bn.getText().toString();
                String str_hotel_name_en = edit_hotel_name_en.getText().toString();
                String str_hotel_contact = edit_hotel_contact.getText().toString();
                String str_hotel_location = edit_hotel_location.getText().toString();

                if(str_hotel_name_bn.isEmpty()){
                    edit_hotel_name_bn.setError(getString(R.string.required));
                    return;
                }
                if(str_hotel_name_en.isEmpty()){
                    edit_hotel_name_en.setError(getString(R.string.required));
                    return;
                }
                if(str_hotel_contact.isEmpty()){
                    edit_hotel_contact.setError(getString(R.string.required));
                    return;
                }
                if(str_hotel_location.isEmpty()){
                    edit_hotel_location.setError(getString(R.string.required));
                    return;
                }

                submit_to_server(str_hotel_name_bn, str_hotel_name_en, str_hotel_contact, str_hotel_location);

            }
        });


        builder.setView(view);
        return builder.create();
    }

}

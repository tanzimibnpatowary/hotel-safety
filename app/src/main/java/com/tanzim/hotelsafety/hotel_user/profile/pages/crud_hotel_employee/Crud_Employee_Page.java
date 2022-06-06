package com.tanzim.hotelsafety.hotel_user.profile.pages.crud_hotel_employee;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class Crud_Employee_Page extends AppCompatActivity {

    Button btn_submit;
    TextInputEditText hotel_foreigners_name, hotel_foreigners_address,hotel_foreigners_mobile, hotel_foreigners_email,
            hotel_foreigners_passport, hotel_foreigners_passport_issue, hotel_foreigners_passport_expired,hotel_foreigners_visa,
    hotel_foreigners_visa_expired, hotel_foreigners_security_papers;
    final Calendar myCalendar= Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_foreigners);


        btn_submit = findViewById(R.id.btn_submit);

        hotel_foreigners_name = findViewById(R.id.hotel_foreigners_name);
        hotel_foreigners_address = findViewById(R.id.hotel_foreigners_address);
        hotel_foreigners_mobile = findViewById(R.id.hotel_foreigners_mobile);
        hotel_foreigners_email = findViewById(R.id.hotel_foreigners_email);
        hotel_foreigners_passport = findViewById(R.id.hotel_foreigners_passport);
        hotel_foreigners_passport_issue = findViewById(R.id.hotel_foreigners_passport_issue);
        hotel_foreigners_passport_expired = findViewById(R.id.hotel_foreigners_passport_expired);
        hotel_foreigners_visa = findViewById(R.id.hotel_foreigners_visa);
        hotel_foreigners_visa_expired = findViewById(R.id.hotel_foreigners_visa_expired);
        hotel_foreigners_security_papers = findViewById(R.id.hotel_foreigners_security_papers);



        DatePickerDialog.OnDateSetListener passport_issue =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                hotel_foreigners_passport_issue.setText(updateLabel());
            }
        };
        hotel_foreigners_passport_issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Crud_Employee_Page.this,passport_issue,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        DatePickerDialog.OnDateSetListener passport_expire =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                hotel_foreigners_passport_expired.setText(updateLabel());
            }
        };
        hotel_foreigners_passport_expired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Crud_Employee_Page.this,passport_expire,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        DatePickerDialog.OnDateSetListener visa_expired =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                hotel_foreigners_visa_expired.setText(updateLabel());
            }
        };
        hotel_foreigners_visa_expired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Crud_Employee_Page.this,visa_expired,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = hotel_foreigners_name.getText().toString();
                String address = hotel_foreigners_address.getText().toString();
                String mobile = hotel_foreigners_mobile.getText().toString();
                String email = hotel_foreigners_email.getText().toString();
                String passport_no = hotel_foreigners_passport.getText().toString();
                String passport_issue = hotel_foreigners_passport_issue.getText().toString();
                String passport_expired = hotel_foreigners_passport_expired.getText().toString();
                String visa_no = hotel_foreigners_visa.getText().toString();
                String visa_expired = hotel_foreigners_visa_expired.getText().toString();
                String security_papers = hotel_foreigners_security_papers.getText().toString();

                if(name.isEmpty()){
                    hotel_foreigners_name.setError(getString(R.string.required));
                    return;
                }
                if(address.isEmpty()){
                    hotel_foreigners_address.setError(getString(R.string.required));
                    return;
                }
                if(mobile.isEmpty()){
                    hotel_foreigners_mobile.setError(getString(R.string.required));
                    return;
                }
                if(email.isEmpty()){
                    hotel_foreigners_email.setError(getString(R.string.required));
                    return;
                }
                if(passport_no.isEmpty()){
                    hotel_foreigners_passport.setError(getString(R.string.required));
                    return;
                }
                if(passport_issue.isEmpty()){
                    hotel_foreigners_passport_issue.setError(getString(R.string.required));
                    return;
                }
                if(passport_expired.isEmpty()){
                    hotel_foreigners_passport_expired.setError(getString(R.string.required));
                    return;
                }
                if(visa_no.isEmpty()){
                    hotel_foreigners_visa.setError(getString(R.string.required));
                    return;
                }
                if(visa_expired.isEmpty()){
                    hotel_foreigners_visa_expired.setError(getString(R.string.required));
                    return;
                }
                if(security_papers.isEmpty()){
                    hotel_foreigners_security_papers.setError(getString(R.string.required));
                    return;
                }


                submit_hotel_employee(name, address, mobile, email, passport_no, passport_issue, passport_expired, visa_no, visa_expired, security_papers);
            }
        });



    }

    private void submit_hotel_employee(String name, String address, String mobile, String email, String passport_no, String passport_issue,
                                       String passport_expired, String visa_no, String visa_expired, String security_papers) {
        ProgressDialog dialog = new ProgressDialog(Crud_Employee_Page.this);
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();



        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Constraint.HOTEL_SET_EMPLOYEE_INFORMATION;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    hotel_foreigners_name.getText().clear();
                    hotel_foreigners_address.getText().clear();
                    hotel_foreigners_mobile.getText().clear();
                    hotel_foreigners_email.getText().clear();
                    hotel_foreigners_passport.getText().clear();
                    hotel_foreigners_passport_issue.getText().clear();
                    hotel_foreigners_passport_expired.getText().clear();
                    hotel_foreigners_visa.getText().clear();
                    hotel_foreigners_visa_expired.getText().clear();
                    hotel_foreigners_security_papers.getText().clear();

                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();;
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

                String hotel_id = Constraint.HOTEL_ID;
                if(hotel_id.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Something went to wrong!",Toast.LENGTH_SHORT).show();
                }else {

                    params.put("name", name);
                    params.put("address", address);
                    params.put("mobile", mobile);
                    params.put("email", email);
                    params.put("passport_no", passport_no);
                    params.put("passport_issue", passport_issue);
                    params.put("passport_expired", passport_expired);
                    params.put("visa_no", visa_no);
                    params.put("visa_expired", visa_expired);
                    params.put("security_papers", security_papers);
                    params.put("hotel_id", hotel_id);
                }

                return params;
            }
        };

        requestQueue.add(request);
    }

    private String updateLabel(){
        String myFormat="dd/MM/yyyy";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Dhaka"));
        return dateFormat.format(myCalendar.getTime());
    }


}
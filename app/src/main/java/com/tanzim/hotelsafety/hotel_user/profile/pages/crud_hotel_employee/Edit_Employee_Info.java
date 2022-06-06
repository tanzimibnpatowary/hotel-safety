package com.tanzim.hotelsafety.hotel_user.profile.pages.crud_hotel_employee;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Edit_Employee_Info extends AppCompatActivity {
    TextInputEditText hotel_owner_name, hotel_owner_address, hotel_owner_nid, hotel_owner_mobile, hotel_owner_politics;
    Button btn_submit;
    String in_hotel_id, in_id = "";

    TextInputEditText hotel_foreigners_name, hotel_foreigners_address,hotel_foreigners_mobile, hotel_foreigners_email,
            hotel_foreigners_passport, hotel_foreigners_passport_issue, hotel_foreigners_passport_expired,hotel_foreigners_visa,
            hotel_foreigners_visa_expired, hotel_foreigners_security_papers;
    final Calendar myCalendar= Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_foreigners);
        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setText(getString(R.string.update));


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

        in_id = getIntent().getStringExtra("id");
        String name = getIntent().getStringExtra("name");
        String address = getIntent().getStringExtra("address");
        String mobile = getIntent().getStringExtra("mobile");
        String email= getIntent().getStringExtra("email");
        String passport_no = getIntent().getStringExtra("passport_no");
        String passport_issue = getIntent().getStringExtra("passport_issue");
        String passport_expired = getIntent().getStringExtra("passport_expired");
        String visa_no = getIntent().getStringExtra("visa_no");
        String visa_expired = getIntent().getStringExtra("visa_expired");
        String security_papers = getIntent().getStringExtra("security_papers");
        in_hotel_id = getIntent().getStringExtra("hotel_id");


        hotel_foreigners_name.setText(name);
        hotel_foreigners_address.setText(address);
        hotel_foreigners_mobile.setText(mobile);
        hotel_foreigners_email.setText(email);
        hotel_foreigners_passport.setText(passport_no);
        hotel_foreigners_passport_issue.setText(passport_issue);
        hotel_foreigners_passport_expired.setText(passport_expired);
        hotel_foreigners_visa.setText(visa_no);
        hotel_foreigners_visa_expired.setText(visa_expired);
        hotel_foreigners_security_papers.setText(security_papers);




        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = hotel_foreigners_name.getText().toString();
                String address = hotel_foreigners_address.getText().toString();
                String mobile = hotel_foreigners_mobile.getText().toString();
                String email = hotel_foreigners_email.getText().toString();
                String passport = hotel_foreigners_passport.getText().toString();
                String passport_issue = hotel_foreigners_passport_issue.getText().toString();
                String passport_expire = hotel_foreigners_passport_expired.getText().toString();
                String visa_no = hotel_foreigners_visa.getText().toString();
                String visa_expire = hotel_foreigners_visa_expired.getText().toString();
                String security_paper = hotel_foreigners_security_papers.getText().toString();



                if (name.isEmpty()) {hotel_owner_name.setError(getString(R.string.required)); return;}
                if (address.isEmpty()) {hotel_foreigners_address.setError(getString(R.string.required)); return;}
                if (mobile.isEmpty()) {hotel_foreigners_mobile.setError(getString(R.string.required)); return;}
                if (email.isEmpty()) {hotel_foreigners_email.setError(getString(R.string.required)); return;}
                if (passport.isEmpty()) {hotel_foreigners_passport.setError(getString(R.string.required)); return;}
                if (passport_issue.isEmpty()) {hotel_foreigners_passport_issue.setError(getString(R.string.required)); return;}
                if (passport_expire.isEmpty()) {hotel_foreigners_passport_expired.setError(getString(R.string.required)); return;}
                if (visa_no.isEmpty()) {hotel_foreigners_visa.setError(getString(R.string.required)); return;}
                if (visa_expire.isEmpty()) {hotel_foreigners_visa_expired.setError(getString(R.string.required)); return;}
                if (security_paper.isEmpty()) {hotel_foreigners_security_papers.setError(getString(R.string.required)); return;}


                update_hotel_employee(name, address, mobile, email, passport, passport_issue, passport_expire, visa_no, visa_expire, security_paper);
            }
        });


    }

    private void update_hotel_employee(String name, String address, String mobile, String email, String passport, String passport_issue,
                                       String passport_expire, String visa_no, String visa_expire, String security_paper) {



        ProgressDialog dialog = new ProgressDialog(Edit_Employee_Info.this);
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Constraint.HOTEL_UPDATE_EMPLOYEE_INFORMATION;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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

                //String hotel_id = Constraint.HOTEL_ID;
                if (in_hotel_id.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Something went to wrong!", Toast.LENGTH_SHORT).show();
                } else {

                    params.put("name", name);
                    params.put("address", address);
                    params.put("mobile", mobile);
                    params.put("email", email);
                    params.put("passport", passport);
                    params.put("passport_issue", passport_issue);
                    params.put("passport_expire", passport_expire);
                    params.put("visa_no", visa_no);
                    params.put("visa_expire", visa_expire);
                    params.put("security_paper", security_paper);
                    params.put("hotel_id", in_hotel_id);
                    params.put("id", in_id);


                }

                return params;
            }
        };

        requestQueue.add(request);
    }


}

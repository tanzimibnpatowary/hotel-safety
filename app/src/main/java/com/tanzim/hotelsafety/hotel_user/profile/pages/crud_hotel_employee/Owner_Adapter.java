package com.tanzim.hotelsafety.hotel_user.profile.pages.crud_hotel_employee;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.tanzim.hotelsafety.hotel_user.profile.pages.crud_hotel_owner.Edit_Owner_Info;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Owner_Adapter extends RecyclerView.Adapter<Owner_Adapter.OwnerListViewholder> {
    Context context;
    ArrayList<Employee_Model> owner_models = new ArrayList<>();
    TextInputEditText hotel_foreigners_name, hotel_foreigners_address,hotel_foreigners_mobile, hotel_foreigners_email,
            hotel_foreigners_passport, hotel_foreigners_passport_issue, hotel_foreigners_passport_expired,hotel_foreigners_visa,
            hotel_foreigners_visa_expired, hotel_foreigners_security_papers;

    public Owner_Adapter(ArrayList<Employee_Model> owner_models) {
        this.owner_models = owner_models;
    }

    @NonNull
    @Override
    public OwnerListViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_employee_list,parent,false);
        context = parent.getContext();
        return new OwnerListViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnerListViewholder holder, int position) {
        Employee_Model current = owner_models.get(position);
        holder.name.setText("নামঃ "+current.getName());
        holder.mobile.setText("মোবাইলঃ "+current.getMobile());
        holder.passport_no.setText("পাসপোর্টঃ "+current.getPassport_no());
        holder.passport_expire.setText("পাসপোর্ট মেয়াদঃ "+current.getPassport_expired());

        holder.visa_no.setText("ভিসাঃ "+current.getVisa_no());
        holder.visa_expire.setText("ভিসা মেয়াদঃ "+current.getVisa_expired());



        holder.see_btn.setOnClickListener(v -> show_data(current.getName(), current.getAddress(), current.getMobile(), current.getEmail(),
                current.getPassport_no(), current.getPassport_issue(), current.getPassport_expired(), current.getVisa_no(), current.getVisa_expired(), current.getSecurity_papers()));
        holder.delete_btn.setOnClickListener(v -> delete_info(current.get_id()));
        holder.edit_btn.setOnClickListener(v -> edit_info(current.get_id(), current.getName(), current.getAddress(), current.getMobile(), current.getEmail(),
                current.getPassport_no(), current.getPassport_issue(), current.getPassport_expired(), current.getVisa_no(), current.getVisa_expired(), current.getSecurity_papers(), current.getHotel_id()));
    }

    private void show_data(String name, String address, String mobile, String email, String passport_no, String passport_issue,
                           String passport_expired, String visa_no, String visa_expired, String security_papers) {
        Dialog alertDialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_foreigners,null);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(view);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button cancel_action = view.findViewById(R.id.cancel_action);
        hotel_foreigners_name = view.findViewById(R.id.hotel_foreigners_name);
        hotel_foreigners_address = view.findViewById(R.id.hotel_foreigners_address);
        hotel_foreigners_mobile = view.findViewById(R.id.hotel_foreigners_mobile);
        hotel_foreigners_email = view.findViewById(R.id.hotel_foreigners_email);
        hotel_foreigners_passport = view.findViewById(R.id.hotel_foreigners_passport);
        hotel_foreigners_passport_issue = view.findViewById(R.id.hotel_foreigners_passport_issue);
        hotel_foreigners_passport_expired = view.findViewById(R.id.hotel_foreigners_passport_expired);
        hotel_foreigners_visa = view.findViewById(R.id.hotel_foreigners_visa);
        hotel_foreigners_visa_expired = view.findViewById(R.id.hotel_foreigners_visa_expired);
        hotel_foreigners_security_papers = view.findViewById(R.id.hotel_foreigners_security_papers);

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


        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void edit_info(String id, String name, String address, String mobile, String email, String passport_no, String passport_issue,
                           String passport_expired, String visa_no, String visa_expired, String security_papers, String hotel_id) {
        Intent intent = new Intent(context.getApplicationContext(), Edit_Employee_Info.class);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        intent.putExtra("address", address);
        intent.putExtra("mobile", mobile);
        intent.putExtra("email", email);
        intent.putExtra("passport_no", passport_no);
        intent.putExtra("passport_issue", passport_issue);
        intent.putExtra("passport_expired", passport_expired);
        intent.putExtra("visa_no", visa_no);
        intent.putExtra("visa_expired", visa_expired);
        intent.putExtra("security_papers", security_papers);
        intent.putExtra("hotel_id", hotel_id);
        context.startActivity(intent);
    }




    private void delete_info(String _id) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = Constraint.HOTEL_DELETE_EMPLOYEE_INFORMATION;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    ((HotelEmployeeList)context).volley_run();
                    notifyDataSetChanged();
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
                if (_id.isEmpty()) {
                    Toast.makeText(context, "Something went to wrong!", Toast.LENGTH_SHORT).show();
                } else {

                    params.put("id", _id);
                }

                return params;
            }
        };

        requestQueue.add(request);
    }

    @Override
    public int getItemCount() {
        return owner_models.size();
    }

    public class OwnerListViewholder extends RecyclerView.ViewHolder {
        TextView name,mobile, passport_no, passport_expire, visa_no, visa_expire;
        ImageView edit_btn, delete_btn, see_btn;
        public OwnerListViewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            mobile = itemView.findViewById(R.id.mobile);
            passport_no = itemView.findViewById(R.id.passport_no);
            passport_expire = itemView.findViewById(R.id.passport_expire);
            visa_no = itemView.findViewById(R.id.visa_no);
            visa_expire = itemView.findViewById(R.id.visa_expire);
            edit_btn = itemView.findViewById(R.id.edit_btn);
            delete_btn = itemView.findViewById(R.id.delete_btn);
            see_btn = itemView.findViewById(R.id.see_btn);
        }
    }
}

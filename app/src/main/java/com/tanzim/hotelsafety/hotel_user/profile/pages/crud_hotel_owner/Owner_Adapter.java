package com.tanzim.hotelsafety.hotel_user.profile.pages.crud_hotel_owner;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.tanzim.hotelsafety.R;
import com.tanzim.hotelsafety.app_static.Constraint;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Owner_Adapter extends RecyclerView.Adapter<Owner_Adapter.OwnerListViewholder> {
    Context context;
    ArrayList<Owner_Model> owner_models = new ArrayList<>();

    public Owner_Adapter(ArrayList<Owner_Model> owner_models) {
        this.owner_models = owner_models;
    }

    @NonNull
    @Override
    public OwnerListViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_owner_list,parent,false);
        context = parent.getContext();
        return new OwnerListViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnerListViewholder holder, int position) {
        Owner_Model current = owner_models.get(position);
        holder.name.setText("নামঃ "+current.getName());
        holder.nid.setText("এন.আই.ডিঃ "+current.getNid());
        holder.mobile.setText("মোবাইলঃ "+current.getMobile());
        holder.politics.setText("রাজনৈতিক পরিচয়ঃ "+current.getPolitics());
        holder.address.setText("ঠিকানাঃ "+current.getAddress());
        holder.delete_btn.setOnClickListener(v -> delete_info(current.get_id()));
        holder.edit_btn.setOnClickListener(v -> edit_info(current.get_id(), current.getHotel_id(), current.getName(),current.getNid(),current.getMobile(),
                current.getAddress(),current.getPolitics()));
    }

    private void edit_info(String id, String hotel_id, String name, String nid, String mobile, String address, String politics) {
        Intent intent = new Intent(context.getApplicationContext(),Edit_Owner_Info.class);
        intent.putExtra("id", id);
        intent.putExtra("hotel_id", hotel_id);
        intent.putExtra("name", name);
        intent.putExtra("nid", nid);
        intent.putExtra("mobile", mobile);
        intent.putExtra("address", address);
        intent.putExtra("politics", politics);
        context.startActivity(intent);
    }


    private void delete_info(String _id) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = Constraint.HOTEL_DELETE_OWNER_INFORMATION;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    ((HotelOwnerList)context).volley_run();
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
        TextView name, nid, mobile, politics, address;
        ImageView edit_btn, delete_btn;
        public OwnerListViewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            nid = itemView.findViewById(R.id.nid);
            mobile = itemView.findViewById(R.id.mobile);
            politics = itemView.findViewById(R.id.politics);
            address = itemView.findViewById(R.id.address);
            edit_btn = itemView.findViewById(R.id.edit_btn);
            delete_btn = itemView.findViewById(R.id.delete_btn);
        }
    }
}

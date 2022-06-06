package com.tanzim.hotelsafety.police.hotel_list;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.tanzim.hotelsafety.hotel_user.profile.pages.crud_hotel_owner.Edit_Owner_Info;
import com.tanzim.hotelsafety.hotel_user.profile.pages.crud_hotel_owner.HotelOwnerList;
import com.tanzim.hotelsafety.hotel_user.profile.pages.crud_hotel_owner.Owner_Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Adapter_Get_Owner extends RecyclerView.Adapter<Adapter_Get_Owner.OwnerListViewholder> {
    Context context;
    ArrayList<Owner_Model> owner_models = new ArrayList<>();

    public Adapter_Get_Owner(ArrayList<Owner_Model> owner_models) {
        this.owner_models = owner_models;
    }

    @NonNull
    @Override
    public OwnerListViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_admin_owner_list,parent,false);
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

    }



    @Override
    public int getItemCount() {
        return owner_models.size();
    }

    public class OwnerListViewholder extends RecyclerView.ViewHolder {
        TextView name, nid, mobile, politics, address;
        public OwnerListViewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            nid = itemView.findViewById(R.id.nid);
            mobile = itemView.findViewById(R.id.mobile);
            politics = itemView.findViewById(R.id.politics);
            address = itemView.findViewById(R.id.address);

        }
    }
}

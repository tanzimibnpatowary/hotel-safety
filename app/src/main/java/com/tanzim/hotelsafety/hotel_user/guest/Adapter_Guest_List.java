package com.tanzim.hotelsafety.hotel_user.guest;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.tanzim.hotelsafety.R;
import com.tanzim.hotelsafety.app_static.Constraint;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_Guest_List extends RecyclerView.Adapter<Adapter_Guest_List.Guest_List_ViewHolder> {

    public ArrayList<Model_Guest_List> model_guest_lists;
    Context context;
    public Adapter_Guest_List(ArrayList<Model_Guest_List> model_guest_lists) {
        this.model_guest_lists = model_guest_lists;
    }

    @NonNull
    @Override
    public Adapter_Guest_List.Guest_List_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_guest_list,parent,false);
        context = parent.getContext();
        return new Guest_List_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Guest_List.Guest_List_ViewHolder holder, int position) {
        Model_Guest_List model_guest_list = model_guest_lists.get(position);
        holder.customer_id.setText("গেস্টের তথ্য | আইডি নংঃ #"+model_guest_list.getCustomer_id());
        holder.name.setText("নামঃ "+model_guest_list.getName()+" ("+model_guest_list.getNationality()+")");
        holder.mobile.setText("মোবাইলঃ "+model_guest_list.getMobile());
        holder.address.setText("ঠিকানাঃ "+model_guest_list.getAddress());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.guest_icon)
                .error(R.drawable.guest_icon);
        Glide.with(context).load(Constraint.ROOT_URL+model_guest_list.getProfile()).apply(options).into(holder.guest_picture);

        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent history = new Intent(view.getContext(), Guest_History.class);
                history.putExtra("_id",model_guest_list.getId());
                history.putExtra("customer_id",model_guest_list.getCustomer_id());
                history.putExtra("name",model_guest_list.getName());
                history.putExtra("mobile",model_guest_list.getMobile());
                history.putExtra("email",model_guest_list.getEmail());
                history.putExtra("dob",model_guest_list.getDob());
                history.putExtra("nationality",model_guest_list.getNationality());
                history.putExtra("address",model_guest_list.getAddress());
                history.putExtra("profile",model_guest_list.getProfile());
                history.putExtra("nid",model_guest_list.getNid());
                history.putExtra("hotel_id",model_guest_list.getHotel_id());
                view.getContext().startActivity(history);


//                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                final CharSequence[] dialog_item = {"Show Details","Edit", "Delete"};
//                builder.setItems(dialog_item, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        switch (i){
//
//                            case 0 :
//
//                                break;
//                        }
//                    }
//                });
//
//                builder.create().show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return model_guest_lists.size();
    }

    public class Guest_List_ViewHolder extends RecyclerView.ViewHolder {
        TextView name, mobile, address, customer_id;
        CircleImageView guest_picture;
        LinearLayout linear;
        public Guest_List_ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            mobile = itemView.findViewById(R.id.mobile);
            address = itemView.findViewById(R.id.address);
            customer_id = itemView.findViewById(R.id.customer_id);
            guest_picture = itemView.findViewById(R.id.guest_picture);
            linear = itemView.findViewById(R.id.linear);


        }
    }
}


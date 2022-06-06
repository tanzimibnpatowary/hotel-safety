package com.tanzim.hotelsafety.police.hotel_list;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tanzim.hotelsafety.R;

import java.util.ArrayList;

public class Adapter_Hotel_List extends RecyclerView.Adapter<Adapter_Hotel_List.HotelListViewHolder> {

    ArrayList<Model_Hotel_List> model_hotel_lists;
    Context context;
    public Adapter_Hotel_List(ArrayList<Model_Hotel_List> model_hotel_lists) {
        this.model_hotel_lists = model_hotel_lists;
    }

    @NonNull
    @Override
    public HotelListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_hotel_list,parent,false);
        return new HotelListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelListViewHolder holder, int position) {
        Model_Hotel_List current = model_hotel_lists.get(position);

        holder.hotel_name.setText("নামঃ "+current.getHotel_name_bn()+" ("+current.getHotel_name_en()+")");
        holder.hotel_address.setText("ঠিকানাঃ "+current.getAddress());
        holder.mobile.setText("মোবাইলঃ "+current.getMobile());
        holder.hotel_id.setText("আইডিঃ #"+current.getHotel_id());

        holder.linearLayout.setOnClickListener(v -> next_page(current.getHotel_id()));


    }

    private void next_page(String hotel_id) {

        Intent intent = new Intent(context,Hotel_Details.class);
        intent.putExtra("hotel_id",hotel_id);


        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return model_hotel_lists.size();
    }

    public class HotelListViewHolder extends RecyclerView.ViewHolder {
        TextView hotel_name, hotel_address, mobile, hotel_id;
        LinearLayout linearLayout;
        public HotelListViewHolder(@NonNull View itemView) {
            super(itemView);

            hotel_name = itemView.findViewById(R.id.hotel_name);
            hotel_address = itemView.findViewById(R.id.hotel_address);
            mobile = itemView.findViewById(R.id.mobile);
            hotel_id = itemView.findViewById(R.id.hotel_id);
            linearLayout = itemView.findViewById(R.id.linear);

        }
    }
}

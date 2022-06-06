package com.tanzim.hotelsafety.police.booking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tanzim.hotelsafety.R;
import com.tanzim.hotelsafety.hotel_user.hotel_home.Adapter_Booked_Room_List;
import com.tanzim.hotelsafety.hotel_user.hotel_home.Model_Room_List;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Adapter_All_Booked_List extends RecyclerView.Adapter<Adapter_All_Booked_List.Room_List_View_Holder> implements Filterable {

    ArrayList<Model_Room_List> model_room_lists;
    ArrayList<Model_Room_List> model_another_room_lists;

    public Adapter_All_Booked_List(ArrayList<Model_Room_List> model_room_lists) {
        this.model_room_lists = model_room_lists;
        model_another_room_lists =new ArrayList<>(model_room_lists);
    }

    @NonNull
    @Override
    public Adapter_All_Booked_List.Room_List_View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_booked_room_list, parent, false);
        return new Adapter_All_Booked_List.Room_List_View_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_All_Booked_List.Room_List_View_Holder holder, int position) {


        Model_Room_List room_list = model_room_lists.get(position);
        holder.room_name.setText("রুম নংঃ #"+room_list.getRoom_name());
        if (room_list.getRoom_type().equals("1")) {
            holder.room_type.setText("রুম টাইপঃ সিঙ্গেল");
        } else if (room_list.getRoom_type().equals("2")) {
            holder.room_type.setText("রুম টাইপঃ ডাবল");
        } else if (room_list.getRoom_type().equals("3")) {
            holder.room_type.setText("রুম টাইপঃ ট্রিপল");
        }

        if (room_list.getRoom_facilities().equals("1")) {
            holder.room_facilities.setText("রুম সুবিধাঃ এসি");
        } else if (room_list.getRoom_facilities().equals("2")) {
            holder.room_facilities.setText("রুম সুবিধাঃ নন-এসি");
        }
        holder.customer_id.setText("আইডিঃ #"+room_list.getC_id());
        holder.customer_name.setText("নামঃ "+room_list.getC_name());
        holder.customer_mobile.setText("মোবাইলঃ "+room_list.getC_mobile());
    }

    @Override
    public int getItemCount() {
        return model_room_lists.size();
    }


    public void sortNameByAsc() {
        Comparator<Model_Room_List> comparator = new Comparator<Model_Room_List>() {

            @Override
            public int compare(Model_Room_List object1, Model_Room_List object2) {
                return object1.getRoom_name().compareToIgnoreCase(object2.getRoom_name());
            }
        };
        Collections.sort(model_room_lists, comparator);
        notifyDataSetChanged();
    }

    public static class Room_List_View_Holder extends RecyclerView.ViewHolder {
        TextView room_name, room_type, room_facilities, customer_id, customer_name, customer_mobile;


        public Room_List_View_Holder(@NonNull View itemView) {
            super(itemView);
            room_name = itemView.findViewById(R.id.room_name);
            room_type = itemView.findViewById(R.id.room_type);
            room_facilities = itemView.findViewById(R.id.room_facilities);
            customer_id = itemView.findViewById(R.id.customer_id);
            customer_name = itemView.findViewById(R.id.customer_name);
            customer_mobile = itemView.findViewById(R.id.customer_mobile);
        }
    }

    public Filter getFilter() {
        return filter;
    }

    public Filter filter = new Filter() {
        @Override
        public FilterResults performFiltering(CharSequence constraint) {
            List<Model_Room_List> filter_list = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filter_list.addAll(model_another_room_lists);
            } else {
                String pattern = constraint.toString().toLowerCase().trim();
                for (Model_Room_List item : model_another_room_lists) {
                    if (item.getRoom_name().toLowerCase().contains(pattern) || item.getC_mobile().toLowerCase().contains(pattern) || item.getC_mobile().toLowerCase().contains(pattern) ||
                            item.getHotel_id().toLowerCase().contains(pattern) || item.getC_name().toLowerCase().contains(pattern) || item.getC_id().toLowerCase().contains(pattern)) {
                        filter_list.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filter_list;
            return filterResults;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            model_room_lists.clear();
            if (results != null) {
                model_room_lists.addAll((List) results.values);
            }

            notifyDataSetChanged();
        }
    };
}

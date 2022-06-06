package com.tanzim.hotelsafety.hotel_user.hotel_home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tanzim.hotelsafety.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Adapter_Room_List extends RecyclerView.Adapter<Adapter_Room_List.Room_List_View_Holder> implements Filterable {

    ArrayList<Model_Room_List> model_room_lists;
    ArrayList<Model_Room_List> model_another_room_lists;

    public Adapter_Room_List(ArrayList<Model_Room_List> model_room_lists) {
        this.model_room_lists = model_room_lists;
        model_another_room_lists =new ArrayList<>(model_room_lists);
    }

    @NonNull
    @Override
    public Room_List_View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_room_list, parent, false);
        return new Room_List_View_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Room_List_View_Holder holder, int position) {


        Model_Room_List room_list = model_room_lists.get(position);
        holder.room_name.setText(room_list.getRoom_name());
        if (room_list.getRoom_type().equals("1")) {
            holder.room_type.setText(R.string.bed_single);
        } else if (room_list.getRoom_type().equals("2")) {
            holder.room_type.setText(R.string.bed_double);
        } else if (room_list.getRoom_type().equals("3")) {
            holder.room_type.setText(R.string.bed_triple);
        }

        if (room_list.getRoom_facilities().equals("1")) {
            holder.room_facilities.setText(R.string.bed_ac);
        } else if (room_list.getRoom_facilities().equals("2")) {
            holder.room_facilities.setText(R.string.bed_non_ac);
        }

        if (room_list.getIs_booked().equals("1")) {
            holder.is_booked.setImageResource(R.drawable.ic_baseline_lock_24);
        } else if (room_list.getIs_booked().equals("0")) {
            holder.is_booked.setImageResource(R.drawable.ic_baseline_lock_open_24);
        }

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
        TextView room_name, room_type, room_facilities;
        ImageView is_booked;
        LinearLayout linearLayout;

        public Room_List_View_Holder(@NonNull View itemView) {
            super(itemView);
            room_name = itemView.findViewById(R.id.room_name);
            room_type = itemView.findViewById(R.id.room_type);
            room_facilities = itemView.findViewById(R.id.room_facilities);
            is_booked = itemView.findViewById(R.id.is_booked);
            linearLayout = itemView.findViewById(R.id.no_data_msg);
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
                    if (item.getRoom_name().toLowerCase().contains(pattern)) {
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

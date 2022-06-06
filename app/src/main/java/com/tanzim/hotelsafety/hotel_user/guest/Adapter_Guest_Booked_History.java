package com.tanzim.hotelsafety.hotel_user.guest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tanzim.hotelsafety.R;

import java.util.ArrayList;

public class Adapter_Guest_Booked_History extends RecyclerView.Adapter<Adapter_Guest_Booked_History.HistoryViewHolder> {

    ArrayList<Model_History> model_historyArrayList;

    public Adapter_Guest_Booked_History(ArrayList<Model_History> model_historyArrayList) {
        this.model_historyArrayList = model_historyArrayList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_booked_history,parent,false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        Model_History current = model_historyArrayList.get(position);

        if (current.getRoom_type().equals("1")) {
            holder.room_type.setText("রুম টাইপঃ সিঙ্গেল");
        } else if (current.getRoom_type().equals("2")) {
            holder.room_type.setText("রুম টাইপঃ ডাবল");
        } else if (current.getRoom_type().equals("3")) {
            holder.room_type.setText("রুম টাইপঃ ট্রিপল");
        }

        if (current.getRoom_facilities().equals("1")) {
            holder.room_facilities.setText("রুম সুবিধাঃ এসি");
        } else if (current.getRoom_facilities().equals("2")) {
            holder.room_facilities.setText("রুম সুবিধাঃ নন-এসি");
        }

        holder.room_name.setText("রুম নংঃ #"+current.getRoom_name());
        holder.entry_date.setText("এন্ট্রির তারিখঃ"+current.getEntry_date());
        holder.leave_date.setText("বাহির তারিখঃ"+current.getLeave_date());

    }

    @Override
    public int getItemCount() {
        return model_historyArrayList.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView room_name, room_type, room_facilities, entry_date, leave_date;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            room_name = itemView.findViewById(R.id.room_name);
            room_type = itemView.findViewById(R.id.room_type);
            room_facilities = itemView.findViewById(R.id.room_facilities);
            entry_date = itemView.findViewById(R.id.entry_date);
            leave_date = itemView.findViewById(R.id.leave_date);

        }
    }
}

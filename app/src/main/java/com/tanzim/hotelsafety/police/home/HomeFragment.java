package com.tanzim.hotelsafety.police.home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.tanzim.hotelsafety.R;
import com.tanzim.hotelsafety.app_static.Constraint;
import com.tanzim.hotelsafety.police.hotel_list.Adapter_Hotel_List;
import com.tanzim.hotelsafety.police.hotel_list.Model_Hotel_List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ArrayList<BarEntry> barArraylist;
    View view;
    TextView total_guest, total_hotel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        BarChart barChart = view.findViewById(R.id.barchart);

        total_guest = view.findViewById(R.id.total_guest);
        total_hotel = view.findViewById(R.id.total_hotel);





        barArraylist = new ArrayList<>();
        barArraylist.add(new BarEntry(0,114f));
        barArraylist.add(new BarEntry(1,114f));
        barArraylist.add(new BarEntry(2,90f));
        barArraylist.add(new BarEntry(3,109f));
        barArraylist.add(new BarEntry(4,135f));
        barArraylist.add(new BarEntry(5,120f));
        barArraylist.add(new BarEntry(6,75f));
        barArraylist.add(new BarEntry(7,90f));
        barArraylist.add(new BarEntry(8,109f));
        barArraylist.add(new BarEntry(9,135f));
        barArraylist.add(new BarEntry(10,114f));
        barArraylist.add(new BarEntry(11,90f));


        BarDataSet barDataSet = new BarDataSet(barArraylist,"Yearly Guest Graph");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        //color bar data set
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        //text color
        barDataSet.setValueTextColor(Color.BLACK);
        //settting text size
        barDataSet.setValueTextSize(12f);
        barChart.getDescription().setEnabled(true);

        String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new AxisFormatter(months));



        return view;
    }
    public class AxisFormatter implements IAxisValueFormatter {
        private String[] mValues;
        public AxisFormatter(String[] months) {
            this.mValues = months;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axisBase) {
            return mValues[(int)(value)];
        }
    }




    private void get_activity_guest_count() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();
        String url = Constraint.HOTEL_GET_ALL_BOOKED_ROOM_INFORMATION;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray room_list = jsonObject.getJSONArray("room_list");
                    total_guest.setText(String.valueOf(room_list.length()));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
            }
        });

        requestQueue.add(request);
    }
    private void get_activity_hotel_count() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();
        String url = Constraint.ADMIN_GET_HOTEL_INFORMATION;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray room_list = jsonObject.getJSONArray("hotel_list");
                    total_hotel.setText(String.valueOf(room_list.length()));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
            }
        });

        requestQueue.add(request);
    }

    @Override
    public void onResume() {
        get_activity_guest_count();
        get_activity_hotel_count();
        super.onResume();
    }
}
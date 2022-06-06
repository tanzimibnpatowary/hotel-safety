package com.tanzim.hotelsafety.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.tanzim.hotelsafety.R;
import com.tanzim.hotelsafety.app_static.Constraint;
import com.tanzim.hotelsafety.hotel_user.guest.GuestFragment;
import com.tanzim.hotelsafety.hotel_user.hotel_home.Adapter_Booked_Room_List;
import com.tanzim.hotelsafety.hotel_user.hotel_home.Model_Room_List;
import com.tanzim.hotelsafety.hotel_user.profile.pages.crud_hotel_owner.HotelOwnerList;
import com.yesterselga.countrypicker.CountryPicker;
import com.yesterselga.countrypicker.Theme;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;


import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class Add_Guest extends DialogFragment {
    public static final String GUEST_ADD = "GUEST_ADD";
    public static final String GUEST_UPDATE = "GUEST_UPDATE";
    private Add_Hotel.OnClickListener listener;
    final Calendar myCalendar= Calendar.getInstance();
    ImageView add_guest_nid;
    CircleImageView add_guest_picture;
    CountryPicker picker;
    Bitmap bitmap_profile, bitmap_nid;
    private Uri nid_file_path, profile_file_path;
    TextInputEditText add_guest_name, add_guest_phone, add_guest_email, add_guest_dob, add_guest_nationality,
            add_guest_address, add_guest_arrival, add_guest_leave;
    AutoCompleteTextView add_guest_room_number;
    String[] room_list_arr;

    public interface OnClickListener {
        void onClick(String str1, String st2);
    }

    public void setListener(Add_Hotel.OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = null;
        if (getTag().equals(GUEST_ADD)) {
            dialog = guest_Add();
        }
        if (getTag().equals(GUEST_UPDATE)) {
            dialog = guest_Update();
        }
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;
    }

    private Dialog guest_Add() {
        AlertDialog.Builder add = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_guest, null);
        get_activity_data();
        Button add_hotel_btn_forward = view.findViewById(R.id.add_hotel_btn_forward);
        Button add_hotel_btn_cancel = view.findViewById(R.id.add_hotel_btn_cancel);
        Button second_part_back = view.findViewById(R.id.second_part_back);
        add_hotel_btn_cancel.setOnClickListener(v -> dismiss());
        Button final_submit = view.findViewById(R.id.final_submit);
        LinearLayout add_guest_from_one = view.findViewById(R.id.add_guest_from_one);
        LinearLayout add_guest_from_two = view.findViewById(R.id.add_guest_from_two);

        CardView add_guest_picture_card_view = view.findViewById(R.id.add_guest_picture_card_view);
        CardView add_guest_nid_card_view = view.findViewById(R.id.add_guest_nid_card_view);

        add_guest_nid = view.findViewById(R.id.add_guest_nid);
        add_guest_picture = view.findViewById(R.id.add_guest_picture);



        add_guest_name = view.findViewById(R.id.add_guest_name);
        add_guest_phone = view.findViewById(R.id.add_guest_phone);
        add_guest_email = view.findViewById(R.id.add_guest_email);
        add_guest_dob = view.findViewById(R.id.add_guest_dob);
        DatePickerDialog.OnDateSetListener star =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                add_guest_dob.setText(update_guest_dob());
            }
        };

        add_guest_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(),star,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        picker = CountryPicker.newInstance("Select Country", Theme.LIGHT);  // Set Dialog Title and Theme
        add_guest_nationality = view.findViewById(R.id.add_guest_nationality);
        picker.setListener((name, code, dialCode, flagDrawableResID) -> {

//            EditText countryCode = (EditText)findViewById(R.id.countryCode);
//            EditText countryName = (EditText)findViewById(R.id.countryName);
//            EditText countryDialCode = (EditText)findViewById(R.id.countryDialCode);
//            ImageView countryIcon = (ImageView)findViewById(R.id.countryIcon);
//
//            countryCode.setText(code);
//            countryName.setText(name);
//            countryDialCode.setText(dialCode);
//            countryIcon.setImageResource(flagDrawableResID);
            add_guest_nationality.setText(code);
            picker.dismiss();

        });
        add_guest_nationality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.show(getActivity().getSupportFragmentManager(), "COUNTRY_PICKER");
            }
        });
        add_guest_address = view.findViewById(R.id.add_guest_address);
        add_guest_arrival = view.findViewById(R.id.add_guest_arrival);
        DatePickerDialog.OnDateSetListener arrival =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                add_guest_arrival.setText(update_guest_dob());
            }
        };

        add_guest_arrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(),arrival,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        add_guest_leave = view.findViewById(R.id.add_guest_leave);
        DatePickerDialog.OnDateSetListener leave =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                add_guest_leave.setText(update_guest_dob());
            }
        };

        add_guest_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(),leave,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        add_guest_room_number = view.findViewById(R.id.add_guest_room_number);



        add_hotel_btn_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_add_guest_name = add_guest_name.getText().toString();
                String str_add_guest_phone = add_guest_phone.getText().toString();
                String str_add_guest_email = add_guest_email.getText().toString();
                String str_add_guest_dob = add_guest_dob.getText().toString();
                String str_add_guest_nationality = add_guest_nationality.getText().toString();
                String str_add_guest_address = add_guest_address.getText().toString();
                String str_add_guest_arrival = add_guest_arrival.getText().toString();
                String str_add_guest_stay = add_guest_leave.getText().toString();
                String str_add_guest_room_number = add_guest_room_number.getText().toString();




                if (str_add_guest_name.isEmpty()) {
                    add_guest_name.setError(getString(R.string.err_add_guest_name));
                    return;
                }
                if (str_add_guest_phone.isEmpty()) {
                    add_guest_phone.setError(getString(R.string.err_add_guest_phone));
                    return;
                }
                if (str_add_guest_email.isEmpty()) {
                    add_guest_email.setError(getString(R.string.err_add_guest_email));
                    return;
                }
                if (str_add_guest_dob.isEmpty()) {
                    add_guest_dob.setError(getString(R.string.err_add_guest_dob));
                    return;
                }
                if (str_add_guest_nationality.isEmpty()) {
                    add_guest_nationality.setError(getString(R.string.err_add_guest_nationality));
                    return;
                }
                if (str_add_guest_address.isEmpty()) {
                    add_guest_address.setError(getString(R.string.err_add_guest_address));
                    return;
                }
                if (str_add_guest_arrival.isEmpty()) {
                    add_guest_arrival.setError(getString(R.string.err_add_guest_arrival));
                    return;
                }
                if (str_add_guest_stay.isEmpty()) {
                    add_guest_leave.setError(getString(R.string.err_add_guest_leave));
                    return;
                }
                if (str_add_guest_room_number.isEmpty()) {
                    add_guest_room_number.setError(getString(R.string.err_add_guest_room_number));
                    return;
                }
                add_guest_from_one.setVisibility(View.GONE);
                add_guest_from_two.setVisibility(View.VISIBLE);
                second_part_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        add_guest_from_one.setVisibility(View.VISIBLE);
                        add_guest_from_two.setVisibility(View.GONE);
                    }
                });
                final_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if (add_guest_picture.getTag().equals("guest_photo")) {
//                            Toast.makeText(getContext(), R.string.err_add_guest_picture, Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        if (add_guest_nid.getTag().equals("guest_nid")) {
//                            Toast.makeText(getContext(), R.string.err_add_guest_nid, Toast.LENGTH_SHORT).show();
//                            return;
//                        }

                        submit_data_to_server(str_add_guest_name, str_add_guest_phone, str_add_guest_email, str_add_guest_dob,
                                str_add_guest_nationality, str_add_guest_address, str_add_guest_arrival, str_add_guest_stay,
                                str_add_guest_room_number);


                    }
                });
            }
        });

        add_guest_picture_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(Add_Guest.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start(1);
            }
        });

        add_guest_nid_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(Add_Guest.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start(2);
            }
        });


        add.setView(view);
        return add.create();
    }


    private String update_guest_dob(){
        String myFormat="dd/MM/yyyy";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Dhaka"));
        return dateFormat.format(myCalendar.getTime());
    }

    private Dialog guest_Update() {
        AlertDialog.Builder add = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_guest, null);
        add.setView(view);
        return add.create();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data!=null) {
            Uri guest_uri =  data.getData();
            add_guest_picture.setImageURI(guest_uri);

        } else if (requestCode == 2 && data!=null) {
            Uri guest_uri =  data.getData();
            add_guest_nid.setImageURI(guest_uri);
        }

    }

    void submit_data_to_server(String str_add_guest_name, String str_add_guest_phone, String str_add_guest_email, String str_add_guest_dob,
                              String str_add_guest_nationality, String str_add_guest_address, String str_add_guest_arrival, String str_add_guest_leave,
                              String str_add_guest_room_number){

        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();
        dialog.setCancelable(false);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = Constraint.HOTEL_SET_CUSTOMER_INFORMATION;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();;
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
        }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                bitmap_profile = ((BitmapDrawable)add_guest_picture.getDrawable()).getBitmap();
                bitmap_nid = ((BitmapDrawable)add_guest_nid.getDrawable()).getBitmap();

                String profile_data =imgToString(bitmap_profile);
                String nid_data =imgToString(bitmap_nid);

                params.put("hotel_id", Constraint.HOTEL_ID);
                params.put("str_add_guest_name", str_add_guest_name);
                params.put("str_add_guest_phone", str_add_guest_phone);
                params.put("str_add_guest_email", str_add_guest_email);
                params.put("str_add_guest_dob", str_add_guest_dob);
                params.put("str_add_guest_nationality", str_add_guest_nationality);
                params.put("str_add_guest_address", str_add_guest_address);
                params.put("str_add_guest_arrival", str_add_guest_arrival);
                params.put("str_add_guest_leave", str_add_guest_leave);
                params.put("str_add_guest_room_number", str_add_guest_room_number);
                params.put("str_add_guest_profile", profile_data);
                params.put("str_add_guest_room_nid", nid_data);
                params.put("REQUEST_FOR_INSERT", "REQUEST_FOR_INSERT");
                Log.e("data",nid_data);
                return params;
            }
        };

        requestQueue.add(request);


    }


    // Image Upload Part
    private String imgToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
    }

    void get_activity_data(){

        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("অপেক্ষা করুন....");
        dialog.show();
        dialog.setCancelable(false);
        String url = Constraint.HOTEL_ROOM_INFORMATION;
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray room_list = object.getJSONArray("room_list");
                    room_list_arr = new String[room_list.length()];
                    for (int i = 0; i < room_list.length(); ++i) {
                        JSONObject data_object = room_list.getJSONObject(i);
                        String room_name = data_object.getString("room_no");
                        room_list_arr[i] = room_name;
                    }
                    add_guest_room_number.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.dropdown_menu_popup_item,room_list_arr));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

                String hotel_id = Constraint.HOTEL_ID;
                if (hotel_id.isEmpty()) {
                    Toast.makeText(getContext(), "Something went to wrong!", Toast.LENGTH_SHORT).show();
                } else {
                    params.put("hotel_id", hotel_id);
                }

                return params;
            }
        };

        requestQueue.add(request);
    }

    @Override
    public void onResume() {
        get_activity_data();
        super.onResume();
    }
}

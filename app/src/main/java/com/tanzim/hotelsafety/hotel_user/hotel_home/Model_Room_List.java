package com.tanzim.hotelsafety.hotel_user.hotel_home;

public class Model_Room_List {
    private String _id, room_name, room_type, room_facilities, hotel_id, is_booked, c_id, c_name, c_mobile;

    public Model_Room_List(String _id, String room_name, String room_type, String room_facilities, String hotel_id, String is_booked, String c_id, String c_name, String c_mobile) {
        this._id = _id;
        this.room_name = room_name;
        this.room_type = room_type;
        this.room_facilities = room_facilities;
        this.hotel_id = hotel_id;
        this.is_booked = is_booked;
        this.c_id = c_id;
        this.c_name = c_name;
        this.c_mobile = c_mobile;
    }

    public Model_Room_List(String room_name, String room_type, String room_facilities, String hotel_id, String c_id, String c_name, String c_mobile) {
        this.room_name = room_name;
        this.room_type = room_type;
        this.room_facilities = room_facilities;
        this.hotel_id = hotel_id;
        this.c_id = c_id;
        this.c_name = c_name;
        this.c_mobile = c_mobile;
    }

    public Model_Room_List(String _id, String room_name, String room_type, String room_facilities, String hotel_id, String is_booked) {
        this._id = _id;
        this.room_name = room_name;
        this.room_type = room_type;
        this.room_facilities = room_facilities;
        this.hotel_id = hotel_id;
        this.is_booked = is_booked;
    }

    public Model_Room_List(String room_name, String room_type, String room_facilities, String is_booked) {
        this.room_name = room_name;
        this.room_type = room_type;
        this.room_facilities = room_facilities;
        this.is_booked = is_booked;
    }





    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_mobile() {
        return c_mobile;
    }

    public void setC_mobile(String c_mobile) {
        this.c_mobile = c_mobile;
    }



    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getRoom_facilities() {
        return room_facilities;
    }

    public void setRoom_facilities(String room_facilities) {
        this.room_facilities = room_facilities;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getIs_booked() {
        return is_booked;
    }

    public void setIs_booked(String is_booked) {
        this.is_booked = is_booked;
    }
}

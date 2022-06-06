package com.tanzim.hotelsafety.hotel_user.guest;

public class Model_History {
    String room_name, room_type, room_facilities, entry_date, leave_date;

    public Model_History(String room_name, String room_type, String room_facilities, String entry_date, String leave_date) {
        this.room_name = room_name;
        this.room_type = room_type;
        this.room_facilities = room_facilities;
        this.entry_date = entry_date;
        this.leave_date = leave_date;
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


    public String getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(String entry_date) {
        this.entry_date = entry_date;
    }

    public String getLeave_date() {
        return leave_date;
    }

    public void setLeave_date(String leave_date) {
        this.leave_date = leave_date;
    }
}

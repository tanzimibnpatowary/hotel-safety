package com.tanzim.hotelsafety.hotel_user.guest;

public class Model_Guest_List {
    private String id, customer_id, name, mobile, email, dob, nationality, address, profile, nid, booking_id, entry_date, leave_date, room_no, hotel_id;

    public Model_Guest_List(String id, String customer_id, String name, String mobile, String email, String dob, String nationality, String address, String profile, String nid, String booking_id, String entry_date, String leave_date, String room_no) {
        this.id = id;
        this.customer_id = customer_id;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.dob = dob;
        this.nationality = nationality;
        this.address = address;
        this.profile = profile;
        this.nid = nid;
        this.booking_id = booking_id;
        this.entry_date = entry_date;
        this.leave_date = leave_date;
        this.room_no = room_no;
    }


    public Model_Guest_List(String id, String customer_id, String name, String mobile, String email, String dob, String nationality, String address, String profile, String nid, String hotel_id) {
        this.id = id;
        this.customer_id = customer_id;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.dob = dob;
        this.nationality = nationality;
        this.address = address;
        this.profile = profile;
        this.nid = nid;
        this.hotel_id = hotel_id;
    }



    public Model_Guest_List(String id, String name, String mobile, String nationality, String booking_id, String room_no, String entry_date, String leave_date){
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.nationality = nationality;
        this.booking_id = booking_id;
        this.room_no = room_no;
        this.entry_date = entry_date;
        this.leave_date = leave_date;
    }
    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
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

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }
}

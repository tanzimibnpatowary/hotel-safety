package com.tanzim.hotelsafety.hotel_user.profile.pages.crud_hotel_owner;

public class Owner_Model {
    private String _id, name, address, nid, mobile, politics, hotel_id;

    public Owner_Model(String _id, String name, String address, String nid, String mobile, String politics, String hotel_id) {
        this._id = _id;
        this.name = name;
        this.address = address;
        this.nid = nid;
        this.mobile = mobile;
        this.politics = politics;
        this.hotel_id = hotel_id;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPolitics() {
        return politics;
    }

    public void setPolitics(String politics) {
        this.politics = politics;
    }
}

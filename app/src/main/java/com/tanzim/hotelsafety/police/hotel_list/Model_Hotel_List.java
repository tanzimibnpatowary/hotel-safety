package com.tanzim.hotelsafety.police.hotel_list;

public class Model_Hotel_List {
    private String hotel_name_en, hotel_name_bn, address, mobile, hotel_id;

    public Model_Hotel_List(String hotel_name_en, String hotel_name_bn, String address, String mobile, String hotel_id) {
        this.hotel_name_en = hotel_name_en;
        this.hotel_name_bn = hotel_name_bn;
        this.address = address;
        this.mobile = mobile;
        this.hotel_id = hotel_id;
    }

    public String getHotel_name_en() {
        return hotel_name_en;
    }

    public void setHotel_name_en(String hotel_name_en) {
        this.hotel_name_en = hotel_name_en;
    }

    public String getHotel_name_bn() {
        return hotel_name_bn;
    }

    public void setHotel_name_bn(String hotel_name_bn) {
        this.hotel_name_bn = hotel_name_bn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }
}

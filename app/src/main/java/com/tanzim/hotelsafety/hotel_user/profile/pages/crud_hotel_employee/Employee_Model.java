package com.tanzim.hotelsafety.hotel_user.profile.pages.crud_hotel_employee;

public class Employee_Model {
    private String _id,name,address, mobile, email, passport_no, passport_issue, passport_expired, visa_no, visa_expired, security_papers, hotel_id;

    public Employee_Model(String _id, String name, String address, String mobile, String email, String passport_no, String passport_issue, String passport_expired, String visa_no, String visa_expired, String security_papers, String hotel_id) {
        this._id = _id;
        this.name = name;
        this.address = address;
        this.mobile = mobile;
        this.email = email;
        this.passport_no = passport_no;
        this.passport_issue = passport_issue;
        this.passport_expired = passport_expired;
        this.visa_no = visa_no;
        this.visa_expired = visa_expired;
        this.security_papers = security_papers;
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

    public String getPassport_no() {
        return passport_no;
    }

    public void setPassport_no(String passport_no) {
        this.passport_no = passport_no;
    }

    public String getPassport_issue() {
        return passport_issue;
    }

    public void setPassport_issue(String passport_issue) {
        this.passport_issue = passport_issue;
    }

    public String getPassport_expired() {
        return passport_expired;
    }

    public void setPassport_expired(String passport_expired) {
        this.passport_expired = passport_expired;
    }

    public String getVisa_no() {
        return visa_no;
    }

    public void setVisa_no(String visa_no) {
        this.visa_no = visa_no;
    }

    public String getVisa_expired() {
        return visa_expired;
    }

    public void setVisa_expired(String visa_expired) {
        this.visa_expired = visa_expired;
    }

    public String getSecurity_papers() {
        return security_papers;
    }

    public void setSecurity_papers(String security_papers) {
        this.security_papers = security_papers;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }
}

package com.henceforth.rhino.webServices.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class ProfileList implements Parcelable {

    private Integer user_id;
    private Integer user_type;
    private String customer_id;
    private String email;
    private String firstname;
    private String middlename;
    private String lastname;
    private String company_name;
    private String image;
    private String address1;
    private String address2;
    private String address3;
    private String city;
    private String state;
    private String country;
    private String phone_no;
    private String fax_no;
    private Integer is_notification;
    private Integer app_login_id;
    private String access_token;
    private Integer device_type;
    private String device_id;
    private String fcm_id;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getUser_type() {
        return user_type;
    }

    public void setUser_type(Integer user_type) {
        this.user_type = user_type;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getFax_no() {
        return fax_no;
    }

    public void setFax_no(String fax_no) {
        this.fax_no = fax_no;
    }

    public Integer getIs_notification() {
        return is_notification;
    }

    public void setIs_notification(Integer is_notification) {
        this.is_notification = is_notification;
    }

    public Integer getApp_login_id() {
        return app_login_id;
    }

    public void setApp_login_id(Integer app_login_id) {
        this.app_login_id = app_login_id;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Integer getDevice_type() {
        return device_type;
    }

    public void setDevice_type(Integer device_type) {
        this.device_type = device_type;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getFcm_id() {
        return fcm_id;
    }

    public void setFcm_id(String fcm_id) {
        this.fcm_id = fcm_id;
    }


    public ProfileList(String customer_id,String email, String firstname, String middlename,
                       String lastname,String image, String address1,
                       String address2, String address3, String city, String state, String country,
                       String phone_no) {
        this.user_id = user_id;
        this.user_type = user_type;
        this.customer_id = customer_id;
        this.email = email;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.company_name = company_name;
        this.image = image;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.city = city;
        this.state = state;
        this.country = country;
        this.phone_no = phone_no;
        this.fax_no = fax_no;
        this.is_notification = is_notification;
        this.app_login_id = app_login_id;
        this.access_token = access_token;
        this.device_type = device_type;
        this.device_id = device_id;
        this.fcm_id = fcm_id;
    }

    protected ProfileList(Parcel in) {
        user_id = in.readByte() == 0x00 ? null : in.readInt();
        user_type = in.readByte() == 0x00 ? null : in.readInt();
        customer_id = in.readString();
        email = in.readString();
        firstname = in.readString();
        middlename = in.readString();
        lastname = in.readString();
        company_name = in.readString();
        image = in.readString();
        address1 = in.readString();
        address2 = in.readString();
        address3 = in.readString();
        city = in.readString();
        state = in.readString();
        country = in.readString();
        phone_no = in.readString();
        fax_no = in.readString();
        is_notification = in.readByte() == 0x00 ? null : in.readInt();
        app_login_id = in.readByte() == 0x00 ? null : in.readInt();
        access_token = in.readString();
        device_type = in.readByte() == 0x00 ? null : in.readInt();
        device_id = in.readString();
        fcm_id = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (user_id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(user_id);
        }
        if (user_type == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(user_type);
        }
        dest.writeString(customer_id);
        dest.writeString(email);
        dest.writeString(firstname);
        dest.writeString(middlename);
        dest.writeString(lastname);
        dest.writeString(company_name);
        dest.writeString(image);
        dest.writeString(address1);
        dest.writeString(address2);
        dest.writeString(address3);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(country);
        dest.writeString(phone_no);
        dest.writeString(fax_no);
        if (is_notification == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(is_notification);
        }
        if (app_login_id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(app_login_id);
        }
        dest.writeString(access_token);
        if (device_type == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(device_type);
        }
        dest.writeString(device_id);
        dest.writeString(fcm_id);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ProfileList> CREATOR = new Parcelable.Creator<ProfileList>() {
        @Override
        public ProfileList createFromParcel(Parcel in) {
            return new ProfileList(in);
        }

        @Override
        public ProfileList[] newArray(int size) {
            return new ProfileList[size];
        }
    };
}
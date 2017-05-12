package com.henceforth.rhino.webServices;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HOME on 5/8/2017.
 */

public class ProfileData implements Parcelable {
    private String fname="", lname="", mname="", phNo="", city="", fax="", state="", country="",
            add1="", add2="", add3="",
            compName="", custId="";

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getMname() {
        return mname;
    }

    public String getPhNo() {
        return phNo;
    }

    public String getCity() {
        return city;
    }

    public String getFax() {
        return fax;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }


    public String getAdd1() {
        return add1;
    }

    public String getAdd2() {
        return add2;
    }

    public String getAdd3() {
        return add3;
    }

    public String getCompName() {
        return compName;
    }

    public String getCustId() {
        return custId;
    }

    public ProfileData(String fname, String lname, String mname, String phNo, String city, String fax,
                       String state, String country,  String add1, String add2,
                       String add3, String compName, String custId) {
        this.fname = fname;
        this.lname = lname;
        this.mname = mname;
        this.phNo = phNo;
        this.city = city;
        this.fax = fax;
        this.state = state;

        this.country = country;
        this.add1 = add1;
        this.add2 = add2;
        this.add3 = add3;
        this.compName = compName;
        this.custId = custId;
    }

    protected ProfileData(Parcel in) {
        fname = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fname);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ProfileData> CREATOR = new Parcelable.Creator<ProfileData>() {
        @Override
        public ProfileData createFromParcel(Parcel in) {
            return new ProfileData(in);
        }

        @Override
        public ProfileData[] newArray(int size) {
            return new ProfileData[size];
        }
    };
}
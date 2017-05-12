package com.henceforth.rhino.webServices;

import android.os.Parcel;
import android.os.Parcelable;



public class Services implements Parcelable {
    private Integer service_id;
    private String desc;

    public Integer getService_id() {
        return service_id;
    }

    public void setService_id(Integer service_id) {
        this.service_id = service_id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }



    protected Services(Parcel in) {
        service_id = in.readByte() == 0x00 ? null : in.readInt();
        desc = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Services(String desc) {
        this.desc = desc;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (service_id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(service_id);
        }
        dest.writeString(desc);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Services> CREATOR = new Parcelable.Creator<Services>() {
        @Override
        public Services createFromParcel(Parcel in) {
            return new Services(in);
        }

        @Override
        public Services[] newArray(int size) {
            return new Services[size];
        }
    };
}
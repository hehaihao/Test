package com.xm6leefun.export_module.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2020/10/10 12:04
 */
public class SelectAddressBean implements Parcelable {
    private String address;
    private String name;

    public SelectAddressBean(String address, String name) {
        this.address = address;
        this.name = name;
    }

    protected SelectAddressBean(Parcel in) {
        address = in.readString();
        name = in.readString();
    }

    public static final Creator<SelectAddressBean> CREATOR = new Creator<SelectAddressBean>() {
        @Override
        public SelectAddressBean createFromParcel(Parcel in) {
            return new SelectAddressBean(in);
        }

        @Override
        public SelectAddressBean[] newArray(int size) {
            return new SelectAddressBean[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(name);
    }
}

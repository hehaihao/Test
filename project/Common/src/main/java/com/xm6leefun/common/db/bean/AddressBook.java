package com.xm6leefun.common.db.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * 项目：DC
 * 文件描述：联系人地址本表
 * 作者：ljj
 * 创建时间：2020/9/16
 */
public class AddressBook extends RealmObject implements Parcelable {
    
    /** 联系人钱包地址 */
    @PrimaryKey
    private String address;
    /** 备注信息；默认：暂无备注 */
    private String remark;
    /** 创建时间 */
    private Long created;
    /** 修改时间 */
    private Long modified;
    /** 预留字段 */
    private String others;
    /** 联系人名称 */
    private String person;

    public AddressBook() {
    }

    protected AddressBook(Parcel in) {
        address = in.readString();
        remark = in.readString();
        if (in.readByte() == 0) {
            created = null;
        } else {
            created = in.readLong();
        }
        if (in.readByte() == 0) {
            modified = null;
        } else {
            modified = in.readLong();
        }
        others = in.readString();
        person = in.readString();
    }

    public static final Creator<AddressBook> CREATOR = new Creator<AddressBook>() {
        @Override
        public AddressBook createFromParcel(Parcel in) {
            return new AddressBook(in);
        }

        @Override
        public AddressBook[] newArray(int size) {
            return new AddressBook[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getModified() {
        return modified;
    }

    public void setModified(Long modified) {
        this.modified = modified;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(remark);
        if (created == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(created);
        }
        if (modified == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(modified);
        }
        dest.writeString(others);
        dest.writeString(person);
    }
}

package com.xm6leefun.points_module.transaction.mvp;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/4/6 9:24
 */
public class TransactionListBean implements Parcelable {
    /** 交易id */
    private long id;
    private String contract_address;
    private String originator_address;
    private String re_address;
    private String create_time;
    private String hash;
    private String TypeName;
    private int type;
    private long transfer_num;
    private int status;

    protected TransactionListBean(Parcel in) {
        id = in.readLong();
        contract_address = in.readString();
        originator_address = in.readString();
        re_address = in.readString();
        create_time = in.readString();
        hash = in.readString();
        TypeName = in.readString();
        type = in.readInt();
        transfer_num = in.readLong();
        status = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(contract_address);
        dest.writeString(originator_address);
        dest.writeString(re_address);
        dest.writeString(create_time);
        dest.writeString(hash);
        dest.writeString(TypeName);
        dest.writeInt(type);
        dest.writeLong(transfer_num);
        dest.writeInt(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TransactionListBean> CREATOR = new Creator<TransactionListBean>() {
        @Override
        public TransactionListBean createFromParcel(Parcel in) {
            return new TransactionListBean(in);
        }

        @Override
        public TransactionListBean[] newArray(int size) {
            return new TransactionListBean[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOriginator_address() {
        return originator_address;
    }

    public void setOriginator_address(String originator_address) {
        this.originator_address = originator_address;
    }

    public String getRe_address() {
        return re_address;
    }

    public void setRe_address(String re_address) {
        this.re_address = re_address;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        this.TypeName = typeName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTransfer_num() {
        return transfer_num;
    }

    public void setTransfer_num(long transfer_num) {
        this.transfer_num = transfer_num;
    }

    public String getContract_address() {
        return contract_address;
    }

    public void setContract_address(String contract_address) {
        this.contract_address = contract_address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

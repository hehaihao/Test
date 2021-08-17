package com.xm6leefun.wallet_module.backup_tips.mvp;


import android.os.Parcel;
import android.os.Parcelable;

import com.xm6leefun.common.wallet.KeysInfo;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021年3月26日15:15:57
 */
public class CusCreateWalletBean implements Parcelable {

    private String address;
    private String walletName;
    private String pwd;
    private String promptInfo;
    private KeysInfo keysInfo;
    private int where;  // 0 创建，1 导入，2 备份
    private int type;  // 0 助记词，1 私钥，2 keystore
    private boolean isFirst;//是否第一次创建，判断是否为身份钱包

    public CusCreateWalletBean() {
    }

    protected CusCreateWalletBean(Parcel in) {
        address = in.readString();
        walletName = in.readString();
        pwd = in.readString();
        promptInfo = in.readString();
        keysInfo = in.readParcelable(KeysInfo.class.getClassLoader());
        where = in.readInt();
        type = in.readInt();
        isFirst = in.readByte() != 0;
    }

    public static final Creator<CusCreateWalletBean> CREATOR = new Creator<CusCreateWalletBean>() {
        @Override
        public CusCreateWalletBean createFromParcel(Parcel in) {
            return new CusCreateWalletBean(in);
        }

        @Override
        public CusCreateWalletBean[] newArray(int size) {
            return new CusCreateWalletBean[size];
        }
    };


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPromptInfo() {
        return promptInfo;
    }

    public void setPromptInfo(String promptInfo) {
        this.promptInfo = promptInfo;
    }

    public KeysInfo getKeysInfo() {
        return keysInfo;
    }

    public void setKeysInfo(KeysInfo keysInfo) {
        this.keysInfo = keysInfo;
    }

    public int getWhere() {
        return where;
    }

    public void setWhere(int where) {
        this.where = where;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(address);
        parcel.writeString(walletName);
        parcel.writeString(pwd);
        parcel.writeString(promptInfo);
        parcel.writeParcelable(keysInfo, i);
        parcel.writeInt(where);
        parcel.writeInt(type);
        parcel.writeByte((byte) (isFirst ? 1 : 0));
    }
}

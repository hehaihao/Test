package com.xm6leefun.common.wallet;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class KeysInfo implements Parcelable {
    private String publicKey;
    private String privateKey;
    private String address;
    private String keystore;
    private String password;
    private List<String> words;
    private String msg;
    private int code;

    public KeysInfo() {
    }

    protected KeysInfo(Parcel in) {
        publicKey = in.readString();
        privateKey = in.readString();
        address = in.readString();
        keystore = in.readString();
        password = in.readString();
        words = in.createStringArrayList();
        msg = in.readString();
        code = in.readInt();
    }

    public static final Creator<KeysInfo> CREATOR = new Creator<KeysInfo>() {
        @Override
        public KeysInfo createFromParcel(Parcel in) {
            return new KeysInfo(in);
        }

        @Override
        public KeysInfo[] newArray(int size) {
            return new KeysInfo[size];
        }
    };

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public String getKeystore() {
        return keystore;
    }

    public void setKeystore(String keystore) {
        this.keystore = keystore;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(publicKey);
        dest.writeString(privateKey);
        dest.writeString(address);
        dest.writeString(keystore);
        dest.writeString(password);
        dest.writeStringList(words);
        dest.writeString(msg);
        dest.writeInt(code);
    }
}

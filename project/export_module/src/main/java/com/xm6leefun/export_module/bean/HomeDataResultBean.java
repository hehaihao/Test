package com.xm6leefun.export_module.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @Description:首页获取ft和nft数据响应结果
 * @Author: hhh
 * @CreateDate: 2021/4/12 17:42
 */
public class HomeDataResultBean {

    private List<FtBean> ft;
    private List<NftBean> nft;
    private NftNumBean nftNums;

    public List<FtBean> getFt() {
        return ft;
    }

    public void setFt(List<FtBean> ft) {
        this.ft = ft;
    }

    public List<NftBean> getNft() {
        return nft;
    }

    public void setNft(List<NftBean> nft) {
        this.nft = nft;
    }

    public NftNumBean getNftNums() {
        return nftNums;
    }

    public void setNftNums(NftNumBean nftNums) {
        this.nftNums = nftNums;
    }

    public static class FtBean implements Parcelable{

        private long id;
        private String token_name;
        private String token_logo;
        private String tx_id;
        private String contract_address;
        private long num;

        private int position;

        protected FtBean(Parcel in) {
            id = in.readLong();
            token_name = in.readString();
            token_logo = in.readString();
            tx_id = in.readString();
            contract_address = in.readString();
            num = in.readLong();
            position = in.readInt();
        }

        public static final Creator<FtBean> CREATOR = new Creator<FtBean>() {
            @Override
            public FtBean createFromParcel(Parcel in) {
                return new FtBean(in);
            }

            @Override
            public FtBean[] newArray(int size) {
                return new FtBean[size];
            }
        };

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public String getContract_address() {
            return contract_address;
        }

        public void setContract_address(String contract_address) {
            this.contract_address = contract_address;
        }


        public FtBean() {

        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getToken_name() {
            return token_name;
        }

        public void setToken_name(String token_name) {
            this.token_name = token_name;
        }

        public String getToken_logo() {
            return token_logo;
        }

        public void setToken_logo(String token_logo) {
            this.token_logo = token_logo;
        }

        public String getTx_id() {
            return tx_id;
        }

        public void setTx_id(String tx_id) {
            this.tx_id = tx_id;
        }


        public long getNum() {
            return num;
        }

        public void setNum(long num) {
            this.num = num;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

            dest.writeLong(id);
            dest.writeString(token_name);
            dest.writeString(token_logo);
            dest.writeString(tx_id);
            dest.writeString(contract_address);
            dest.writeLong(num);
            dest.writeInt(position);
        }

    }

    public static class NftBean implements Parcelable {
        private long id;
        private String token_logo;
        private String chain_name;
        private String contract_address;
        private boolean nft_details;

        protected NftBean(Parcel in) {
            id = in.readLong();
            token_logo = in.readString();
            chain_name = in.readString();
            contract_address = in.readString();
            nft_details = in.readByte() != 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(id);
            dest.writeString(token_logo);
            dest.writeString(chain_name);
            dest.writeString(contract_address);
            dest.writeByte((byte) (nft_details ? 1 : 0));
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<NftBean> CREATOR = new Creator<NftBean>() {
            @Override
            public NftBean createFromParcel(Parcel in) {
                return new NftBean(in);
            }

            @Override
            public NftBean[] newArray(int size) {
                return new NftBean[size];
            }
        };

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getToken_logo() {
            return token_logo;
        }

        public void setToken_logo(String token_logo) {
            this.token_logo = token_logo;
        }

        public String getChain_name() {
            return chain_name;
        }

        public void setChain_name(String chain_name) {
            this.chain_name = chain_name;
        }

        public String getContract_address() {
            return contract_address;
        }

        public void setContract_address(String contract_address) {
            this.contract_address = contract_address;
        }

        public boolean isNft_details() {
            return nft_details;
        }

        public void setNft_details(boolean nft_details) {
            this.nft_details = nft_details;
        }
    }

    public static class NftNumBean{
        private long contractAddressNum;
        private long credentialsNum;

        public long getContractAddressNum() {
            return contractAddressNum;
        }

        public void setContractAddressNum(long contractAddressNum) {
            this.contractAddressNum = contractAddressNum;
        }

        public long getCredentialsNum() {
            return credentialsNum;
        }

        public void setCredentialsNum(long credentialsNum) {
            this.credentialsNum = credentialsNum;
        }
    }
}

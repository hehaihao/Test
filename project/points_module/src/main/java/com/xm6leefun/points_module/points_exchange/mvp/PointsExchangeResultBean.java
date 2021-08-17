package com.xm6leefun.points_module.points_exchange.mvp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/4/27 13:51
 */
public class PointsExchangeResultBean {
    private long integralNum;
    private List<ListBean> list;

    public long getIntegralNum() {
        return integralNum;
    }

    public void setIntegralNum(long integralNum) {
        this.integralNum = integralNum;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Parcelable {
        private String integralName;
        private String expendId;
        private String integralLogo;
        private long num;

        protected ListBean(Parcel in) {
            integralName = in.readString();
            expendId = in.readString();
            integralLogo = in.readString();
            num = in.readLong();
        }

        public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel in) {
                return new ListBean(in);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };

        public String getIntegralName() {
            return integralName;
        }

        public void setIntegralName(String integralName) {
            this.integralName = integralName;
        }

        public String getExpendId() {
            return expendId;
        }

        public void setExpendId(String expendId) {
            this.expendId = expendId;
        }

        public String getIntegralLogo() {
            return integralLogo;
        }

        public void setIntegralLogo(String integralLogo) {
            this.integralLogo = integralLogo;
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
            dest.writeString(integralName);
            dest.writeString(expendId);
            dest.writeString(integralLogo);
            dest.writeLong(num);
        }
    }
}

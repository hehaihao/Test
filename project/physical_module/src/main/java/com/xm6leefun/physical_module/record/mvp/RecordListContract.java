package com.xm6leefun.physical_module.record.mvp;

import com.xm6leefun.common.base.BaseView;
import com.xm6leefun.physical_module.physical_list.mvp.PhysicalListBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * @Description:契约层
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:02
 */
public interface RecordListContract {
    interface IModel {
        Observable<?> getRecordList(int page,int limit,String address);
    }

    interface IView extends BaseView {
        void getRecordListSuccess(PhysicalRecordResultBean bean, boolean isLoadMore);
        void onLoadFail(String msg);
    }
}

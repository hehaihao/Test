package com.xm6leefun.physical_module.physical_list.mvp;

import com.xm6leefun.common.base.BaseView;
import java.util.List;
import io.reactivex.Observable;

/**
 * @Description:契约层
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:02
 */
public interface PhysicalListContract {
    interface IModel {
        Observable<?> getPhysicalList(String address,String contractAddress,String name);
    }

    interface IView extends BaseView {
        void getPhysicalListSuccess(List<PhysicalListBean> physicalListBeans,boolean isLoadMore);
        void onLoadFail(String msg);
    }
}

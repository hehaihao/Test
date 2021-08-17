package com.xm6leefun.setting_module.nodesetting.mvp;

import com.xm6leefun.common.BuildConfig;
import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.setting_module.api.SystemSettingsApiService;


import org.onlychain.wallet.base.ApiConfig;

import java.util.List;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/12
 */
public class NodeSettingPresenter extends BasePresenter<NodeSettingContract.IView> {

    private  NodeSettingContract.IModel iModel;
    public NodeSettingPresenter(NodeSettingContract.IView baseView) {
        super(baseView);
        iModel = new NodeSettingModelImp(retrofitManager.createRs(SystemSettingsApiService.class));
    }

    public void getList(){
        //从数据库读取数据
        addDisposableObserveOnMain(iModel.getNodeList(), new BaseObserver<BaseNftDataBean<NodeResultBean>>(baseView,true) {

            @Override
            public void onSuccess(BaseNftDataBean<NodeResultBean> o) {
                if(o != null && o.getResult() != null && o.getResult().getNodeList() != null) {
                    boolean isHasCurrNode = false;
                    List<NodeBean> nodeBeans = o.getResult().getNodeList();
                    for(NodeBean nodeBean : nodeBeans){
                        if(ApiConfig.SUB_IP.replace(BuildConfig.SUB_VERSION,"").equals(nodeBean.getNodeUrl())){
                            isHasCurrNode = true;
                            break;
                        }
                    }
                    if(!isHasCurrNode){
                        nodeBeans.add(0,new NodeBean("Default",ApiConfig.SUB_IP.replace(BuildConfig.SUB_VERSION,""),""));
                    }
                    baseView.getListSuccess(nodeBeans);
                }else baseView.onLoadFail("data_null");
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }

}
